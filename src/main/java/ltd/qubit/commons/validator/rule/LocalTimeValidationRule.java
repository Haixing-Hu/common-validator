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
 * 此规则用于验证本地时间的符合 ISO-8601 的字符串表示形式。
 * <p>
 * 此规则匹配下面的时间字符串：
 * <ul>
 *   <li>HH:mm:ss</li>
 * </ul>
 *
 * @author 胡海星
 */
@Immutable
public class LocalTimeValidationRule implements ValidationRule<String> {

  /**
   * 用于验证本地时间字符串的正则表达式。
   * <p>
   * 该正则表达式匹配的格式为 "HH:mm:ss"，其中：
   * <ul>
   *   <li>HH 表示小时 (00-23)。</li>
   *   <li>mm 表示分钟 (00-59)。</li>
   *   <li>ss 表示秒 (00-59)。</li>
   *   <li>允许前导或尾随空格。</li>
   * </ul>
   * 示例： "08:30:00"
   */
  public static final Pattern REGEXP = Pattern.compile("^\\s*\\d{2}:\\d{2}:\\d{2}\\s*$");

  /**
   * {@link LocalTimeValidationRule} 的单例实例。
   */
  public static final LocalTimeValidationRule INSTANCE = new LocalTimeValidationRule();

  /** {@inheritDoc} */
  @Override
  public boolean validate(@Nullable final String str) {
    return (str != null) && REGEXP.matcher(str).matches();
  }
}
