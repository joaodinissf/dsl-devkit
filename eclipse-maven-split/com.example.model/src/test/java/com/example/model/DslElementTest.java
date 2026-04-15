package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class DslElementTest {

  @Test
  void basicProperties() {
    DslElement el = new DslElement("greeting", "String");
    assertEquals("greeting", el.getName());
    assertEquals("String", el.getType());
  }

  @Test
  void nonEObject() {
    DslElement el = new DslElement("x", "y");
    assertFalse(el.isEObject("not an eobject"));
  }
}
