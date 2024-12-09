package com.spring.weekthree.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 * 도전 과제 U 초안 완료
 * 도전 과제 D 완료
 */

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