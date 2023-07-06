package org.hejia.jrb.core.mapper;

import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 借款信息表 Mapper 接口
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface BorrowInfoMapper extends BaseMapper<BorrowInfo> {

    /**
     * 查询借款信息列表
     * @return 列表信息
     */
    List<BorrowInfo> selectBorrowInfoList();

}
