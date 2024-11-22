package com.erp.erpsystem;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.erpsystem.db.Account;
import com.erp.erpsystem.db.AccountRepository;

@Service
public class AccountDummyDataGenerator {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> generateDummyData() {
        List<Account> dummyData = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 9, 30);
        BigDecimal initialBalance = BigDecimal.valueOf(1000000000.0);
        BigDecimal currentBalance = initialBalance;

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            int dayOfMonth = date.getDayOfMonth();
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            // 공과금, 임대료, 월급, 관리비 (매달 10일)
            if (dayOfMonth == 10) {
                currentBalance = currentBalance.subtract(BigDecimal.valueOf(130000.0));
                dummyData.add(createAccount(date, "공과금", BigDecimal.valueOf(-130000.0), "지출", currentBalance));

                currentBalance = currentBalance.subtract(BigDecimal.valueOf(2600000.0));
                dummyData.add(createAccount(date, "임대료", BigDecimal.valueOf(-2600000.0), "지출", currentBalance));

                currentBalance = currentBalance.subtract(BigDecimal.valueOf(3900000.0));
                dummyData.add(createAccount(date, "월급 (김랜서)", BigDecimal.valueOf(-3900000.0), "지출", currentBalance));

                currentBalance = currentBalance.subtract(BigDecimal.valueOf(3900000.0));
                dummyData.add(createAccount(date, "월급 (윤아처)", BigDecimal.valueOf(-3900000.0), "지출", currentBalance));

                currentBalance = currentBalance.subtract(BigDecimal.valueOf(3900000.0));
                dummyData.add(createAccount(date, "월급 (박세이버)", BigDecimal.valueOf(-3900000.0), "지출", currentBalance));

                currentBalance = currentBalance.subtract(BigDecimal.valueOf(195000.0));
                dummyData.add(createAccount(date, "관리비", BigDecimal.valueOf(-195000.0), "지출", currentBalance));
            }

            // 복리후생비 (매달 1일)
            if (dayOfMonth == 1) {
                currentBalance = currentBalance.subtract(BigDecimal.valueOf(650000.0));
                dummyData.add(createAccount(date, "복리후생비", BigDecimal.valueOf(-650000.0), "지출", currentBalance));
            }

            // 성과급 (매 분기 마지막 달 10일)
            if ((date.getMonthValue() % 3 == 0) && dayOfMonth == 10) {
                currentBalance = currentBalance.subtract(BigDecimal.valueOf(6500000.0));
                dummyData.add(createAccount(date, "성과급", BigDecimal.valueOf(-6500000.0), "지출", currentBalance));
            }

            // 소모품비 (매달 15일)
            if (dayOfMonth == 15) {
                currentBalance = currentBalance.subtract(BigDecimal.valueOf(390000.0));
                dummyData.add(createAccount(date, "소모품비", BigDecimal.valueOf(-390000.0), "지출", currentBalance));
            }

            // 물류비 (매달 7일)
            if (dayOfMonth == 7) {
                currentBalance = currentBalance.subtract(BigDecimal.valueOf(520000.0));
                dummyData.add(createAccount(date, "물류비", BigDecimal.valueOf(-520000.0), "지출", currentBalance));
            }

            // 원자재구입 (매주 목요일)
            if (dayOfWeek == DayOfWeek.THURSDAY) {
                BigDecimal expense;
                if (date.isBefore(LocalDate.of(2024, 1, 1))) {
                    expense = date.isBefore(LocalDate.of(2023, 10, 1)) ? BigDecimal.valueOf(850000.0)
                            : BigDecimal.valueOf(890000.0);
                } else if (date.isBefore(LocalDate.of(2024, 4, 1))) {
                    expense = BigDecimal.valueOf(910000.0);
                } else {
                    expense = BigDecimal.valueOf(8100000.0);
                }
                currentBalance = currentBalance.subtract(expense);
                dummyData.add(createAccount(date, "원자재구입", expense.negate(), "지출", currentBalance));
            }

            // 대금 입금
            if (dayOfMonth == 1) {
                BigDecimal income;
                if (date.isBefore(LocalDate.of(2024, 1, 1))) {
                    income = date.isBefore(LocalDate.of(2023, 10, 1)) ? BigDecimal.valueOf(9150000.0)
                            : BigDecimal.valueOf(9550000.0);
                } else if (date.isBefore(LocalDate.of(2024, 4, 1))) {
                    income = BigDecimal.valueOf(9750000.0);
                } else {
                    income = BigDecimal.valueOf(8750000.0);
                }
                currentBalance = currentBalance.add(income);
                dummyData.add(createAccount(date, "대금 입금 (기업1)", income, "수익", currentBalance));
            }
        }

        return dummyData;
    }

    public void saveDummyDataToDatabase() {
        List<Account> dummyData = generateDummyData();
        accountRepository.saveAll(dummyData);
    }

    private static Account createAccount(LocalDate date, String description, BigDecimal amount, String type,
            BigDecimal afterBalance) {
        Account account = new Account();
        account.setDate(date.atStartOfDay());
        account.setDescription(description);
        account.setAmount(amount);
        account.setType(type);
        account.setAfterBalance(afterBalance);
        return account;
    }
}
