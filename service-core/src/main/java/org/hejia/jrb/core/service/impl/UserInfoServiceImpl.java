package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.hejia.common.exception.Assert;
import org.hejia.common.result.ResponseEnum;
import org.hejia.common.util.MD5;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.mapper.UserAccountMapper;
import org.hejia.jrb.core.mapper.UserInfoMapper;
import org.hejia.jrb.core.mapper.UserLoginRecordMapper;
import org.hejia.jrb.core.pojo.entity.UserAccount;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.pojo.entity.UserLoginRecord;
import org.hejia.jrb.core.pojo.query.UserInfoQuery;
import org.hejia.jrb.core.pojo.vo.LoginVO;
import org.hejia.jrb.core.pojo.vo.RegisterVO;
import org.hejia.jrb.core.pojo.vo.UserIndexVO;
import org.hejia.jrb.core.pojo.vo.UserInfoVO;
import org.hejia.jrb.core.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
@AllArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private final UserAccountMapper userAccountMapper;

    private final UserLoginRecordMapper userLoginRecordMapper;

    /**
     * 注册新用户
     * @param registerVO 注册信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterVO registerVO) {
        //判断用户是否被注册
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", registerVO.getMobile());
        Long count = baseMapper.selectCount(queryWrapper);

        // 手机号是否被注册
        Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);

        //插入用户基本信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setName(registerVO.getMobile());
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        //正常
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        //设置一张静态资源服务器上的头像图片
        userInfo.setHeadImg("https://jrb-file-hejia.oss-cn-hangzhou.aliyuncs.com/avatar/wallpaper7.jpg");

        baseMapper.insert(userInfo);
        //创建会员账户
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);

    }

    /**
     * 用户登录
     * @param loginVO 登录信息
     * @param ip 请求地址
     * @return 登录结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoVO login(LoginVO loginVO, String ip) {

        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        Integer userType = loginVO.getUserType();

        // 获取会员信息
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        queryWrapper.eq("user_type", userType);
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);

        // 用户不存在
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);

        // 校验密码
        //LOGIN_PASSWORD_ERROR(-209, "密码不正确"),
        Assert.equals(MD5.encrypt(password), userInfo.getPassword(), ResponseEnum.LOGIN_PASSWORD_ERROR);

        // 用户是否被禁用
        // LOGIN_DISABLED_ERROR(-210, "用户已被禁用"),
        Assert.equals(userInfo.getStatus(), UserInfo.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);

        // 记录登录日志
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userInfo.getId());
        userLoginRecord.setIp(ip);
        userLoginRecordMapper.insert(userLoginRecord);

        // 生成token
        String token = JwtUtils.createToken(userInfo.getId(), userInfo.getName());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        userInfoVO.setName(userInfo.getName());
        userInfoVO.setNickName(userInfo.getNickName());
        userInfoVO.setHeadImg(userInfo.getHeadImg());
        userInfoVO.setMobile(userInfo.getMobile());
        userInfoVO.setUserType(userType);

        return userInfoVO;

    }

    /**
     * 获取会员用户列表
     * @param pageParam 分页信息
     * @param userInfoQuery 用户查询信息
     * @return 用户列表
     */
    @Override
    public IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery) {

        String mobile = userInfoQuery.getMobile();
        Integer status = userInfoQuery.getStatus();
        Integer userType = userInfoQuery.getUserType();

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isEmpty(userInfoQuery)) {
            return baseMapper.selectPage(pageParam, null);
        }

        userInfoQueryWrapper
                .eq(StringUtils.isNotBlank(mobile), "mobile", mobile)
                .eq(status != null, "status", status)
                .eq(userType != null, "user_type", userType);

        return baseMapper.selectPage(pageParam, userInfoQueryWrapper);
    }

    /**
     * 锁定与解锁
     * @param id 用户id
     * @param status 需要修改的用户状态
     */
    @Override
    public void lock(Long id, Integer status) {

        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);
        baseMapper.updateById(userInfo);

    }

    /**
     * 检查手机号是否注册
     * @param mobile 手机号
     * @return 检查结果
     */
    @Override
    public Boolean checkMobile(String mobile) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Long count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    /**
     * 获取用户个人信息
     * @param userId 用户id
     * @return 个人信息
     */
    @Override
    public UserIndexVO getIndexUserInfo(Long userId) {

        // 用户信息
        UserInfo userInfo = baseMapper.selectById(userId);
        // 账户信息
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq("user_id", userId);
        UserAccount userAccount = userAccountMapper.selectOne(userAccountQueryWrapper);

        // 登录信息
        QueryWrapper<UserLoginRecord> userLoginRecordQueryWrapper = new QueryWrapper<>();
        userLoginRecordQueryWrapper
                .eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 1");
        UserLoginRecord userLoginRecord = userLoginRecordMapper.selectOne(userLoginRecordQueryWrapper);

        // 组装结果数据
        return getUserIndexVO(userInfo, userAccount, userLoginRecord);

    }

    private static UserIndexVO getUserIndexVO(UserInfo userInfo, UserAccount userAccount, UserLoginRecord userLoginRecord) {
        UserIndexVO userIndexVO = new UserIndexVO();
        userIndexVO.setUserId(userInfo.getId());
        userIndexVO.setUserType(userInfo.getUserType());
        userIndexVO.setName(userInfo.getName());
        userIndexVO.setNickName(userInfo.getNickName());
        userIndexVO.setHeadImg(userInfo.getHeadImg());
        userIndexVO.setBindStatus(userInfo.getBindStatus());
        userIndexVO.setAmount(userAccount.getAmount());
        userIndexVO.setFreezeAmount(userAccount.getFreezeAmount());
        userIndexVO.setLastLoginTime(userLoginRecord.getCreateTime());
        return userIndexVO;
    }
}
