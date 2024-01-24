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

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import ltd.qubit.commons.reflect.AnnotationUtils;

/**
 * The base class of all validators.
 *
 * @param <A>
 *       The type of constraint annotations.
 * @param <T>
 *       The type of values to be validated.
 * @author Haixing Hu
 */
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
      final var hc = context.unwrap(HibernateConstraintValidatorContext.class);
      final String message = getErrorMessage();
      hc.disableDefaultConstraintViolation();
      addExpressionVariables(hc);
      addMessageParameters(hc);
      hc.buildConstraintViolationWithTemplate(message)
          .enableExpressionLanguage()
          .addConstraintViolation();
    }
    return valid;
  }

  protected String getErrorMessage() {
    final String message = AnnotationUtils.getAttributeOrNull(annotation, "message");
    if (message != null) {
      return message;
    }
    final String annotationName = annotation.annotationType().getSimpleName();
    return "The value of the field violates the constraints of the annotation @" + annotationName;
  }

  /**
   * Adds expression variables to the context used to interpolate the error message.
   * <p>
   * The default implementation does nothing. Subclasses may override this
   * method to add additional variables.
   * <p>
   * Apart from the syntax, the main difference between message parameters and
   * expression variables is that message parameters are simply interpolated
   * whereas expression variables are interpreted using the Expression Language
   * engine. In practice, use message parameters if you do not need the advanced
   * features of an Expression Language.
   *
   * @param hc
   *     the context to which to add the variables.
   */
  protected void addExpressionVariables(final HibernateConstraintValidatorContext hc) {
    //  do nothing
  }

  /**
   * Adds message parameters to the context used to interpolate the error message.
   * <p>
   * The default implementation does nothing. Subclasses may override this
   * method to add additional parameters.
   * <p>
   * Apart from the syntax, the main difference between message parameters and
   * expression variables is that message parameters are simply interpolated
   * whereas expression variables are interpreted using the Expression Language
   * engine. In practice, use message parameters if you do not need the advanced
   * features of an Expression Language.
   *
   * @param hc
   *     the context to which to add the parameters.
   */
  protected void addMessageParameters(final HibernateConstraintValidatorContext hc) {
    //  do nothing
  }

  /**
   * Validates the specified value.
   *
   * @param value
   *     the value to be validated.
   * @return
   *     {@code true} if the specified value is valid; {@code false} otherwise.
   */
  public abstract boolean validate(T value);

}
