////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import org.junit.jupiter.api.BeforeAll;

import ltd.qubit.commons.random.RandomBeanGenerator;

public class ValidatorTestBase {

  protected static Validator validator;
  protected static RandomBeanGenerator random = new RandomBeanGenerator();

  @BeforeAll
  public static void setUp() {
    Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }
}
