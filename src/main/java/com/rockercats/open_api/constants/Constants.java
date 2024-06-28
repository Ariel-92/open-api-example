package com.rockercats.open_api.constants;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    // Rate Limit Constants
    public static final int USER_DAILY_ACCESS_LIMIT = 100;
    public static final int IP_DAILY_ACCESS_LIMIT = 500;
}
