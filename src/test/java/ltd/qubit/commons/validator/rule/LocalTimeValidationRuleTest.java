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

public class LocalTimeValidationRuleTest {

  private final LocalTimeValidationRule rule = new LocalTimeValidationRule();

  @Test
  public void testNormal() {
    assertTrue(rule.validate("22:01:01"));
    assertTrue(rule.validate(" 22:00:10 "));
  }

  @Test
  public void testCornerCase() {
    assertFalse(rule.validate(null));
    assertFalse(rule.validate(""));
  }

  @Test
  public void testFailed() {
    assertFalse(rule.validate("22:1:1"));
    assertFalse(rule.validate("113:01:01"));
    assertFalse(rule.validate("12: 12: 12"));
  }

}
