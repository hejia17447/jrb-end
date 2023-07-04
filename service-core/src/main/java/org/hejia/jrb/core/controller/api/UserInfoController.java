package org.hejia.jrb.core.controller.api;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.exception.Assert;
import org.hejia.common.result.ResponseEnum;
import org.hejia.common.result.Result;
import org.hejia.common.util.RegexValidateUtils;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.pojo.vo.LoginVO;
import org.hejia.jrb.core.pojo.vo.RegisterVO;
import org.hejia.jrb.core.pojo.vo.UserInfoVO;
import org.hejia.jrb.core.service.UserInfoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/core/userInfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 会员注册
     * @param registerVO 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVO registerVO) {
        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String code = registerVO.getCode();

        // 手机号不能为空
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);

        // 手机号不正确
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        // 密码不能为空
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        // 验证码不能为空
        Assert.notEmpty(code, ResponseEnum.CODE_NULL_ERROR);

        // 校验验证码
        String codeGen = (String)redisTemplate.opsForValue().get("srb:sms:code:" + mobile);

        // 验证码不正确
        Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

        // 注册
        userInfoService.register(registerVO);

        return Result.success().message("注册成功");


    }

    /**
     * 会员登录
     * @param loginVO 登录信息
     * @param request 登录请求,用于获取请求IP
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        String ip = request.getRemoteAddr();
        UserInfoVO userInfoVO = userInfoService.login(loginVO, ip);

        return Result.success().data("userInfo", userInfoVO);

    }

    /**
     * 校验令牌
     * @param request 获取token
     * @return 校验结果
     */
    @GetMapping("/checkToken")
    public Result checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        boolean result = JwtUtils.checkToken(token);

        if (result) {
            return Result.success();
        } else {
            return Result.setResult(ResponseEnum.LOGIN_AUTH_ERROR);
        }

    }

    @GetMapping("/checkMobile/{mobile}")
    public Boolean checkMobile(@PathVariable String mobile) {

        return userInfoService.checkMobile(mobile);

    }

}

