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

  @Test
  public void testAnnotationWithCustomizedMessage() {
    final NotEmptyWithCustomizedMessageBean b1 = new NotEmptyWithCustomizedMessageBean(null);
    final Set<ConstraintViolation<NotEmptyWithCustomizedMessageBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<NotEmptyWithCustomizedMessageBean>> i1 = v1.iterator();
    assertEquals("name属性为空，违背自定义规则", i1.next().getMessage());

    final NotEmptyWithCustomizedMessageBean b2 = new NotEmptyWithCustomizedMessageBean("");
    final Set<ConstraintViolation<NotEmptyWithCustomizedMessageBean>> v2 = validator.validate(b2);
    assertEquals(1, v2.size());
    final Iterator<ConstraintViolation<NotEmptyWithCustomizedMessageBean>> i2 = v2.iterator();
    assertEquals("name属性为空，违背自定义规则", i2.next().getMessage());
  }

  @Test
  public void testAnnotationWithoutValue() {
    final NotEmptyWithoutValueBean b1 = new NotEmptyWithoutValueBean(null);
    final Set<ConstraintViolation<NotEmptyWithoutValueBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<NotEmptyWithoutValueBean>> i1 = v1.iterator();
    assertEquals("不能为空。", i1.next().getMessage());

    final NotEmptyWithoutValueBean b2 = new NotEmptyWithoutValueBean("");
    final Set<ConstraintViolation<NotEmptyWithoutValueBean>> v2 = validator.validate(b2);
    assertEquals(1, v2.size());
    final Iterator<ConstraintViolation<NotEmptyWithoutValueBean>> i2 = v2.iterator();
    assertEquals("不能为空。", i2.next().getMessage());
  }
}
