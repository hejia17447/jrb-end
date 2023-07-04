package org.hejia.jrb.sms.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.hejia.jrb.sms.client.CoreUserInfoClient;
import org.springframework.stereotype.Service;

/**
 * 用户信息接口远程调用回滚操作
 */
@Slf4j
@Service
public class CoreUserInfoClientFallback implements CoreUserInfoClient {
    @Override
    public boolean checkMobile(String mobile) {
        log.error("远程调用失败，服务熔断");
        return false;
    }
}
