////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

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
import ltd.qubit.commons.validator.annotation.IdentityCard;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 中华人民共和国大陆身份证号码验证器。
 * <p>
 * 身份证号码第1~6位为地址码，表示登记户口时所在地的行政区划代码（省、市、县）。
 * 其中1-2位省、自治区、直辖市代码；3-4位地级市、盟、自治州代码；5-6位县、县级 市、区代码。
 * 如果行政区划进行了重新划分，同一个地方进行户口登记的可能存在地址码不一致的情况。行政区划代
 * 码按GB/T2260的规定执行。
 * <p>
 * 身份证号码第7~12为日期码，表示该居民的出生年月日。格式为YYYYMMDD，
 * 如19491001。出生日期码是按GB/T 7408的规定执行的。
 * <p>
 * 身份证号码第15~17为顺序码，表示同一地址码区域内，同年、同月、同日生的人所
 * 编订的顺序号，根据自己身份证的顺序码就可以知道：与我们同年同月同日生的同 性至少有多少个，且
 * 在我们之前登记户籍的有多少人。身份证顺序码的奇数分配给男性，偶数分配给女性。因此身份证号码倒
 * 数第2位是奇数则为男性，偶数则为女性。
 * <p>
 * 身份证中第十八位数字的计算方法如下：
 * <ul>
 * <li>将前面的身份证号码17位数分别乘以不同的系数，
 * 从第一位到第十七位的系数分别为：7、9、10、5、8、4、2、1、6、3、7、9、
 * 10、5、8、4、2；</li>
 * <li>将这17位数字和系数相乘的结果相加；</li>
 * <li>用加出来和除以11，看余数是多少；</li>
 * <li>余数只可能有0 、1、 2、 3、 4、 5、 6、 7、 8、 9、 10这11个数字；</li>
 * <li>其分别对应的最后一位身份证的号码为1、0、X、9、8、7、6、5、4、3、2；</li>
 * </ul>
 * <p>
 * 通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。
 * 如果余数是10，身份证的最后一位号码就是2。
 * <p>
 * <b>注意：</b>对于身份证号码中编码的出生日期，此验证器只验证该日期是否存在，
 * 没有验证出生日期的范围是否合法。
 *
 * @author 胡海星
 */
public class IdentityCardValidator extends BaseValidator<IdentityCard, String> {

  /**
   * 验证身份证号码是否合法。
   *
   * <p>身份证中第十八位数字的计算方法如下：
   * <ul>
   * <li>将前面的身份证号码17位数分别乘以不同的系数，
   * 从第一位到第十七位的系数分别为：7、9、10、5、8、4、2、1、6、3、7、9、
   * 10、5、8、4、2；</li>
   * <li>将这17位数字和系数相乘的结果相加；</li>
   * <li>用加出来和除以11，看余数是多少；</li>
   * <li>余数只可能有0 、1、 2、 3、 4、 5、 6、 7、 8、 9、 10这11个数字。</li>
   * <li>其分别对应的最后一位身份证的号码为1、0、X、9、8、7、6、5、4、3、2；</li>
   * </ul>
   * 通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。
   * 如果余数是10，身份证的最后一位号码就是2。
   *
   * <p><b>注意：</b>对于身份证号码中编码的出生日期，此函数只验证该日期是否存在，
   * 没有验证出生日期的范围是否合法。
   *
   * @param number
   *     待验证的身份证号码。
   * @return 若该身份证号码合法则返回{@code true}，否则返回{@code false}。
   */
  @Override
  public boolean validate(final String number) {
    // 验证长度是否合法
    if (number == null || number.length() != NUMBER_LENGTH) {
      return false;
    }
    // 验证奇偶校验码是否合法
    int sum = 0;
    for (int i = 0; i < NUMBER_LENGTH - 1; ++i) {
      final char ch = number.charAt(i);
      if (ch < '0' || ch > '9') {
        return false;
      }
      sum += (ch - '0') * RATIO[i];
    }
    final char lastChar = LAST_CHAR[sum % LAST_CHAR.length];
    if (Character.toUpperCase(number.charAt(NUMBER_LENGTH - 1)) != lastChar) {
      return false;
    }
    // 验证出生日期是否合法
    return isBirthdayValid(number);
    // 验证地址区县是否合法
    // FIXME: 暂时不支持旧地区编码
    //    if (! isAreaValid(number)) {
    //      return false;
    //    }
  }

  private static final int NUMBER_LENGTH = 18;

  private static final int YEAR_INDEX = 6;

  private static final int YEAR_LENGTH = 4;

  private static final int MONTH_INDEX = 10;

  private static final int MONTH_LENGTH = 2;

  private static final int DAY_INDEX = 12;

  private static final int DAY_LENGTH = 2;

  private static final int AREA_INDEX = 0;

  private static final int AREA_LENGTH = 6;

  private static final int DECIMAL_BASE = 10;

  private static final int[] RATIO = {
      7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
  };

  private static final char[] LAST_CHAR = {
      '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'
  };

  private static Integer parseNumber(final String number, final int start, final int end) {
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
  private static boolean isBirthdayValid(final String number) {
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
  private static boolean isAreaValid(final String number) {
    if (number == null || number.length() != NUMBER_LENGTH) {
      return false;
    }
    final String area = number.substring(AREA_INDEX, AREA_LENGTH);
    return (AREA_MAP.get(area) != null);
  }

  private static final String AREA_MAP_RESOURCE = "/china-area.properties";
  private static final Map<String, String> AREA_MAP;

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
