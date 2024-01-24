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
 * The interface of validation rules.
 *
 * @param <T>
 *     the type of the value to be validated.
 * @author Haixing Hu
 */
public interface ValidationRule<T> {

  boolean validate(@Nullable T obj);
}
