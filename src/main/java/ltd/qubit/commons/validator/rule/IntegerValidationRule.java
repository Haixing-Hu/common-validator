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
 * 此规则用于验证十进制整数的字符串表示形式。
 * <p>
 * <b>注意：</b> 此规则接受前导或尾随空格，但不接受中间空格。接受前导正号和负号。
 *
 * @author 胡海星
 */
@Immutable
public class IntegerValidationRule implements ValidationRule<String> {

  /**
   * 用于验证十进制整数的正则表达式。
   * <p>
   * 该正则表达式允许前导或尾随空格，以及可选的前导正号 ({@code +}) 或负号 ({@code -})。
   */
  public static final Pattern REGEXP = Pattern.compile("^\\s*[+-]?\\d+\\s*$");

  /**
   * {@link IntegerValidationRule} 的单例实例。
   */
  public static final IntegerValidationRule INSTANCE = new IntegerValidationRule();

  /** {@inheritDoc} */
  @Override
  public boolean validate(@Nullable final String str) {
    return (str != null) && REGEXP.matcher(str).matches();
  }
}
