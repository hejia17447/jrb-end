package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.hejia.common.exception.Assert;
import org.hejia.common.result.ResponseEnum;
import org.hejia.common.util.MD5;
import org.hejia.jrb.core.mapper.UserAccountMapper;
import org.hejia.jrb.core.mapper.UserInfoMapper;
import org.hejia.jrb.core.pojo.entity.UserAccount;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.pojo.vo.RegisterVO;
import org.hejia.jrb.core.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
