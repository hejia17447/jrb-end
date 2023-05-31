package org.hejia.jrb.core.service.impl;

import org.hejia.jrb.core.pojo.entity.UserLoginRecord;
import org.hejia.jrb.core.mapper.UserLoginRecordMapper;
import org.hejia.jrb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
