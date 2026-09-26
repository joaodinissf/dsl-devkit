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
package com.avaloq.tools.ddk.xtext.generator.expression;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.jupiter.api.Test;

import com.avaloq.tools.ddk.xtext.scope.ScopeStandaloneSetup;
import com.google.inject.Injector;


/**
 * Tests that the Java types a scope model refers to by name, in {@code extension} declarations and in expressions, of the
 * model and of the scope models it includes, are imported names of the model, so that a change to such a type affects it.
 */
@SuppressWarnings("nls")
public class ScopeExtensionDependencyTest {

  private static final String ECORE_IMPORT = "\n\nimport \"http://www.eclipse.org/emf/2002/Ecore\" as ecore\n\n";

  private static final String BASE = "scoping test.Base" + ECORE_IMPORT + "extension com::acme::util::BaseExtensions\n\n"
      + "naming {\n  ecore::EClass = factory com::acme::util::Names.nameOf(this);\n}\n";

  private static final String MAIN = "scoping test.Main with test.Base" + ECORE_IMPORT + "extension com::acme::^scope::MainExtensions\n";

  private static final String PLAIN = "scoping test.Plain" + ECORE_IMPORT;

  private static final String MAIN_EXTENSION_CLASS = "com.acme.scope.MainExtensions";

  private static final String FACTORY_CLASS = "com.acme.util.Names";

  private static final String NOT_NAMED = "A class the model does not name must not be an imported name.";

  /** Not registered globally: all test suites share one runtime, which must keep the plug-in's registration. */
  private final Injector injector = new ScopeStandaloneSetup().createInjector();

  @Test
  void testOwnAndIncludedDependenciesAreImportedNames() throws IOException {
    final XtextResourceSet resourceSet = newResourceSet();
    final Resource base = load(resourceSet, "Base", BASE);
    final Resource main = load(resourceSet, "Main", MAIN);
    EcoreUtil.resolveAll(resourceSet);
    assertTrue(base.getErrors().isEmpty(), () -> "The included model must parse: " + base.getErrors());
    assertTrue(main.getErrors().isEmpty(), () -> "The model must parse: " + main.getErrors());
    final IResourceDescription description = describe(main);
    assertTrue(imports(description, MAIN_EXTENSION_CLASS), "The model's own extension class must be an imported name.");
    assertTrue(imports(description, "com.acme.util.BaseExtensions"), "The included model's extension class must be an imported name.");
    assertTrue(imports(description, FACTORY_CLASS), "A factory type named in an included model must be an imported name.");
  }

  @Test
  void testModelWithoutDependenciesImportsNoneOfThem() throws IOException {
    final XtextResourceSet resourceSet = newResourceSet();
    final Resource plain = load(resourceSet, "Plain", PLAIN);
    EcoreUtil.resolveAll(resourceSet);
    final IResourceDescription description = describe(plain);
    assertFalse(imports(description, MAIN_EXTENSION_CLASS), NOT_NAMED);
    assertFalse(imports(description, FACTORY_CLASS), NOT_NAMED);
  }

  /**
   * Returns a resource set that creates scope resources with this test's injector, without changing the global registry.
   */
  private XtextResourceSet newResourceSet() {
    final XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("scope", injector.getInstance(IResourceFactory.class));
    return resourceSet;
  }

  private static Resource load(final XtextResourceSet resourceSet, final String name, final String source) throws IOException {
    final Resource resource = resourceSet.createResource(URI.createURI("memory:/" + name + ".scope"));
    resource.load(new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)), null);
    return resource;
  }

  private IResourceDescription describe(final Resource resource) {
    return injector.getInstance(IResourceDescription.Manager.class).getResourceDescription(resource);
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
}
