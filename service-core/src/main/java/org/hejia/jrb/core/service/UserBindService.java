package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.UserBind;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.UserBindVO;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface UserBindService extends IService<UserBind> {

    /**
     *  账户绑定提交到托管平台的数据
     * @param userBindVO 用户绑定数据
     * @param userId 用户id
     * @return 提交表单
     */
    String commitBindUser(UserBindVO userBindVO, Long userId);

    /**
     * 修改绑定状态
     * @param paramMap 绑定信息
     */
    void notify(Map<String, Object> paramMap);
}
