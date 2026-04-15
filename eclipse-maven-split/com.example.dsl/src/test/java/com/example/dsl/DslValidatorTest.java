package com.example.dsl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.model.DslElement;
import org.junit.jupiter.api.Test;

class DslValidatorTest {

  @Test
  void validatesSimpleName() {
    DslValidator validator = new DslValidator();
    DslElement el = new DslElement("myRule", "Check");
    assertTrue(validator.isValidName(el));
  }

  @Test
  void validatesQualifiedName() {
    DslValidator validator = new DslValidator();
    DslElement el = new DslElement("com.example.myRule", "Check");
    assertTrue(validator.isValidName(el));
  }

  @Test
  void hasResourceHelper() {
    DslValidator validator = new DslValidator();
    assertNotNull(validator.getResourceHelper());
  }
}
