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
 * 对{@link IdentityCardValidator}的单元测试。
 *
 * @author 胡海星
 */
public class IdentityCardValidatorTest extends ValidatorTestBase {

  @Test
  public void testValid() {
    final IdentityCardBean b1 = new IdentityCardBean("320114197001160058");
    final Set<ConstraintViolation<IdentityCardBean>> v1 = validator.validate(b1);
    assertEquals(0, v1.size());

    final IdentityCardBean b2 = new IdentityCardBean("32128319931103141X");
    final Set<ConstraintViolation<IdentityCardBean>> v2 = validator.validate(b2);
    assertEquals(0, v2.size());

    final IdentityCardBean b3 = new IdentityCardBean("32128319931103141x");
    final Set<ConstraintViolation<IdentityCardBean>> v3 = validator.validate(b3);
    assertEquals(0, v3.size());
  }

  @Test
  public void testInvalid() {
    final IdentityCardBean b1 = new IdentityCardBean("320114197001160059");
    final Set<ConstraintViolation<IdentityCardBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<IdentityCardBean>> i1 = v1.iterator();
    assertEquals("身份证号码格式不正确。", i1.next().getMessage());

    final IdentityCardBean b2 = new IdentityCardBean("32128319931103141y");
    final Set<ConstraintViolation<IdentityCardBean>> v2 = validator.validate(b2);
    assertEquals(1, v2.size());
    final Iterator<ConstraintViolation<IdentityCardBean>> i2 = v2.iterator();
    assertEquals("身份证号码格式不正确。", i2.next().getMessage());
  }

  @Test
  public void bug_old_area() {
    // FIXME:
    final IdentityCardBean b1 = new IdentityCardBean("320121194905121510");
    final Set<ConstraintViolation<IdentityCardBean>> v1 = validator.validate(b1);
    assertEquals(0, v1.size());

    final IdentityCardBean b2 = new IdentityCardBean("320121196612114711");
    final Set<ConstraintViolation<IdentityCardBean>> v2 = validator.validate(b2);
    assertEquals(0, v2.size());

    final IdentityCardBean b3 = new IdentityCardBean("320121196612114711");
    final Set<ConstraintViolation<IdentityCardBean>> v3 = validator.validate(b3);
    assertEquals(0, v3.size());
  }
}
