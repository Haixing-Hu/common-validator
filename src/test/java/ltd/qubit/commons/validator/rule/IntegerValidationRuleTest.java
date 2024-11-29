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

public class IntegerValidationRuleTest {

  private final IntegerValidationRule rule = new IntegerValidationRule();

  @Test
  public void testNormalInteger() {
    assertTrue(rule.validate("123"));
    assertTrue(rule.validate("0"));
    assertTrue(rule.validate("-123"));
    assertTrue(rule.validate(" +123"));
    assertTrue(rule.validate("  -123"));
    assertTrue(rule.validate("  -123  "));
  }

  @Test
  public void testCornerCase() {
    assertFalse(rule.validate(null));
    assertFalse(rule.validate(""));
  }

  @Test
  public void testFailed() {
    assertFalse(rule.validate("1.1"));
    assertFalse(rule.validate("1 23"));
    assertFalse(rule.validate("- 123"));
  }

}
