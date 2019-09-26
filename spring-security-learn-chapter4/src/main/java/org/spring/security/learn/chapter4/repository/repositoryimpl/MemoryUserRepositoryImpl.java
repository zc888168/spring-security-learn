
/*
 * <p>文件名称: MemoryUserRepositoryImpl</p>
 * <p>项目描述: KOCA 金证云原生平台 V1.0.0</p>
 * <p>公司名称: 深圳市金证科技股份有限公司</p>
 * <p>版权所有: 版权所有(C)2019-2020</p>
 */

package org.spring.security.learn.chapter4.repository.repositoryimpl;

import org.spring.security.learn.chapter4.entity.Account;
import org.spring.security.learn.chapter4.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>一句话功能简述.</p>
 * <p>功能详细描述</p>
 *
 * @author .
 * @version 1.0.0
 * @since 1.0.0, 2019/09/26
 */
@Service
public class MemoryUserRepositoryImpl  implements UserRepository {
    private static final Map<String, Account>  accountDb = new ConcurrentHashMap<String, Account>(5);
    static {
        Account account1 = new Account(1L, "12345678901", "12345678901");
        accountDb.put(account1.getUsername(), account1);
        Account account2 = new Account(2L, "lisi", "11111111");
        accountDb.put(account1.getUsername(), account2);
    }
    @Override
    public Account findByUsername(String username) {
        return accountDb.get(username);
    }
}
