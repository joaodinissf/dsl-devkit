package com.example.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.xtext.naming.QualifiedName;
import org.junit.jupiter.api.Test;

/**
 * Plain JUnit 5 test — no OSGi container, no Eclipse runtime.
 * Validates that Xtext/EMF APIs work standalone.
 */
class ResourceHelperTest {

  @Test
  void qualifiedNameCreation() {
    QualifiedName name = QualifiedName.create("com", "example", "MyDsl");
    assertEquals("com.example.MyDsl", name.toString());
    assertEquals(3, name.getSegmentCount());
  }

  @Test
  void qualifiedNameEquality() {
    QualifiedName a = QualifiedName.create("org", "test");
    QualifiedName b = QualifiedName.create("org", "test");
    assertEquals(a, b);
  }

  @Test
  void resourceHelperInstantiates() {
    ResourceHelper helper = new ResourceHelper();
    assertNotNull(helper);
  }
}
