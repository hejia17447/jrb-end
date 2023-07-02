package org.hejia.jrb.core.mapper;

import org.hejia.jrb.core.pojo.dto.ExcelDictDTO;
import org.hejia.jrb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 数据字典批量保存
     * @param list 字典数据
     */
    void insertBatch(List<ExcelDictDTO> list);
}
