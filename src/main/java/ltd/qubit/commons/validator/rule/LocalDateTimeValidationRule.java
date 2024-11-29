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
 * </u>
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateTimeValidationRule implements ValidationRule<String> {

  public static final Pattern REGEXP = Pattern.compile("^\\s*\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}\\s*$");

  public static final LocalDateTimeValidationRule INSTANCE = new LocalDateTimeValidationRule();

  @Override
  public boolean validate(@Nullable final String str) {
    return (str != null) && REGEXP.matcher(str).matches();
  }
}
