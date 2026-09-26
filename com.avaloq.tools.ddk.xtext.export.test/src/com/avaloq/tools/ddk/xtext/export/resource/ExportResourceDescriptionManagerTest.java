/*******************************************************************************
 * Copyright (c) 2026 Avaloq Group AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Avaloq Group AG - initial API and implementation
 *******************************************************************************/
package com.avaloq.tools.ddk.xtext.export.resource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.TypesFactory;
import org.eclipse.xtext.common.types.access.ClasspathTypeProviderFactory;
import org.eclipse.xtext.common.types.access.impl.TypeResourceServices;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.junit.jupiter.api.Test;

import com.avaloq.tools.ddk.xtext.test.export.util.ExportTestUtil;
import com.avaloq.tools.ddk.xtext.test.jupiter.AbstractXtextTest;


/**
 * Tests that the Java types an export model refers to by name, in {@code extension} declarations and in expressions, are
 * dependencies of the model, and that the Java types referenced by its inferred JVM model are recorded in the form in which
 * changed Java types are reported: a change to such a type must make the model affected.
 */
@SuppressWarnings("nls")
public class ExportResourceDescriptionManagerTest extends AbstractXtextTest {

  /** Fully qualified name of the extension class; it need not exist, since the dependency is recorded by name. */
  private static final String EXTENSION_CLASS = "com.acme.export.util.NamingExtensions";

  private static final String WITH_EXTENSION = "WithExtension";

  private static final String ECORE_IMPORT = "import \"http://www.eclipse.org/emf/2002/Ecore\" as ecore\n\n";

  private static final String ECLASS_EXPORT = "export ecore::EClass as name\n";

  private static final String MODEL_WITH_EXTENSION = ECORE_IMPORT + "extension com::acme::^export::util::NamingExtensions\n\n" + ECLASS_EXPORT;

  private static final String MODEL_WITHOUT_EXTENSION = ECORE_IMPORT + ECLASS_EXPORT;

  /** Fully qualified name of a type named in a cast; it need not exist either. */
  private static final String CAST_TYPE = "com.acme.export.model.Node";

  private static final String MODEL_WITH_CAST = ECORE_IMPORT + "export ecore::EClass as name {\n  data node = (com::acme::^export::model::Node) this;\n}\n";

  @Override
  protected ExportTestUtil getXtextTestUtil() {
    return ExportTestUtil.getInstance();
  }

  /**
   * This test builds its sources in memory and has no test source file. {@inheritDoc}
   */
  @Override
  protected String getTestSourceFileName() {
    return null;
  }

  @Test
  public void testExtensionClassIsImportedName() throws IOException {
    final IResourceDescription description = describe(WITH_EXTENSION, MODEL_WITH_EXTENSION);
    assertTrue(imports(description, EXTENSION_CLASS), "The extension class must be an imported name of the export model.");
  }

  @Test
  public void testChangedExtensionClassAffectsModel() throws IOException {
    final XtextResource resource = parse(WITH_EXTENSION, MODEL_WITH_EXTENSION);
    final IResourceDescription.Manager manager = resource.getResourceServiceProvider().getResourceDescriptionManager();
    assertInstanceOf(ExportResourceDescriptionManager.class, manager, "Export must bind its own resource description manager.");
    final IResourceDescription candidate = manager.getResourceDescription(resource);
    assertTrue(manager.isAffected(List.of(javaTypeChange(EXTENSION_CLASS)), candidate, new ResourceDescriptionsData(List.of(candidate))),
        "A change to an extension class must affect the export model.");
  }

  @Test
  public void testChangedClassDoesNotAffectModelWithoutExtension() throws IOException {
    final XtextResource resource = parse("WithoutExtension", MODEL_WITHOUT_EXTENSION);
    final IResourceDescription.Manager manager = resource.getResourceServiceProvider().getResourceDescriptionManager();
    final IResourceDescription candidate = manager.getResourceDescription(resource);
    assertFalse(manager.isAffected(List.of(javaTypeChange(EXTENSION_CLASS)), candidate, new ResourceDescriptionsData(List.of(candidate))),
        "A class the export model does not declare must not affect it.");
  }

  @Test
  public void testTypeNamedInExpressionIsImportedName() throws IOException {
    final XtextResource resource = parse("WithCast", MODEL_WITH_CAST);
    assertTrue(resource.getErrors().isEmpty(), () -> "The model must parse: " + resource.getErrors());
    final IResourceDescription.Manager manager = resource.getResourceServiceProvider().getResourceDescriptionManager();
    final IResourceDescription candidate = manager.getResourceDescription(resource);
    assertTrue(imports(candidate, CAST_TYPE), "A type named in an expression must be an imported name of the export model.");
    assertTrue(manager.isAffected(List.of(javaTypeChange(CAST_TYPE)), candidate, new ResourceDescriptionsData(List.of(candidate))),
        "A change to a type named in an expression must affect the export model.");
  }

  @Test
  public void testTypesOfInferredModelAreDotSeparatedImportedNames() throws IOException {
    final ClassLoader loader = ExportResourceDescriptionManagerTest.class.getClassLoader();
    final XtextResource resource = getXtextTestUtil().parseWithoutGrammar(getTargetSourceUri("Resolved.export"), MODEL_WITHOUT_EXTENSION, resourceSet -> {
      resourceSet.setClasspathURIContext(loader);
      new ClasspathTypeProviderFactory(loader, getXtextTestUtil().get(TypeResourceServices.class)).createTypeProvider(resourceSet);
    });
    final IResourceDescription description = resource.getResourceServiceProvider().getResourceDescriptionManager().getResourceDescription(resource);
    assertTrue(imports(description, "org.eclipse.emf.ecore.EObject"),
        () -> "Types referenced by the inferred JVM model must be recorded as dot-separated names: " + description.getImportedNames());
    final QualifiedName singleSegment = QualifiedName.create("org.eclipse.emf.ecore.eobject");
    for (final QualifiedName name : description.getImportedNames()) {
      assertFalse(singleSegment.equals(name), "A Java type name must not be recorded as a single segment.");
    }
  }

  private static boolean imports(final IResourceDescription description, final String fqn) {
    final QualifiedName expected = QualifiedName.create(fqn.split("\\.")).toLowerCase();
    for (final QualifiedName name : description.getImportedNames()) {
      if (expected.equals(name)) {
        return true;
      }
    }
    return false;
  }

  private IResourceDescription describe(final String name, final String source) throws IOException {
    final XtextResource resource = parse(name, source);
    return resource.getResourceServiceProvider().getResourceDescriptionManager().getResourceDescription(resource);
  }

  /**
   * Returns a change of the given Java type shaped like the ones JDT reports: the resource URI {@code java:/Objects/<FQN>},
   * exporting one object named by the fully qualified name.
   */
  private static IResourceDescription.Delta javaTypeChange(final String fqn) {
    final URI uri = URI.createURI("java:/Objects/" + fqn);
    final JvmGenericType type = TypesFactory.eINSTANCE.createJvmGenericType();
    ((InternalEObject) type).eSetProxyURI(uri.appendFragment(fqn));
    final IEObjectDescription exported = EObjectDescription.create(QualifiedName.create(fqn.split("\\.")), type);
    final IResourceDescription changed = new AbstractResourceDescription() {
      @Override
      protected List<IEObjectDescription> computeExportedObjects() {
        return List.of(exported);
      }

      @Override
      public Iterable<QualifiedName> getImportedNames() {
        return List.of();
      }

      @Override
      public Iterable<IReferenceDescription> getReferenceDescriptions() {
        return List.of();
      }

      @Override
      public URI getURI() {
        return uri;
      }
    };
    return new DefaultResourceDescriptionDelta(null, changed);
  }

  private XtextResource parse(final String name, final String source) throws IOException {
    return getXtextTestUtil().parseWithoutGrammar(getTargetSourceUri(name + ".export"), source);
  }
}
