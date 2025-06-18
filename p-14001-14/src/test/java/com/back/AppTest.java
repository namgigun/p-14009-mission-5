package com.back;

import com.back.global.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class AppTest {
    @BeforeAll
    static void beforeAll() {
        AppConfig.setTestMode();
    }
    @Test
    @DisplayName("'== 명언 앱 ==' 출력")
    void t1() {
        String rs = AppTestRunner.run("");

        Assertions.assertThat(rs)
                .contains("== 명언 앱 ==")
                .contains("명령) ");
    }
}
