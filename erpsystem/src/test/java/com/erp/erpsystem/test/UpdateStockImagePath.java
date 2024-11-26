package com.erp.erpsystem.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erpsystem.db.Stock;
import com.erp.erpsystem.db.StockRepository;

import java.io.File;
import java.util.Optional;

@SpringBootTest
public class UpdateStockImagePath {

    @Autowired
    private StockRepository stockRepository;

    @Test
    void updateStockWithImagePathUsingFindByName() {
        // 이미지 파일 경로
        File imageFolder = new File("C:/Users/2COOOO20/Documents/ERP_STS/erpsystem/data/AR");

        // 이미지 폴더 확인
        if (!imageFolder.exists() || !imageFolder.isDirectory()) {
            System.err.println("이미지 폴더가 존재하지 않습니다: " + imageFolder.getAbsolutePath());
            return;
        }

        // 이미지 파일 리스트 가져오기
        File[] imageFiles = imageFolder.listFiles((dir, name) -> name.endsWith(".webp"));
        if (imageFiles == null || imageFiles.length == 0) {
            System.err.println("이미지 파일이 없습니다.");
            return;
        }

        for (File imageFile : imageFiles) {
            // 파일명에서 확장자를 제거하여 이름만 추출
            String fileNameWithoutExtension = imageFile.getName().replace(".webp", "");

            // 데이터베이스에서 이름으로 Stock 검색
            Optional<Stock> optionalStock = stockRepository.findByName(fileNameWithoutExtension);

            if (optionalStock.isPresent()) {
                Stock stock = optionalStock.get();
                // 이미지 경로 설정
                stock.setImagePath(imageFile.getAbsolutePath());
                stockRepository.save(stock); // 데이터베이스에 저장
                System.out.println("이미지 경로 저장 성공: " + stock.getName() + " -> " + imageFile.getAbsolutePath());
            } else {
                System.err.println("Stock 엔티티를 찾을 수 없습니다: " + fileNameWithoutExtension);
            }
        }
    }
}
