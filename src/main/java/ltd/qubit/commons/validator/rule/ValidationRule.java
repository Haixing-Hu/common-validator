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

/**
 * 验证规则的接口。
 *
 * @param <T>
 *     要验证的值的类型。
 * @author 胡海星
 */
public interface ValidationRule<T> {

  /**
   * 验证给定的对象。
   *
   * @param obj
   *     要验证的对象，可以为 {@code null}。
   * @return
   *     如果对象有效，则返回 {@code true}；否则返回 {@code false}。
   */
  boolean validate(@Nullable T obj);
}
