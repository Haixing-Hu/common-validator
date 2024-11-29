////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.rule;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * 此规则用于验证布尔值的字符串表示形式。
 *
 * @author 胡海星
 */
@Immutable
public class BooleanValidationRule implements ValidationRule<String> {

  public static final BooleanValidationRule INSTANCE = new BooleanValidationRule();

  @Override
  public boolean validate(@Nullable final String str) {
    if (str == null) {
      return false;
    } else {
      final String stripped = str.strip();
      return stripped.equalsIgnoreCase("true") || stripped.equalsIgnoreCase("false");
    }
  }
}
