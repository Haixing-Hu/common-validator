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

public class DecimalValidationRuleTest {

  private final DecimalValidationRule rule = new DecimalValidationRule();

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
  public void testNormalDecimal() {
    assertTrue(rule.validate("12.3"));
    assertTrue(rule.validate("0.123"));
    assertTrue(rule.validate("-123.1"));
    assertTrue(rule.validate("  -123.0"));
    assertTrue(rule.validate("+123.1 "));
    assertTrue(rule.validate("  -123.  "));
  }

  @Test
  public void testScientificNotation() {
    assertTrue(rule.validate("1e10"));
    assertTrue(rule.validate("-1E-10"));
    assertTrue(rule.validate("+121E+1110"));
    assertTrue(rule.validate("0E+1110"));
  }

  @Test
  public void testCornerCase() {
    assertFalse(rule.validate(null));
    assertFalse(rule.validate(""));
  }

  @Test
  public void testFailed() {
    assertFalse(rule.validate(".1.1"));
    assertFalse(rule.validate("+ 1.23"));
    assertFalse(rule.validate("1 23"));
    assertFalse(rule.validate("- 123."));
    assertFalse(rule.validate("e123"));
    assertFalse(rule.validate("1e12.3"));
  }

}
