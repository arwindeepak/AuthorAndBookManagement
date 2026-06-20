package com.module5.AuthorAndBookManagement.util;

public class CsvHelper {

    public static final String TYPE = "text/csv";

    public static boolean hasCSVFormat(String contentType) {

        return TYPE.equals(contentType);
    }
}
