package com.erp.erpsystem.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erpsystem.db.AssetLiabilityLog;
import com.erp.erpsystem.db.AssetLiabilityLogRepository;
import com.erp.erpsystem.db.Liability;
import com.erp.erpsystem.db.LiabilityRepository;

// 241112 까지 이자 계산
@SpringBootTest
public class LiabilityUpdateTests {

    @Autowired
    private LiabilityRepository liabilityRepository;

    @Autowired
    private AssetLiabilityLogRepository assetLiabilityLogRepository;

    @Test
    void calculateInterestAndUpdateCurrentValue() {
        // 오늘 날짜 가져오기
        LocalDateTime today = LocalDateTime.now();

        // 모든 부채 목록 가져오기
        List<Liability> liabilities = liabilityRepository.findAll();

        for (Liability liability : liabilities) {
            // 이자를 계산해야 하는 필드들 가져오기
            LocalDateTime startDate = liability.getDate(); // 부채 발생 날짜
            BigDecimal interestRate = liability.getInterestRate(); // 이자율 (예: 3.5%)
            BigDecimal originValue = liability.getOriginValue(); // 원래의 부채 금액

            // 날짜 차이 계산 (부채 발생 날짜부터 오늘까지의 개월 수)
            long monthsBetween = ChronoUnit.MONTHS.between(startDate, today);

            // 현재 value 설정 (초기값은 원래 부채 금액)
            BigDecimal updatedCurrentValue = originValue;

            // 매달 발생하는 이자를 계산하고 로그 남기기
            for (int i = 0; i < monthsBetween; i++) {
                LocalDateTime interestDate = startDate.plusMonths(i + 1); // 매월 발생 날짜 계산

                // 이자 계산 (월별 이자)
                BigDecimal monthlyInterest = originValue
                        .multiply(interestRate)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                // current_value 업데이트 (원금 + 누적 이자)
                updatedCurrentValue = updatedCurrentValue.add(monthlyInterest);

                // 로그 남기기 (매월 이자 발생 시 로그 생성)
                AssetLiabilityLog log = new AssetLiabilityLog();
                log.setName(liability.getName());
                log.setAmount(monthlyInterest); // 발생한 이자 금액
                log.setDescription("이자 발생");
                log.setDate(interestDate); // 매월 이자 발생 날짜
                assetLiabilityLogRepository.save(log);
            }

            // liability 객체의 current_value 업데이트
            liability.setCurrentValue(updatedCurrentValue);

            // 업데이트된 부채 정보를 데이터베이스에 저장
            liabilityRepository.save(liability);
        }

        System.out.println("부채 이자 계산 및 current_value 업데이트 완료");
    }
}
