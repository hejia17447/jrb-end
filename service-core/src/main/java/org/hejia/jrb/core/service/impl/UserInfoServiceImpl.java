package org.hejia.jrb.core.service.impl;

import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.mapper.UserInfoMapper;
import org.hejia.jrb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
