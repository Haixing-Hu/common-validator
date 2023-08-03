////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import java.lang.annotation.Annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import ltd.qubit.commons.reflect.AnnotationUtils;

public abstract class BaseValidator<A extends Annotation, T>
    implements ConstraintValidator<A, T> {

  protected Annotation annotation;

  @Override
  public void initialize(final A annotation) {
    this.annotation = annotation;
  }

  @Override
  public boolean isValid(final T value, final ConstraintValidatorContext context) {
    final boolean valid = validate(value);
    if (! valid) {
      final String message = AnnotationUtils.getAttribute(annotation, "message");
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message)
             .addConstraintViolation();
    }
    return valid;
  }

  public abstract boolean validate(T value);

}
