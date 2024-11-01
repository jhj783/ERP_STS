package com.erp.erpsystem;

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
        double initialBalance = 1000000000.0;
        double currentBalance = initialBalance;

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            int dayOfMonth = date.getDayOfMonth();
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            // 공과금, 임대료, 월급, 관리비 (매달 10일)
            if (dayOfMonth == 10) {
                currentBalance += -130000.0;
                dummyData.add(createAccount(date, "공과금", -130000.0, "지출", currentBalance));
                currentBalance += -2600000.0;
                dummyData.add(createAccount(date, "임대료", -2600000.0, "지출", currentBalance));
                currentBalance += -3900000.0;
                dummyData.add(createAccount(date, "월급 (김랜서)", -3900000.0, "지출", currentBalance));
                currentBalance += -3900000.0;
                dummyData.add(createAccount(date, "월급 (윤아처)", -3900000.0, "지출", currentBalance));
                currentBalance += -3900000.0;
                dummyData.add(createAccount(date, "월급 (박세이버)", -3900000.0, "지출", currentBalance));
                currentBalance += -195000.0;
                dummyData.add(createAccount(date, "관리비", -195000.0, "지출", currentBalance));
            }

            // 복리후생비 (매달 1일)
            if (dayOfMonth == 1) {
                currentBalance += -650000.0;
                dummyData.add(createAccount(date, "복리후생비", -650000.0, "지출", currentBalance));
            }

            // 성과급 (매 분기 마지막 달 10일)
            if ((date.getMonthValue() % 3 == 0) && dayOfMonth == 10) {
                currentBalance += -6500000.0;
                dummyData.add(createAccount(date, "성과급", -6500000.0, "지출", currentBalance));
            }

            // 소모품비 (매달 15일)
            if (dayOfMonth == 15) {
                currentBalance += -390000.0;
                dummyData.add(createAccount(date, "소모품비", -390000.0, "지출", currentBalance));
            }

            // 물류비 (매달 7일)
            if (dayOfMonth == 7) {
                currentBalance += -520000.0;
                dummyData.add(createAccount(date, "물류비", -520000.0, "지출", currentBalance));
            }

            // 원자재구입 (매주 목요일)
            if (dayOfWeek == DayOfWeek.THURSDAY) {
                if (date.isBefore(LocalDate.of(2024, 1, 1))) {
                    if (date.isBefore(LocalDate.of(2023, 10, 1))) {
                        currentBalance += -850000.0;
                        dummyData.add(createAccount(date, "원자재구입", -850000.0, "지출", currentBalance));
                    } else {
                        currentBalance += -890000.0;
                        dummyData.add(createAccount(date, "원자재구입", -890000.0, "지출", currentBalance));
                    }
                } else if (date.isBefore(LocalDate.of(2024, 4, 1))) {
                    currentBalance += -910000.0;
                    dummyData.add(createAccount(date, "원자재구입", -910000.0, "지출", currentBalance));
                } else {
                    currentBalance += -8100000.0;
                    dummyData.add(createAccount(date, "원자재구입", -8100000.0, "지출", currentBalance));
                }
            }

            // 대금 입금
            if (dayOfMonth == 1) {
                if (date.isBefore(LocalDate.of(2024, 1, 1))) {
                    if (date.isBefore(LocalDate.of(2023, 10, 1))) {
                        currentBalance += 9150000.0;
                        dummyData.add(createAccount(date, "대금 입금 (기업1)", 9150000.0, "수익", currentBalance));
                    } else {
                        currentBalance += 9550000.0;
                        dummyData.add(createAccount(date, "대금 입금 (기업1)", 9550000.0, "수익", currentBalance));
                    }
                } else if (date.isBefore(LocalDate.of(2024, 4, 1))) {
                    currentBalance += 9750000.0;
                    dummyData.add(createAccount(date, "대금 입금 (기업1)", 9750000.0, "수익", currentBalance));
                } else {
                    currentBalance += 8750000.0;
                    dummyData.add(createAccount(date, "대금 입금 (기업1)", 8750000.0, "수익", currentBalance));
                }
            } else if (dayOfMonth == 11) {
                if (date.isBefore(LocalDate.of(2024, 1, 1))) {
                    if (date.isBefore(LocalDate.of(2023, 10, 1))) {
                        currentBalance += 9700000.0;
                        dummyData.add(createAccount(date, "대금 입금 (기업2)", 9700000.0, "수익", currentBalance));
                    } else {
                        currentBalance += 10100000.0;
                        dummyData.add(createAccount(date, "대금 입금 (기업2)", 10100000.0, "수익", currentBalance));
                    }
                } else if (date.isBefore(LocalDate.of(2024, 4, 1))) {
                    currentBalance += 10300000.0;
                    dummyData.add(createAccount(date, "대금 입금 (기업2)", 10300000.0, "수익", currentBalance));
                } else {
                    currentBalance += 9300000.0;
                    dummyData.add(createAccount(date, "대금 입금 (기업2)", 9300000.0, "수익", currentBalance));
                }
            } else if (dayOfMonth == 28) {
                if (date.isBefore(LocalDate.of(2024, 1, 1))) {
                    if (date.isBefore(LocalDate.of(2023, 10, 1))) {
                        currentBalance += 7200000.0;
                        dummyData.add(createAccount(date, "대금 입금 (기업3)", 7200000.0, "수익", currentBalance));
                    } else {
                        currentBalance += 7600000.0;
                        dummyData.add(createAccount(date, "대금 입금 (기업3)", 7600000.0, "수익", currentBalance));
                    }
                } else if (date.isBefore(LocalDate.of(2024, 4, 1))) {
                    currentBalance += 7800000.0;
                    dummyData.add(createAccount(date, "대금 입금 (기업3)", 7800000.0, "수익", currentBalance));
                } else {
                    currentBalance += 6800000.0;
                    dummyData.add(createAccount(date, "대금 입금 (기업3)", 6800000.0, "수익", currentBalance));
                }
            } else if (dayOfMonth == 15) {
                if (date.isBefore(LocalDate.of(2024, 1, 1))) {
                    if (date.isBefore(LocalDate.of(2023, 10, 1))) {
                        currentBalance += 11100000.0;
                        dummyData.add(createAccount(date, "대금 입금 (기업4)", 11100000.0, "수익", currentBalance));
                    } else {
                        currentBalance += 11500000.0;
                        dummyData.add(createAccount(date, "대금 입금 (기업4)", 11500000.0, "수익", currentBalance));
                    }
                } else if (date.isBefore(LocalDate.of(2024, 4, 1))) {
                    currentBalance += 11700000.0;
                    dummyData.add(createAccount(date, "대금 입금 (기업4)", 11700000.0, "수익", currentBalance));
                } else {
                    currentBalance += 10700000.0;
                    dummyData.add(createAccount(date, "대금 입금 (기업4)", 10700000.0, "수익", currentBalance));
                }
            }
        }
        return dummyData;
    }

    public void saveDummyDataToDatabase() {
        List<Account> dummyData = generateDummyData();
        for (Account account : dummyData) {
            accountRepository.save(account);
        }
    }

    private static Account createAccount(LocalDate date, String description, Double amount, String type, Double afterBalance) {
        Account account = new Account();
        account.setDate(date.atStartOfDay());
        account.setDescription(description);
        account.setAmount(amount);
        account.setType(type);
        account.setAfterBalance(afterBalance);
        return account;
    }
}
