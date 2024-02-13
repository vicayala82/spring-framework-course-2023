package com.vicayala.demotravel.util;

import org.springframework.http.MediaType;

import java.math.BigDecimal;

public class ServiceConstants {
    private ServiceConstants() {}

    public static final BigDecimal CHARGE_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);
    public static final String FONT_TYPE = "Arial";
    public static final String SHEET_NAME = "Customer total sales";
    public static final String COLUMN_CUSTOMER_ID = "id";
    public static final String COLUMN_CUSTOMER_NAME = "name";
    public static final String COLUMN_CUSTOMER_PURCHASES = "purchases";
    public static final String REPORTS_PATH_WITH_NAME = "reports/Sales-%s";
    public static final String REPORTS_PATH = "reports";
    public static final String FILE_TYPE = ".xlsx";
    public static final String FILE_NAME = "Sales-%s.xlsx";
    public static final MediaType FORCE_DOWNLOAD = new MediaType("application", "force-download");
    public static final String FORCE_DOWNLOAD_HEADER_VALUE = "attachment; filename=report.xlsx";
    public static final String COLLECTION_NAMES = "app_user";

    //REDIS CONSTANTS

    public static final String FLY_CACHE_NAME = "flights"; //cache name for flights
    public static final String HOTEL_CACHE_NAME = "hotels"; // cache name for hotels
    public static final String SCHEDULED_RESET_CACHE = "0 0 0 * * ?";

}

