////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.rule.impl;

import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import ltd.qubit.commons.error.InitializationError;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.util.PropertiesUtils;
import ltd.qubit.commons.validator.IdentityCardValidator;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ChineseIdentityCardUtils {

  public static final int NUMBER_LENGTH = 18;

  public static final int YEAR_INDEX = 6;

  public static final int YEAR_LENGTH = 4;

  public static final int MONTH_INDEX = 10;

  public static final int MONTH_LENGTH = 2;

  public static final int DAY_INDEX = 12;

  public static final int DAY_LENGTH = 2;

  public static final int AREA_INDEX = 0;

  public static final int AREA_LENGTH = 6;

  public static final int DECIMAL_BASE = 10;

  public static final int[] RATIO = {
      7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
  };

  public static final char[] LAST_CHAR = {
      '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'
  };

  public static Integer parseNumber(final String number, final int start, final int end) {
    if ((number == null) || (start < 0) || (end <= start) || (end > number.length())) {
      return null;
    }
    int result = 0;
    for (int i = start; i < end; ++i) {
      final char ch = number.charAt(i);
      if (ch < '0' || ch > '9') {
        return null;
      }
      result *= DECIMAL_BASE;
      result += (ch - '0');
    }
    return result;
  }

  /**
   * 检查身份证号码中的出生日期是否合法。
   *
   * <p>身份证号码第7~12为日期码，表示该居民的出生年月日。格式为YYYYMMDD，
   * 如19491001。出生日期码是按GB/T 7408的规定执行的。
   *
   * <p><b>注意：</b>对于身份证号码中编码的出生日期，此函数不仅验证该日期是否存
   * 在，也会验证出生日期是否是个合法的日期，同时也会验证该日期是否超过今天的日
   * 期（不应该超过今天的日期）。
   *
   * @param number
   *     身份证号码。
   * @return 若该身份证号码中的出生日期编码合法则返回{@code true}，否则返回{@code false}。
   */
  public static boolean isBirthdayValid(final String number) {
    final Integer year = parseNumber(number, YEAR_INDEX, YEAR_INDEX + YEAR_LENGTH);
    final Integer month = parseNumber(number, MONTH_INDEX, MONTH_INDEX + MONTH_LENGTH);
    final Integer day = parseNumber(number, DAY_INDEX, DAY_INDEX + DAY_LENGTH);
    if (year == null || month == null || day == null) {
      return false;
    }
    try {
      LocalDate.of(year, month, day);
      return true;
    } catch (final DateTimeException e) {
      return false;
    }
  }

  /**
   * 检查身份证号码中的区县是否合法。
   *
   * <p>身份证号码第1~6位为地址码，表示登记户口时所在地的行政区划代码（省、市、县）。
   * 其中1-2位省、自治区、直辖市代码；3-4位地级市、盟、自治州代码；5-6位县、县级 市、区代码。
   * 如果行政区划进行了重新划分，同一个地方进行户口登记的可能存在地址码不一致的情况。行政区划代
   * 码按GB/T2260的规定执行。
   *
   * @param number
   *     身份证号码。
   * @return 若该身份证号码中的区县是否合法则返回{@code true}，否则返回{@code false}。
   */
  public static boolean isAreaValid(final String number) {
    if (number == null || number.length() != NUMBER_LENGTH) {
      return false;
    }
    final String area = number.substring(AREA_INDEX, AREA_LENGTH);
    return (AREA_MAP.get(area) != null);
  }

  public static final String AREA_MAP_RESOURCE = "/china-area.properties";
  public static final Map<String, String> AREA_MAP;

  static {
    try {
      final URL url = SystemUtils.getResource(AREA_MAP_RESOURCE, IdentityCardValidator.class);
      if (url == null) {
        throw new InitializationError("Resource not found: " + AREA_MAP_RESOURCE);
      }
      final Properties properties = PropertiesUtils.load(url, UTF_8);
      final Map<String, String> map = PropertiesUtils.toMap(properties);
      AREA_MAP = Collections.unmodifiableMap(map);
    } catch (final IOException e) {
      throw new InitializationError(e);
    }
  }
}
