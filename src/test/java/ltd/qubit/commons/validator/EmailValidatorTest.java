////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.RegEx;

import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.net.DomainSuffix;
import ltd.qubit.commons.net.DomainSuffixRegistry;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 对{@link EmailValidator}的单元测试。
 *
 * @author 胡海星
 */
public class EmailValidatorTest extends ValidatorTestBase {

  private static final int TEST_LOOPS = 1000;

  @Test
  public void testValid() {
    final EmailBean b1 = new EmailBean("i@gmail.com");
    final Set<ConstraintViolation<EmailBean>> v1 = validator.validate(b1);
    assertEquals(0, v1.size());

    final EmailBean b2 = new EmailBean("i@i.com");
    final Set<ConstraintViolation<EmailBean>> v2 = validator.validate(b2);
    assertEquals(0, v2.size());
  }

  @Test
  public void testInvalid() {
    final EmailBean b1 = new EmailBean("@gmail.com");
    final Set<ConstraintViolation<EmailBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<EmailBean>> i1 = v1.iterator();
    assertEquals("电子邮件格式不正确。", i1.next().getMessage());

    final EmailBean b2 = new EmailBean("i@i.");
    final Set<ConstraintViolation<EmailBean>> v2 = validator.validate(b2);
    assertEquals(1, v2.size());
    final Iterator<ConstraintViolation<EmailBean>> i2 = v2.iterator();
    assertEquals("电子邮件格式不正确。", i2.next().getMessage());
  }

  @RegEx
  private static final String ASCII_DOMAIN_SUFFIX_REGEX = "[a-z][a-z.]*[a-z]?";
  private static final Pattern ASCII_DOMAIN_SUFFIX_PATTERN =
      Pattern.compile(ASCII_DOMAIN_SUFFIX_REGEX);

  private static String getRandomAsciiDomainSuffix() {
    final DomainSuffixRegistry registry = DomainSuffixRegistry.getInstance();
    final List<DomainSuffix> suffixes = registry.list();
    final int n = suffixes.size();
    String result;
    do {
      final int index = ((random.nextInt() % n) + n) % n;
      final DomainSuffix suffix = suffixes.get(index);
      result = suffix.getDomain();
    } while (! ASCII_DOMAIN_SUFFIX_PATTERN.matcher(result).matches());
    return result;
  }

  public static String createEmail() {
    final String username = random.nextObject(String.class);
    final String domain = random.nextObject(String.class);
    final String suffix = getRandomAsciiDomainSuffix();
    return username + "@" + domain + "." + suffix;
  }

  @Test
  public void listAllDomainSuffixes() {
    final DomainSuffixRegistry registry = DomainSuffixRegistry.getInstance();
    final List<DomainSuffix> suffixes = registry.list();
    for (final DomainSuffix suffix : suffixes) {
      System.out.println(suffix.getDomain());
    }
  }

  @Test
  public void testRandomValidEmail() {
    for (int i = 0; i < TEST_LOOPS; ++i) {
      final String email = createEmail();
      System.out.println("Validate email: " + email);
      final EmailBean bean = new EmailBean(email);
      final Set<ConstraintViolation<EmailBean>> violations = validator.validate(bean);
      // FIXME: some non-ascii domain suffix cannot pass the validation.
      // For example: R@twtkxeIJvsikFBz.návuotna.no
      assertEquals(0, violations.size());
    }
  }
}
