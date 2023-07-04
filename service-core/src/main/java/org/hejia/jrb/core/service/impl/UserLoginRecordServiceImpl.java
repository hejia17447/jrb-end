package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hejia.jrb.core.mapper.UserLoginRecordMapper;
import org.hejia.jrb.core.pojo.entity.UserLoginRecord;
import org.hejia.jrb.core.service.UserLoginRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 获取用户登录记录前50个
     * @param userId 用户id
     * @return 登录记录列表
     */
    @Override
    public List<UserLoginRecord> listTop(Long userId) {
        QueryWrapper<UserLoginRecord> userLoginRecordQueryWrapper = new QueryWrapper<>();
        userLoginRecordQueryWrapper
                .eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 50");
        return baseMapper.selectList(userLoginRecordQueryWrapper);
    }
}
