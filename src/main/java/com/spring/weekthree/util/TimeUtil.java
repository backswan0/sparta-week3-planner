package com.spring.weekthree.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * [리팩토링 완료]
 * 수정이 바로 안 되는 점 해결
 */

public class TimeUtil {

    public static LocalDateTime now () {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return LocalDateTime.parse(LocalDateTime.now().format(formatter));
        // 문자열로 반환되기 때문에 parse() 사용. 예를 들어서 문자열을 객체로 바꾸거나 반대로 바꿀 때
    }
}