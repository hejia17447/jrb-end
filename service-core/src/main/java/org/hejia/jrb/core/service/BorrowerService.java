package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.Borrower;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.BorrowerVO;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface BorrowerService extends IService<Borrower> {

    /**
     * 根据用户id保存借款人信息
     * @param borrowerVO 借款人信息
     * @param userId 用户id
     */
    void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId);

    /**
     * 根据用户id获取用户的认证状态
     * @param userId 用户id
     * @return 查询结果
     */
    Integer getStatusByUserId(Long userId);
}
