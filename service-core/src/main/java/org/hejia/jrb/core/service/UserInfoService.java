package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.RegisterVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 注册新用户
     * @param registerVO 注册信息
     */
    void register(RegisterVO registerVO);

}
