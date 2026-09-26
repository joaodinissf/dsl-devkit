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
package com.avaloq.tools.ddk.xtext.scope.resource;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;

import com.avaloq.tools.ddk.xtext.expression.resource.AbstractExpressionModelResourceDescriptionManager;
import com.avaloq.tools.ddk.xtext.scope.generator.ScopeProviderX;
import com.avaloq.tools.ddk.xtext.scope.scope.Extension;
import com.avaloq.tools.ddk.xtext.scope.scope.ScopeModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;


/**
 * Resource description manager for scope models: the classes named in {@code extension} declarations and the qualified types
 * named in expressions, of the model and of the scope models it includes, are dependencies of the model. The generated scope
 * provider uses the extensions and rules of included models as well.
 */
@Singleton
public class ScopeResourceDescriptionManager extends AbstractExpressionModelResourceDescriptionManager {

  @Inject
  private ScopeProviderX scopeProviderX;

  @Override
  protected Set<String> getJavaTypeNamesReferencedByName(final Resource resource) {
    if (resource.getContents().isEmpty() || !(resource.getContents().get(0) instanceof ScopeModel model)) {
      return Set.of();
    }
    final Set<String> names = new LinkedHashSet<>();
    for (final Extension declaration : scopeProviderX.allExtensions(model)) {
      addQualifiedIdName(declaration.getExtension(), names);
    }
    addModelAndIncludes(model, new LinkedHashSet<>(), names);
    return names;
  }

  /**
   * Adds the qualified types named in the expressions of the given scope model and, transitively, of the models it includes.
   *
   * @param model
   *          the scope model, must not be {@code null}
   * @param visited
   *          the models already visited, guarding against include cycles, must not be {@code null}
   * @param names
   *          the set to add the names to, must not be {@code null}
   */
  private static void addModelAndIncludes(final ScopeModel model, final Set<ScopeModel> visited, final Set<String> names) {
    if (!visited.add(model)) {
      return;
    }
    addExpressionTypeNames(model, names);
    for (final ScopeModel included : model.getIncludedScopes()) {
      if (included != null && !included.eIsProxy()) {
        addModelAndIncludes(included, visited, names);
      }
    }
  }
}
