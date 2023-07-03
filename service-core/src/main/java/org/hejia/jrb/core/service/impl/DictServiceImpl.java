package org.hejia.jrb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hejia.jrb.core.listener.ExcelDictDTOListener;
import org.hejia.jrb.core.mapper.DictMapper;
import org.hejia.jrb.core.pojo.dto.ExcelDictDTO;
import org.hejia.jrb.core.pojo.entity.Dict;
import org.hejia.jrb.core.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
@AllArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final RedisTemplate<String, Object> redisTemplate;

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

    /**
     * 根据父ID查询数据列表
     * @param parentId 父ID
     * @return 结果
     */
    @Override
    public List<Dict> listByParentId(Long parentId) {

        // 先查询redis中是否存在数据列表
        List<Dict> dictList;

        try {
            Object o = redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            dictList = castList(o);
            if (dictList != null) {
                log.info("从redis中取值");
                return dictList;
            }
        } catch (Exception e) {
            // 此处不抛出异常，继续执行后面的代码
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

        log.info("从数据库中取值");

        dictList = baseMapper.selectList(new QueryWrapper<Dict>().
                eq("parent_id", parentId));
        dictList.forEach(dict -> {
            // 如果有子节点，则是非叶子节点
            boolean hasChildren = this.hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });

        // 将数据存入redis
        try {
            redisTemplate.opsForValue().set("srb:core:dictList:" + parentId, dictList, 5, TimeUnit.MINUTES);
            log.info("数据存入redis");
        } catch (Exception e) {
            // 此处不抛出异常，继续执行后面的代码
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

        return dictList;
    }

    /**
     * 反序列化
     * @param obj 从redis中读取的数据
     * @return 返回反序列化结果
     */
    private List<Dict> castList(Object obj) {
        List<Dict> result = new ArrayList<>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add((Dict) o);
            }
            return result;
        }
        return null;
    }

    /**
     * 判断该节点是否有子节点
     * @param id 节点id
     * @return 是否有子节点
     */
    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        Long count = baseMapper.selectCount(queryWrapper);
        return count.intValue() > 0;
    }
}
