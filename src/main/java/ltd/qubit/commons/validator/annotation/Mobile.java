////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import ltd.qubit.commons.validator.MobileValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 此标注表示字段或变量是手机号码。
 *
 * @author 胡海星
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
@Documented
@Repeatable(Mobile.List.class)
public @interface Mobile {

  /**
   * 获取验证失败时的错误消息。
   * <p>
   * 此方法首先尝试从注解的 {@code message} 属性获取消息。
   * 如果 {@code message} 属性未设置或为空，则会生成一个默认的错误消息，
   * 格式为 "字段值违反了 @注解名 的约束"。
   *
   * @return 错误消息字符串。
   */
  String message() default "{annotation.ltd.qubit.commons.validator.Mobile.message}";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

  @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
  @Retention(RUNTIME)
  @Documented
  @interface List {
    Mobile[] value();
  }
}
