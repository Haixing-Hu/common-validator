////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.reflect.AnnotationUtils;
import ltd.qubit.commons.validator.annotation.NotEmpty;

/**
 * 非空字段验证器。
 *
 * @author 胡海星
 */
public class NotEmptyValidator extends BaseValidator<NotEmpty, String> {

  /** {@inheritDoc} */
  @Override
  public boolean validate(final String str) {
    return (str != null) && (str.length() > 0);
  }

  /**
   * 获取注解中指定的字段名。
   *
   * @return 字段名。
   */
  public final String getField() {
    return AnnotationUtils.getAttribute(annotation, "value");
  }
}
