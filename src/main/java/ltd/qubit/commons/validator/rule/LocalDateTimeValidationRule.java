////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.rule;

import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * 此规则用于验证本地日期时间符合 ISO-8601 的字符串表示形式。
 * <p>
 * 考虑到兼容性，本规则允许更宽松的表示形式，即月份、日期可以省略前导的零。换句话说，此规则
 * 匹配下面的日期字符串：
 * <ul>
 *   <li>yyyy-MM-dd HH:mm:ss</li>
 *   <li>yyyy-M-dd HH:mm:ss</li>
 *   <li>yyyy-MM-d HH:mm:ss</li>
 *   <li>yyyy-M-d HH:mm:ss</li>
 * </ul>
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateTimeValidationRule implements ValidationRule<String> {

  /**
   * 用于验证本地日期时间字符串的正则表达式。
   * <p>
   * 该正则表达式匹配的格式允许：
   * <ul>
   *   <li>年份为4位数字。</li>
   *   <li>月份和日期为1或2位数字。</li>
   *   <li>小时、分钟和秒为2位数字。</li>
   *   <li>日期和时间之间用空格分隔。</li>
   *   <li>允许前导或尾随空格。</li>
   * </ul>
   * 示例： "2023-12-31 08:30:00", "2023-1-1 08:30:00"
   */
  public static final Pattern REGEXP = Pattern.compile("^\\s*\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}\\s*$");

  /**
   * {@link LocalDateTimeValidationRule} 的单例实例。
   */
  public static final LocalDateTimeValidationRule INSTANCE = new LocalDateTimeValidationRule();

  /** {@inheritDoc} */
  @Override
  public boolean validate(@Nullable final String str) {
    return (str != null) && REGEXP.matcher(str).matches();
  }
}
