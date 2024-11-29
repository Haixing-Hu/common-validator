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

  public static final Pattern REGEXP = Pattern.compile("^\\s*\\d{2}:\\d{2}:\\d{2}\\s*$");

  public static final LocalTimeValidationRule INSTANCE = new LocalTimeValidationRule();

  @Override
  public boolean validate(@Nullable final String str) {
    return (str != null) && REGEXP.matcher(str).matches();
  }
}
