package com.example.dsl;

import com.example.core.ResourceHelper;
import com.example.model.DslElement;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Simulates a DSL validator that depends on both model and core.
 * Represents the kind of code in com.avaloq.tools.ddk.xtext.
 */
public class DslValidator {

  private final ResourceHelper resourceHelper;

  public DslValidator() {
    this.resourceHelper = new ResourceHelper();
  }

  public boolean isValidName(DslElement element) {
    QualifiedName qn = QualifiedName.create(element.getName().split("\\."));
    return qn.getSegmentCount() > 0
        && !qn.getLastSegment().isEmpty();
  }

  public ResourceHelper getResourceHelper() {
    return resourceHelper;
  }
}
