package com.example.core;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

/**
 * Demonstrates a "core" class that uses Xtext and EMF APIs
 * without any Eclipse UI dependency. This is representative of
 * the kind of code in dsl-devkit core plugins.
 */
public class ResourceHelper {

  /**
   * Look up exported objects by qualified name in an Xtext index.
   */
  public Iterable<IEObjectDescription> findByName(
      IResourceDescriptions index, String... segments) {
    QualifiedName qn = QualifiedName.create(segments);
    return index.getExportedObjects(EcorePackage.Literals.EOBJECT, qn, false);
  }

  /**
   * Build a qualified name from an EObject's URI fragment.
   */
  public QualifiedName qualifiedNameOf(EObject object) {
    String fragment = object.eResource().getURIFragment(object);
    return QualifiedName.create(fragment.split("/"));
  }
}
