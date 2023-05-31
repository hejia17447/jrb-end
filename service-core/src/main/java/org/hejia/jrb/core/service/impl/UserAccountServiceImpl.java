package org.hejia.jrb.core.service.impl;

import org.hejia.jrb.core.pojo.entity.UserAccount;
import org.hejia.jrb.core.mapper.UserAccountMapper;
import org.hejia.jrb.core.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
