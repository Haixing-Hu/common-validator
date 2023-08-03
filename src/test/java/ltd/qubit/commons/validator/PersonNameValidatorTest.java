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
 * 对{@link PersonNameValidator}的单元测试。
 *
 * @author 胡海星
 */
public class PersonNameValidatorTest extends ValidatorTestBase {

  @Test
  public void testValidChineseName() {
    final NameBean bean = new NameBean("张三丰");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testValidChineseNameMinLength() {
    final NameBean bean = new NameBean("张三");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testValidChineseNameMaxLength() {
    final NameBean bean = new NameBean("张张张张张张张张张张张张张张张张张张张张张张张张张张张张张张");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithDot() {
    final NameBean bean = new NameBean("阿凡提·穆罕穆德·买买提");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithDot_2() {
    final NameBean bean = new NameBean("阿凡提.穆罕穆德.买买提");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_Cjk_Unified_Ideographs() {
    final NameBean bean = new NameBean("賈滑串句龜綠");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_Cjk_Unified_Ideographs_Extension_A() {
    final NameBean bean = new NameBean("䶮㐑㐒㐓㐔㐕");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_Cjk_Unified_Ideographs_Extension_B() {
    final NameBean bean =
        new NameBean("\uD840\uDC46\uD840\uDC3E\uD840\uDCF5"); // CJK
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_Cjk_Unified_Ideographs_Extension_C() {
    final NameBean bean = new NameBean("\uD86A\uDCFB\uD86A\uDDE6\uD86A\uDE58"); // CJK
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_Cjk_Unified_Ideographs_Extension_D() {
    final NameBean bean = new NameBean("\uD86D\uDF94\uD86D\uDF5A\uD86D\uDF82"); // CJK
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_Cjk_Unified_Ideographs_Extension_E() {
    final NameBean bean = new NameBean("\uD86E\uDCB8\uD86E\uDEC7\uD86E\uDF62"); // CJK
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_Cjk_Unified_Ideographs_Extension_F() {
    final NameBean bean = new NameBean("\uD878\uDE78\uD878\uDE78\uD878\uDE78"); // CJK
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_Cjk_Unified_Ideographs_Supplement() {
    final NameBean bean = new NameBean("\uD87E\uDC64\uD87E\uDC65\uD87E\uDC66\uD87E\uDC67"); // CJK
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testValidPinyinName_all_uppercase() {
    final NameBean bean = new NameBean("ZHANG SAN");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testValidPinyinName_all_lowercase() {
    final NameBean bean = new NameBean("zhang san");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testValidPinyinName_camelcase() {
    final NameBean bean = new NameBean("Zhang San");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testValidEnglishName() {
    final NameBean bean = new NameBean("Bill Gates");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testValidEnglishNameMinLength() {
    final NameBean bean = new NameBean("SS");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testValidEnglishNameMaxLength() {
    final NameBean bean = new NameBean("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testNullName() {
    final NameBean bean = new NameBean(null);
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(1, violations.size());
    final Iterator<ConstraintViolation<NameBean>> iterator = violations.iterator();
    assertEquals("姓名格式不正确。", iterator.next().getMessage());
  }

  @Test
  public void testChineseNameTooShort() {
    final NameBean bean = new NameBean("张");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(1, violations.size());
    final Iterator<ConstraintViolation<NameBean>> iterator = violations.iterator();
    assertEquals("姓名格式不正确。", iterator.next().getMessage());
  }

  @Test
  public void testChineseNameTooLong() {
    final NameBean bean = new NameBean("张张张张张张张张张张张张张张张张张张张张张张张张张张张张张张张");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(1, violations.size());
    final Iterator<ConstraintViolation<NameBean>> iterator = violations.iterator();
    assertEquals("姓名格式不正确。", iterator.next().getMessage());
  }

  @Test
  public void testChineseNameWithInternalSpace() {
    final NameBean bean = new NameBean("张 三");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(1, violations.size());
    final Iterator<ConstraintViolation<NameBean>> iterator = violations.iterator();
    assertEquals("姓名格式不正确。", iterator.next().getMessage());
  }

  @Test
  public void testEnglishNameTooShort() {
    final NameBean bean = new NameBean("S");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(1, violations.size());
    final Iterator<ConstraintViolation<NameBean>> iterator = violations.iterator();
    assertEquals("姓名格式不正确。", iterator.next().getMessage());
  }

  @Test
  public void testEnglishNameTooLong() {
    final NameBean bean = new NameBean(
        "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(1, violations.size());
    final Iterator<ConstraintViolation<NameBean>> iterator = violations.iterator();
    assertEquals("姓名格式不正确。", iterator.next().getMessage());
  }

  @Test
  public void testChineseNameWithRareCharacter_1() {
    final NameBean bean = new NameBean("李\uE844");  // rare character
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    // assertEquals(0, violations.size());
    // FIXME
  }

  @Test
  public void testChineseNameWithRareCharacter_2() {
    final NameBean bean = new NameBean("李");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_3() {
    final NameBean bean = new NameBean("王鑫龑");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  @Test
  public void testChineseNameWithRareCharacter_4() {
    final NameBean bean = new NameBean("张\uE863");    // rare character
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }

  /**
   * some comment.
   */
  @Test
  public void testChineseNameWithRareCharacter_5() {
    final NameBean bean = new NameBean("张");
    final Set<ConstraintViolation<NameBean>> violations = validator.validate(bean);
    assertEquals(0, violations.size());
  }
}
