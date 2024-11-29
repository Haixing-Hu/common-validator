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
import java.util.Set;

import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 对{@link MobileValidator}的单元测试。
 *
 * @author 胡海星
 */
public class MobileValidatorTest extends ValidatorTestBase {

  @Test
  public void testYiDong() {
    final String[] prefixes = {"134", "135", "136", "137", "138", "139", "147",
        "148", "150", "151", "152", "157", "158", "159", "172", "178",
        "182", "183", "184", "187", "188", "198"};
    for (final String prefix : prefixes) {
      testImpl(prefix);
    }
  }

  @Test
  public void testLianTong() {
    final String[] prefixes = {"130", "131", "132", "145", "146", "155", "156",
        "166", "171", "175", "176", "185", "186"};
    for (final String prefix : prefixes) {
      testImpl(prefix);
    }
  }

  @Test
  public void testDianXin() {
    final String[] prefixes = {"133", "149", "153", "173", "174", "177", "180",
        "181", "189", "199"};
    for (final String prefix : prefixes) {
      testImpl(prefix);
    }
  }

  @Test
  public void testVirtual() {
    final String[] prefixes = {"170"};
    for (final String prefix : prefixes) {
      testImpl(prefix);
    }
  }

  private void testImpl(final String prefix) {
    final MobileBean b1 = new MobileBean(prefix + "74937629");
    final Set<ConstraintViolation<MobileBean>> v1 = validator.validate(b1);
    assertEquals(0, v1.size());

    final MobileBean b2 = new MobileBean("0" + prefix + "74937629");
    final Set<ConstraintViolation<MobileBean>> v2 = validator.validate(b2);
    assertEquals(0, v2.size());

    final MobileBean b3 = new MobileBean("86" + prefix + "74937629");
    final Set<ConstraintViolation<MobileBean>> v3 = validator.validate(b3);
    assertEquals(0, v3.size());

    final MobileBean b4 = new MobileBean("17951" + prefix + "74937629");
    final Set<ConstraintViolation<MobileBean>> v4 = validator.validate(b4);
    assertEquals(0, v4.size());
  }

  @Test
  public void testInvalidLength() {
    final MobileBean b1 = new MobileBean("74937629");
    final Set<ConstraintViolation<MobileBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<MobileBean>> i1 = v1.iterator();
    assertEquals("手机号码格式不正确。", i1.next().getMessage());

    final MobileBean b2 = new MobileBean("139015874937629");
    final Set<ConstraintViolation<MobileBean>> v2 = validator.validate(b2);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<MobileBean>> i2 = v2.iterator();
    assertEquals("手机号码格式不正确。", i2.next().getMessage());
  }

  @Test
  public void testInvalidPrefix() {
    final MobileBean b1 = new MobileBean("10074937629");
    final Set<ConstraintViolation<MobileBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<MobileBean>> i1 = v1.iterator();
    assertEquals("手机号码格式不正确。", i1.next().getMessage());

    final MobileBean b2 = new MobileBean("99974937629");
    final Set<ConstraintViolation<MobileBean>> v2 = validator.validate(b2);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<MobileBean>> i2 = v2.iterator();
    assertEquals("手机号码格式不正确。", i2.next().getMessage());
  }

  @Test
  public void testInvalidCharacter() {
    final MobileBean b1 = new MobileBean("1357493762x");
    final Set<ConstraintViolation<MobileBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<MobileBean>> i1 = v1.iterator();
    assertEquals("手机号码格式不正确。", i1.next().getMessage());
  }
}
