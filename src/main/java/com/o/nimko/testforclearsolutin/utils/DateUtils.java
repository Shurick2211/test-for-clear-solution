package com.o.nimko.testforclearsolutin.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DateUtils {

  public static String now() {
    var formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    return ZonedDateTime.now(ZoneId.of("Europe/Kiev")).format(formater);
  }

  public static LocalDate parseDate(String date){
    log.info("Parsing date: {}", date);
    var formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formater);
  }
}
