package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {

    /**
     * 获取用户登录记录前50个
     * @param userId 用户id
     * @return 登录记录列表
     */
    List<UserLoginRecord> listTop(Long userId);
}
