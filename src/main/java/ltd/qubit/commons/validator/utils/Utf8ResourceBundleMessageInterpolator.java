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

  public Utf8ResourceBundleMessageInterpolator() {
    super(new Utf8ResourceBundleLocator(USER_VALIDATION_MESSAGES));
  }
}
