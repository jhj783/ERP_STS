package com.erp.erpsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class saveAccountDummyDataToDatabase {

    @Autowired
    private AccountDummyDataGenerator dummyDataGenerator;

    @Test
    void testSaveDummyDataToDatabase() {
        dummyDataGenerator.saveDummyDataToDatabase();
    }
}
