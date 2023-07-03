package org.hejia.jrb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.hejia.jrb.core.listener.ExcelDictDTOListener;
import org.hejia.jrb.core.pojo.dto.ExcelDictDTO;
import org.hejia.jrb.core.pojo.entity.Dict;
import org.hejia.jrb.core.mapper.DictMapper;
import org.hejia.jrb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * 字典数据导入
     * @param inputStream 字典数据流
     */
    @Override
    @Transactional(rollbackFor = { Exception.class })
    public void importData(InputStream inputStream) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("importData finished");
    }

    /**
     * 查询数据字典数据
     * @return 数据
     */
    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);
        //创建ExcelDictDTO列表，将Dict列表转换成ExcelDictDTO列表
        List<ExcelDictDTO> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }
}
