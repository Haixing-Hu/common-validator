////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import java.util.Locale;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import ltd.qubit.commons.random.RandomBeanGenerator;

import org.junit.jupiter.api.BeforeAll;

public class ValidatorTestBase {

  protected static Validator validator;
  protected static RandomBeanGenerator random = new RandomBeanGenerator();

  @BeforeAll
  public static void setUp() {
    Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
    try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }
}
