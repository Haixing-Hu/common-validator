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

import javax.annotation.Nullable;

import ltd.qubit.commons.error.InitializationError;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.util.properties.PropertiesUtils;
import ltd.qubit.commons.validator.IdentityCardValidator;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 中国大陆身份证号码相关的工具类。
 * <p>
 * 提供解析和验证身份证号码中特定信息（如出生日期、性别、地区代码）的方法，
 * 以及相关的常量定义。
 * <p>
 * 此类不可实例化。
 */
public final class ChineseIdentityCardUtils {

  /**
   * 身份证号码的长度（18位）。
   */
  public static final int NUMBER_LENGTH = 18;

  /**
   * 身份证号码中年份部分的起始索引（从0开始，第7位字符）。
   */
  public static final int YEAR_INDEX = 6;

  /**
   * 身份证号码中年份部分的长度（4位）。
   */
  public static final int YEAR_LENGTH = 4;

  /**
   * 身份证号码中月份部分的起始索引（从0开始，第11位字符）。
   */
  public static final int MONTH_INDEX = 10;

  /**
   * 身份证号码中月份部分的长度（2位）。
   */
  public static final int MONTH_LENGTH = 2;

  /**
   * 身份证号码中日期部分的起始索引（从0开始，第13位字符）。
   */
  public static final int DAY_INDEX = 12;

  /**
   * 身份证号码中日期部分的长度（2位）。
   */
  public static final int DAY_LENGTH = 2;

  /**
   * 身份证号码中地区代码部分的起始索引（从0开始，第1位字符）。
   */
  public static final int AREA_INDEX = 0;

  /**
   * 身份证号码中地区代码部分的长度（6位）。
   */
  public static final int AREA_LENGTH = 6;

  /**
   * 身份证号码中用于判断性别的顺序码的索引（从0开始，第17位字符）。
   */
  public static final int GENDER_INDEX = 16;

  /**
   * 十进制基数（10）。
   */
  public static final int DECIMAL_BASE = 10;

  /**
   * 身份证号码前17位数字的加权因子。
   * 用于计算校验码。
   */
  public static final int[] RATIO = {
      7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
  };

  /**
   * 身份证号码校验码字符集。
   * 校验码由前17位加权和模11得到，此数组的索引即为模值，对应的值为校验字符。
   */
  public static final char[] LAST_CHAR = {
      '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'
  };

  /**
   * 私有构造函数，防止实例化。
   */
  private ChineseIdentityCardUtils() {
    // 工具类不应被实例化
  }

  /**
   * 从字符串的指定部分解析数字。
   *
   * @param number
   *     包含数字的字符串。
   * @param start
   *     解析的起始索引（包含）。
   * @param end
   *     解析的结束索引（不包含）。
   * @return
   *     解析得到的整数；如果输入无效（例如，{@code number} 为 {@code null}，
   *     索引越界，或指定部分包含非数字字符），则返回 {@code null}。
   */
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
   * 在，也会验证出生日期是否是个合法的日期，但不会验证该日期是否超过今天的日期（不应该超过今天的日期）。
   *
   * @param number
   *     身份证号码。
   * @return
   *     若该身份证号码中的出生日期编码合法则返回{@code true}，否则返回{@code false}。
   */
  public static boolean isBirthdayValid(final String number) {
    return getBirthday(number) != null;
  }

  /**
   * 从身份证号码提取出生日期。
   * <p>
   * 身份证号码第7~12为日期码，表示该居民的出生年月日。格式为YYYYMMDD，
   * 如19491001。出生日期码是按GB/T 7408的规定执行的。
   *
   * <p>
   * <b>注意：</b>对于身份证号码中编码的出生日期，此函数不仅验证该日期是否存在，也会验证出生
   * 日期是否是个合法的日期，同时也会验证该日期是否超过今天的日期（不应该超过今天的日期）。
   *
   * @param number
   *     身份证号码。
   * @return
   *     若该身份证号码中的出生日期编码合法则返回合法的出生日期，否则返回{@code null}。
   */
  @Nullable
  public static LocalDate getBirthday(final String number) {
    final Integer year = parseNumber(number, YEAR_INDEX, YEAR_INDEX + YEAR_LENGTH);
    final Integer month = parseNumber(number, MONTH_INDEX, MONTH_INDEX + MONTH_LENGTH);
    final Integer day = parseNumber(number, DAY_INDEX, DAY_INDEX + DAY_LENGTH);
    if (year == null || month == null || day == null) {
      return null;
    }
    try {
      return LocalDate.of(year, month, day);
    } catch (final DateTimeException e) {
      return null;
    }
  }

  /**
   * 从身份证号码中提取性别。
   *
   * @param number
   *     指定的身份证号码。
   * @return
   *     若该身份证号码中的性别编码合法则返回合法的性别，即字符串"MALE"或"FEMALE"；否则返回
   *     {@code null}。
   */
  @Nullable
  public static String getGender(final String number) {
    if (number == null || number.length() != NUMBER_LENGTH) {
      return null;
    }
    final char ch = number.charAt(GENDER_INDEX);
    if (ch < '0' || ch > '9') {
      return null;
    }
    return ((ch - '0') % 2 == 1) ? "MALE" : "FEMALE";
  }

  /**
   * 从身份证号码中提取家庭住址所在地区编码。
   * <p>
   * 注意：此函数不验证该编码是否合法。
   *
   * @param number
   *     指定的身份证号码。
   * @return
   *     若该身份证号码中的家庭住址所在地区编码合法则返回家庭住址所在地区编码；否则返回
   *     {@code null}。
   */
  @Nullable
  public static String getAreaCode(final String number) {
    if (number == null || number.length() != NUMBER_LENGTH) {
      return null;
    }
    return number.substring(AREA_INDEX, AREA_LENGTH);
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
    final String area = getAreaCode(number);
    if (area == null) {
      return false;
    }
    return (AREA_MAP.get(area) != null);
  }

  /**
   * 中国行政区划代码属性文件的资源路径。
   */
  public static final String AREA_MAP_RESOURCE = "/china-area.properties";
  
  /**
   * 中国行政区划代码与其名称的映射表。
   * <p>
   * 键为6位地区代码，值为地区名称。
   * 此映射表在类加载时从 {@link #AREA_MAP_RESOURCE} 文件中加载并设为不可修改。
   */
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
