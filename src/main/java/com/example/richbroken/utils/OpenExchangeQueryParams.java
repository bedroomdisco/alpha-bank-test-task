package com.example.richbroken.utils;

import org.springframework.web.bind.annotation.GetMapping;

public class OpenExchangeQueryParams {
    private final String app_id = "7c7a37dd90394285b7fef73536a6bcd5";

    public OpenExchangeQueryParams() {
    }

    public String getApp_id() {
        return app_id;
    }
}
