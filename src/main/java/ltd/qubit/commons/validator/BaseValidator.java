////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * 所有验证器的基类。
 *
 * @param <A> 约束注解的类型。
 * @param <T> 要验证的值的类型。
 * @author 胡海星
 */
public abstract class BaseValidator<A extends Annotation, T>
    implements ConstraintValidator<A, T> {

  /**
   * 关联的约束注解实例。
   */
  protected Annotation annotation;

  /** {@inheritDoc} */
  @Override
  public void initialize(final A annotation) {
    this.annotation = annotation;
  }

  /** {@inheritDoc} */
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

  /**
   * 获取验证失败时的错误消息。
   * <p>
   * 此方法首先尝试从注解的 {@code message} 属性获取消息。
   * 如果 {@code message} 属性未设置或为空，则会生成一个默认的错误消息，
   * 格式为 "字段值违反了 @注解名 的约束"。
   *
   * @return 错误消息字符串。
   */
  protected String getErrorMessage() {
    final String message = AnnotationUtils.getAttributeOrNull(annotation, "message");
    if (message != null) {
      return message;
    }
    final String annotationName = annotation.annotationType().getSimpleName();
    return "The value of the field violates the constraints of the annotation @" + annotationName;
  }

  /**
   * 向用于插值错误消息的上下文中添加表达式变量。
   * <p>
   * 默认实现不执行任何操作。子类可以覆盖此方法以添加额外的变量。
   * <p>
   * 除了语法之外，消息参数和表达式变量之间的主要区别在于，消息参数只是被简单地插值，
   * 而表达式变量则是使用表达式语言（Expression Language）引擎进行解释。
   * 在实践中，如果您不需要表达式语言的高级功能，请使用消息参数。
   *
   * @param hc
   *     要向其添加变量的上下文。
   */
  protected void addExpressionVariables(final HibernateConstraintValidatorContext hc) {
    //  do nothing
  }

  /**
   * 向用于插值错误消息的上下文中添加消息参数。
   * <p>
   * 默认实现不执行任何操作。子类可以覆盖此方法以添加额外的参数。
   * <p>
   * 除了语法之外，消息参数和表达式变量之间的主要区别在于，消息参数只是被简单地插值，
   * 而表达式变量则是使用表达式语言（Expression Language）引擎进行解释。
   * 在实践中，如果您不需要表达式语言的高级功能，请使用消息参数。
   *
   * @param hc
   *     要向其添加参数的上下文。
   */
  protected void addMessageParameters(final HibernateConstraintValidatorContext hc) {
    //  do nothing
  }

  /**
   * 验证指定的值。
   *
   * @param value
   *     要验证的值。
   * @return
   *     如果指定的值有效，则返回 {@code true}；否则返回 {@code false}。
   */
  public abstract boolean validate(T value);

}
