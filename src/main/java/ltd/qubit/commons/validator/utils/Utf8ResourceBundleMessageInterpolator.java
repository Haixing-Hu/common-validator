////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.utils;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

/**
 * 支持UTF-8编码的 .properties 文件的 {@link ResourceBundleMessageInterpolator}.
 *
 * @author 胡海星
 * @see <a href="https://stackoverflow.com/questions/6421790/hibernate-validator-jsf-2-0-validationmessages-properties-in-utf-8">
 *   hibernate validator + jsf 2.0: ValidationMessages.properties in UTF-8</a>
 */
public class Utf8ResourceBundleMessageInterpolator extends
        ResourceBundleMessageInterpolator {

  /**
   * 构造一个 {@code Utf8ResourceBundleMessageInterpolator} 实例。
   * <p>
   * 使用 {@link Utf8ResourceBundleLocator} 来加载 UTF-8 编码的验证消息。
   */
  public Utf8ResourceBundleMessageInterpolator() {
    super(new Utf8ResourceBundleLocator(USER_VALIDATION_MESSAGES));
  }
}
