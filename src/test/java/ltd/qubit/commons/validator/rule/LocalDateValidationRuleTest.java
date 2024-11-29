////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalDateValidationRuleTest {

  private final LocalDateValidationRule rule = new LocalDateValidationRule();

  @Test
  public void testNormal() {
    assertTrue(rule.validate("2023-03-01"));
    assertTrue(rule.validate(" 2023-3-01  "));
    assertTrue(rule.validate(" 2023-03-1"));
    assertTrue(rule.validate("2023-3-1 "));
  }

  @Test
  public void testCornerCase() {
    assertFalse(rule.validate(null));
    assertFalse(rule.validate(""));
  }

  @Test
  public void testFailed() {
    assertFalse(rule.validate("2023-123-12"));
    assertFalse(rule.validate("23-12-12"));
    assertFalse(rule.validate("2023-12-12 12:12:12"));
  }

}
