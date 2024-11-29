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

public class BooleanValidationRuleTest {

  private final BooleanValidationRule rule = new BooleanValidationRule();

  @Test
  public void testNormal() {
    assertTrue(rule.validate("true"));
    assertTrue(rule.validate(" false  "));
    assertTrue(rule.validate(" True"));
    assertTrue(rule.validate("FaLsE "));
  }

  @Test
  public void testCornerCase() {
    assertFalse(rule.validate(null));
    assertFalse(rule.validate(""));
  }

  @Test
  public void testFailed() {
    assertFalse(rule.validate("是"));
    assertFalse(rule.validate("真"));
    assertFalse(rule.validate("yes"));
  }

}
