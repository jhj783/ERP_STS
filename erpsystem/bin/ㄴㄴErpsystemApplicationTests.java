package com.erp.erpsystem.test;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erpsystem.db.Account;
import com.erp.erpsystem.db.AccountRepository;

@SpringBootTest
class ㄴㄴErpsystemApplicationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testJpa() {
        // 첫 번째 거래 데이터 저장
        Account c1 = new Account();
        c1.setAmount(5000);
        c1.setDate(LocalDateTime.now());
        c1.setDescription("초기 입금");
        c1.setType("입금");
        c1.setAfterBalance(5000.0);
        this.accountRepository.save(c1);

        // 두 번째 거래 데이터 저장
        Account c2 = new Account();
        c2.setAmount(2000.0);
        c2.setDate(LocalDateTime.now());
        c2.setDescription("출금");
        c2.setType("출금");
        c2.setAfterBalance(3000.0);
        this.accountRepository.save(c2);
    }
}
