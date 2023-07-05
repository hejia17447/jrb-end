package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.hejia.jrb.core.enums.BorrowerStatusEnum;
import org.hejia.jrb.core.mapper.BorrowerAttachMapper;
import org.hejia.jrb.core.mapper.BorrowerMapper;
import org.hejia.jrb.core.mapper.UserInfoMapper;
import org.hejia.jrb.core.pojo.entity.Borrower;
import org.hejia.jrb.core.pojo.entity.BorrowerAttach;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.pojo.vo.BorrowerVO;
import org.hejia.jrb.core.service.BorrowerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
@AllArgsConstructor
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService {

    private final BorrowerAttachMapper borrowerAttachMapper;

    private final UserInfoMapper userInfoMapper;

    /**
     * 根据用户id保存借款人信息
     * @param borrowerVO 借款人信息
     * @param userId 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId) {

        UserInfo userInfo = userInfoMapper.selectById(userId);

        // 保存借款人信息
        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);
        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
        borrower.setIdCard(userInfo.getIdCard());
        borrower.setMobile(userInfo.getMobile());
        // 认证中
        borrower.setStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        baseMapper.insert(borrower);

        // 保存附件
        List<BorrowerAttach> borrowerAttachList = borrowerVO.getBorrowerAttachList();
        borrowerAttachList.forEach(borrowerAttach -> {
            borrowerAttach.setBorrowerId(borrower.getId());
            borrowerAttachMapper.insert(borrowerAttach);
        });

        // 更新会员状态，更新为认证中
        userInfo.setBorrowAuthStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        userInfoMapper.updateById(userInfo);

    }

    /**
     * 根据用户id获取用户的认证状态
     * @param userId 用户id
     * @return 查询结果
     */
    @Override
    public Integer getStatusByUserId(Long userId) {
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.select("status").eq("user_id", userId);
        List<Object> objects = baseMapper.selectObjs(borrowerQueryWrapper);

        if(objects.isEmpty()){
            // 借款人尚未提交信息
            return BorrowerStatusEnum.NO_AUTH.getStatus();
        }

        return (Integer)objects.get(0);
    }
}
