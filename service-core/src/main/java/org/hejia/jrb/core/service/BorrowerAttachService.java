package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.BorrowerAttach;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.BorrowerAttachVO;

import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface BorrowerAttachService extends IService<BorrowerAttach> {

    /**
     * 根据借款人id查询借款人附件信息
     * @param borrowerId 借款人id
     * @return 附加信息
     */
    List<BorrowerAttachVO> selectBorrowerAttachVOList(Long borrowerId);
}
