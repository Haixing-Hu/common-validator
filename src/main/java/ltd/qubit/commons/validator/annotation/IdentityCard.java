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

import ltd.qubit.commons.validator.IdentityCardValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 此标注表示字段或变量是中华人民共和国大陆身份证号码。
 *
 * @author 胡海星
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = IdentityCardValidator.class)
@Documented
@Repeatable(IdentityCard.List.class)
public @interface IdentityCard {

  String message() default "{annotation.ltd.qubit.commons.validator.IdentityCard.message}";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

  @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
  @Retention(RUNTIME)
  @Documented
  @interface List {
    IdentityCard[] value();
  }
}
