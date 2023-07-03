package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.dto.ExcelDictDTO;
import org.hejia.jrb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface DictService extends IService<Dict> {

    /**
     * 字典数据导入
     * @param inputStream 字典数据流
     */
    void importData(InputStream inputStream);

    /**
     * 查询数据字典数据
     * @return 数据
     */
    List<ExcelDictDTO> listDictData();

    /**
     * 根据父ID查询数据列表
     * @param parentId 父ID
     * @return 结果
     */
    List<Dict> listByParentId(Long parentId);

}
