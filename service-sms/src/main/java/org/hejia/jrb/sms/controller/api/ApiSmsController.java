package org.hejia.jrb.sms.controller.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.exception.Assert;
import org.hejia.common.result.ResponseEnum;
import org.hejia.common.result.Result;
import org.hejia.common.util.RandomUtils;
import org.hejia.common.util.RegexValidateUtils;
import org.hejia.jrb.sms.service.SmsService;
import org.hejia.jrb.sms.util.SmsProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 短信服务API
 */
@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/sms")
public class ApiSmsController {

    private final SmsService smsService;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 发送带短信接口
     * @param mobile 接收短信电话
     * @return 发送结果
     */
    @GetMapping("/send/{ mobile }")
    public Result send(@PathVariable String mobile) {

        // 判断手机号不为空
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);

        // 判断手机号不正确
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        // 生成验证码
        String code = RandomUtils.getFourBitRandom();

        // 组装短信模板参数
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        // 发送短信
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, param);

        // 将验证码存入redis
        redisTemplate.opsForValue().set("srb:sms:code:" + mobile, code, 5, TimeUnit.MINUTES);

        return Result.success().message("短信发送成功");

    }

}
