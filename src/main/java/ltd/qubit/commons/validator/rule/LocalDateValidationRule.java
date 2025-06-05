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
 * 此规则用于验证本地日期符合 ISO-8601 的字符串表示形式。
 * <p>
 * 考虑到兼容性，本规则允许更宽松的表示形式，即月份、日期可以省略前导的零。换句话说，此规则
 * 匹配下面的日期字符串：
 * <ul>
 *   <li>yyyy-MM-dd</li>
 *   <li>yyyy-M-dd</li>
 *   <li>yyyy-MM-d</li>
 *   <li>yyyy-M-d</li>
 * </ul>
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateValidationRule implements ValidationRule<String> {

  /**
   * 用于验证本地日期字符串的正则表达式。
   * <p>
   * 该正则表达式匹配的格式允许：
   * <ul>
   *   <li>年份为4位数字。</li>
   *   <li>月份和日期为1或2位数字。</li>
   *   <li>允许前导或尾随空格。</li>
   * </ul>
   * 示例： "2023-12-31", "2023-1-1"
   */
  public static final Pattern REGEXP = Pattern.compile("^\\s*\\d{4}-\\d{1,2}-\\d{1,2}\\s*$");

  /**
   * {@link LocalDateValidationRule} 的单例实例。
   */
  public static final LocalDateValidationRule INSTANCE = new LocalDateValidationRule();

  /** {@inheritDoc} */
  @Override
  public boolean validate(@Nullable final String str) {
    return (str != null) && REGEXP.matcher(str).matches();
  }
}
