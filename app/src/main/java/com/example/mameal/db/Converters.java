package com.example.mameal.db;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {
    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .filter(item -> item != null && !item.trim().isEmpty())
                .collect(Collectors.joining(","));
    }

    @TypeConverter
    public static List<String> fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Arrays.asList(value.split(","));
    }
}
