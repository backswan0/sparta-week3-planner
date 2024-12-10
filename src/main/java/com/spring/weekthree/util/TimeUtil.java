package com.spring.weekthree.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static LocalDateTime now() {

        DateTimeFormatter formatter;

        formatter = DateTimeFormatter.
                ofPattern(
                        "yyyy-MM-dd'T'HH:mm:ss"
                );

        return LocalDateTime
                .parse(
                        LocalDateTime
                                .now()
                                .format(formatter)
                );
    }
}