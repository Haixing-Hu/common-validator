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
 * 对{@link @NotBlank}的单元测试。
 *
 * @author 胡海星
 */
public class NotBlankValidatorTest extends ValidatorTestBase {

  @Test
  public void testValid() {
    final NotBlankBean b1 = new NotBlankBean("张三");
    final Set<ConstraintViolation<NotBlankBean>> v1 = validator.validate(b1);
    assertEquals(0, v1.size());

    final NotBlankBean b2 = new NotBlankBean("李");
    final Set<ConstraintViolation<NotBlankBean>> v2 = validator.validate(b2);
    assertEquals(0, v2.size());
  }

  @Test
  public void testInvalid() {
    final NotBlankBean b1 = new NotBlankBean(null);
    final Set<ConstraintViolation<NotBlankBean>> v1 = validator.validate(b1);
    assertEquals(1, v1.size());
    final Iterator<ConstraintViolation<NotBlankBean>> i1 = v1.iterator();
    assertEquals("不能为空", i1.next().getMessage());

    final NotBlankBean b2 = new NotBlankBean("");
    final Set<ConstraintViolation<NotBlankBean>> v2 = validator.validate(b2);
    assertEquals(1, v2.size());
    final Iterator<ConstraintViolation<NotBlankBean>> i2 = v2.iterator();
    assertEquals("不能为空", i2.next().getMessage());

    final NotBlankBean b3 = new NotBlankBean(" ");
    final Set<ConstraintViolation<NotBlankBean>> v3 = validator.validate(b3);
    assertEquals(1, v3.size());
    final Iterator<ConstraintViolation<NotBlankBean>> i3 = v3.iterator();
    assertEquals("不能为空", i3.next().getMessage());
  }
}
