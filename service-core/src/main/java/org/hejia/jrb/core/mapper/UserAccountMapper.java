package org.hejia.jrb.core.mapper;

import org.hejia.jrb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 Mapper 接口
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {


    /**
     *
     * @param bindCode
     * @param bigDecimal
     * @param bigDecimal1
     */
    void updateAccount(
            @Param("bindCode") String bindCode,
            @Param("amount") BigDecimal bigDecimal,
            @Param("freezeAmount") BigDecimal bigDecimal1);

}
