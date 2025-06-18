package com.back.global;

import lombok.Getter;
import lombok.Setter;


public class AppConfig {
    @Getter
    private static String mode = "dev";

    public void setDevMode() {
        mode = "dev";
    }

    public static void setTestMode() {
        mode = "test";
    }
}