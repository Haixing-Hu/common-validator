////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.validator.annotation.Email;
import ltd.qubit.commons.validator.rule.EmailValidationRule;

/**
 * 电子邮件地址验证器。
 *
 * @author 胡海星
 * @see <a href="http://fightingforalostcause.net/misc/2006/compare-email-regex.php">
 *   Comparing E-mail Address Validating Regular Expressions</a>
 * @see <a href="http://thedailywtf.com/Articles/Validating_Email_Addresses.aspx">
 *   VALIDATING EMAIL ADDRESSES</a>
 * @see <a href="http://stackoverflow.com/questions/201323/what-is-the-best-regular-expression-for-validating-email-addresses/201378#201378">
 *   How to validate an email address using a regular expression?</a>
 */
public class EmailValidator extends BaseValidator<Email, String> {

  @Override
  public boolean validate(final String str) {
    return EmailValidationRule.INSTANCE.validate(str);
  }
}
