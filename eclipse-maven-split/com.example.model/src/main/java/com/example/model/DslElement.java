package com.example.model;

import org.eclipse.emf.ecore.EObject;

/**
 * Base model element — represents what com.avaloq.tools.ddk would contain.
 */
public class DslElement {

  private final String name;
  private final String type;

  public DslElement(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public boolean isEObject(Object obj) {
    return obj instanceof EObject;
  }
}
