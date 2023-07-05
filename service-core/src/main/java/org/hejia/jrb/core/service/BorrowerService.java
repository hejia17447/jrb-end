package org.hejia.jrb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hejia.jrb.core.pojo.entity.Borrower;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.BorrowerDetailVO;
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

    /**
     * 通过关键字查询借款分页数据
     * @param pageParam 分页信息
     * @param keyword 查询关键字
     * @return 查询结果
     */
    IPage<Borrower> listPage(Page<Borrower> pageParam, String keyword);

    /**
     * 根据借款人id查询借款人信息
     * @param id 借款人id
     * @return 借款人信息
     */
    BorrowerDetailVO getBorrowerDetailVOById(Long id);
}
