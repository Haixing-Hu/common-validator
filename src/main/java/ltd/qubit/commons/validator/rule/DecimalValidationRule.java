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
 * 此规则用于验证十进制小数的字符串表示形式。
 * <p>
 * <b>注意：</b>可以是正数或者负数，可以是科学计数法，并且只考虑十进制，但不考虑{@code NaN}
 * 和{@code Infinity}。
 *
 * @author 胡海星
 */
@Immutable
public class DecimalValidationRule implements ValidationRule<String> {

  /**
   * 用于验证十进制小数字符串的正则表达式。
   * <p>
   * 该正则表达式允许：
   * <ul>
   *   <li>可选的前导正负号 ({@code +} 或 {@code -})。</li>
   *   <li>整数部分后跟可选的小数点，或者可选的整数部分后跟小数点和至少一位小数。</li>
   *   <li>可选的科学计数法表示 (例如, {@code E+10}, {@code e-5})。</li>
   *   <li>前导或尾随空格。</li>
   * </ul>
   * 此表达式不匹配 {@code NaN} 或 {@code Infinity}。
   */
  public static final Pattern REGEXP = Pattern.compile("^\\s*[+-]?(?:\\d+\\.?|\\d*\\.\\d+)(?:[E|e][+|-]?\\d+)?\\s*$");

  /**
   * {@link DecimalValidationRule} 的单例实例。
   */
  public static final DecimalValidationRule INSTANCE = new DecimalValidationRule();

  /** {@inheritDoc} */
  @Override
  public boolean validate(@Nullable final String str) {
    return (str != null) && REGEXP.matcher(str).matches();
  }
}
