////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.rule;

import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.annotation.RegEx;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * The validation rule of email addresses.
 *
 * @see <a href="http://fightingforalostcause.net/misc/2006/compare-email-regex.php">
 *   Comparing E-mail Address Validating Regular Expressions</a>
 * @see <a href="http://thedailywtf.com/Articles/Validating_Email_Addresses.aspx">
 *   VALIDATING EMAIL ADDRESSES</a>
 * @see <a href="http://stackoverflow.com/questions/201323/what-is-the-best-regular-expression-for-validating-email-addresses/201378#201378">
 *   How to validate an email address using a regular expression?</a>
 * @author Haixing Hu
 */
@Immutable
@ThreadSafe
public class EmailValidationRule implements ValidationRule<String> {

  public static final EmailValidationRule INSTANCE = new EmailValidationRule();

  @RegEx
  private static final String REGEX = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~]"
      + "(\\.?[-!#$%&'*+/0-9=?A-Z^_a-z`{|}~])*@[a-zA-Z0-9](-*\\.?[a-zA-Z0-9])*"
      + "\\.[a-zA-Z](-?[a-zA-Z0-9])+$";

  private static final Pattern PATTERN = Pattern.compile(REGEX);

  @Override
  public boolean validate(@Nullable final String email) {
    if (email == null || email.isEmpty()) {
      return false;
    }
    return PATTERN.matcher(email).matches();
  }
}
