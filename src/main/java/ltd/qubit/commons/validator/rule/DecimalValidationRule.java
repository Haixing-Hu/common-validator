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

  public static final Pattern REGEXP = Pattern.compile("^\\s*[+-]?(?:\\d+\\.?|\\d*\\.\\d+)(?:[E|e][+|-]?\\d+)?\\s*$");

  public static final DecimalValidationRule INSTANCE = new DecimalValidationRule();

  @Override
  public boolean validate(@Nullable final String str) {
    return (str != null) && REGEXP.matcher(str).matches();
  }
}
