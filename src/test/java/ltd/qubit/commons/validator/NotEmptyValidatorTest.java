////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * 对{@link NotEmptyValidator}的单元测试。
 *
 * @author 胡海星
 */
public class NotEmptyValidatorTest extends ValidatorTestBase {

  @Test
  public void testValid() {
    final NotEmptyBean b1 = new NotEmptyBean("张三");
    final Set<ConstraintViolation<NotEmptyBean>> v1 = validator.validate(b1);
    assertEquals(0, v1.size());

    final NotEmptyBean b2 = new NotEmptyBean("李");
    final Set<ConstraintViolation<NotEmptyBean>> v2 = validator.validate(b2);
    assertEquals(0, v2.size());
  }

  @Test
  public void testInvalid() {
    final NotEmptyBean b1 = new NotEmptyBean(null);
    final Set<ConstraintViolation<NotEmptyBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<NotEmptyBean>> i1 = v1.iterator();
    assertEquals("姓名不能为空。", i1.next().getMessage());

    final NotEmptyBean b2 = new NotEmptyBean("");
    final Set<ConstraintViolation<NotEmptyBean>> v2 = validator.validate(b2);
    assertEquals(1, v2.size());
    final Iterator<ConstraintViolation<NotEmptyBean>> i2 = v2.iterator();
    assertEquals("姓名不能为空。", i2.next().getMessage());
  }
}
