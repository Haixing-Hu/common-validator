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
      final String message = getErrorMessage();
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message)
             .addConstraintViolation();
    }
    return valid;
  }

  private String getErrorMessage() {
    final String message = AnnotationUtils.getAttributeOrNull(annotation, "message");
    if (message != null) {
      return message;
    }
    final String annotationName = annotation.annotationType().getSimpleName();
    return "The value of the field is invalid according to the annotation @" + annotationName;
  }

  public abstract boolean validate(T value);

}
