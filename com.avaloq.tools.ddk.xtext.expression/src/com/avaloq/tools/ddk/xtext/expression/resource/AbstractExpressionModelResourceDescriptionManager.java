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
package com.avaloq.tools.ddk.xtext.expression.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.xbase.resource.XbaseResourceDescription;
import org.eclipse.xtext.xbase.resource.XbaseResourceDescriptionManager;
import org.eclipse.xtext.xbase.typesystem.IBatchTypeResolver;

import com.avaloq.tools.ddk.xtext.expression.expression.Identifier;


/**
 * Resource description manager for languages whose models refer to Java types by name, in expressions or declarations that
 * are only resolved while the model's Java is generated.
 * <p>
 * Such a name is neither a reference nor, since it is not part of the inferred JVM model, an imported name of the model, so a
 * change to the named type would not re-queue the model and its generated code could stay stale. The descriptions created by
 * this manager therefore add the names returned by {@link #getJavaTypeNamesReferencedByName(Resource)} to the imported
 * names, in the form in which changed Java types are reported (dot-separated segments, lower case).
 * </p>
 * <p>
 * The imported names of the types referenced by the inferred JVM model are also computed with a dot-separated name
 * converter. The language's own {@link IQualifiedNameConverter} may use another delimiter (for example {@code ::}), which
 * would turn a Java type name into a single segment that never matches a changed type.
 * </p>
 */
public abstract class AbstractExpressionModelResourceDescriptionManager extends XbaseResourceDescriptionManager {

  /** Converts Java type names as they are reported for changed Java types. */
  private static final IQualifiedNameConverter JAVA_NAME_CONVERTER = new IQualifiedNameConverter.DefaultImpl();

  /** Delimiter of the segments of a Java type name. */
  private static final String JAVA_DELIMITER = "."; //$NON-NLS-1$

  @Override
  protected IResourceDescription createResourceDescription(final Resource resource, final IDefaultResourceDescriptionStrategy strategy) {
    return new ExpressionModelResourceDescription(resource, strategy, getCache(), typeResolver, this);
  }

  /**
   * Returns the fully qualified, dot-separated names of the Java types that the model in the given resource refers to by
   * name only, without a reference that the builder could track.
   *
   * @param resource
   *          the resource, must not be {@code null}
   * @return the names, never {@code null}
   */
  protected abstract Set<String> getJavaTypeNamesReferencedByName(Resource resource);

  /**
   * Adds the names of the qualified types named in the expressions contained in the given model element, such as casts,
   * {@code typeSelect} arguments and the receivers of static calls ({@code a::b::C.op()}). Only qualified names are added:
   * an unqualified name denotes a model type or a variable.
   *
   * @param root
   *          the model element whose contents are searched, must not be {@code null}
   * @param names
   *          the set to add the dot-separated names to, must not be {@code null}
   */
  protected static void addExpressionTypeNames(final EObject root, final Set<String> names) {
    final TreeIterator<EObject> contents = root.eAllContents();
    while (contents.hasNext()) {
      if (contents.next() instanceof Identifier identifier && identifier.getId().size() > 1) {
        // a language may keep the escape character of keyword segments, e.g. ^export; Java names never contain it
        names.add(identifier.getId().stream()
            .map(AbstractExpressionModelResourceDescriptionManager::unescape).collect(Collectors.joining(JAVA_DELIMITER)));
      }
    }
  }

  /**
   * Returns the given name segment without a leading escape character.
   *
   * @param segment
   *          the segment, must not be {@code null}
   * @return the unescaped segment, never {@code null}
   */
  private static String unescape(final String segment) {
    return segment.startsWith("^") ? segment.substring(1) : segment; //$NON-NLS-1$
  }

  /**
   * Adds the Java class name corresponding to the given {@code ::} delimited qualified ID, as used in {@code extension}
   * declarations.
   *
   * @param qualifiedId
   *          the qualified ID, may be {@code null}
   * @param names
   *          the set to add the dot-separated name to, must not be {@code null}
   */
  protected static void addQualifiedIdName(final String qualifiedId, final Set<String> names) {
    if (qualifiedId != null && !qualifiedId.isBlank()) {
      names.add(Arrays.stream(qualifiedId.split("::")) //$NON-NLS-1$
          .map(AbstractExpressionModelResourceDescriptionManager::unescape).collect(Collectors.joining(JAVA_DELIMITER)));
    }
  }

  /**
   * A description whose imported names also contain the Java types the model refers to by name, and whose names of Java
   * types referenced by the inferred JVM model are dot-separated.
   */
  private static final class ExpressionModelResourceDescription extends XbaseResourceDescription {

    private final AbstractExpressionModelResourceDescriptionManager manager;

    private Set<QualifiedName> importedNames;

    ExpressionModelResourceDescription(final Resource resource, final IDefaultResourceDescriptionStrategy strategy, final IResourceScopeCache cache,
        final IBatchTypeResolver typeResolver, final AbstractExpressionModelResourceDescriptionManager manager) {
      super(resource, strategy, cache, typeResolver, JAVA_NAME_CONVERTER);
      this.manager = manager;
    }

    @Override
    public Iterable<QualifiedName> getImportedNames() {
      if (importedNames == null) {
        final Set<QualifiedName> names = new LinkedHashSet<>();
        super.getImportedNames().forEach(names::add);
        for (final String name : manager.getJavaTypeNamesReferencedByName(getResource())) {
          names.add(JAVA_NAME_CONVERTER.toQualifiedName(name).toLowerCase());
        }
        importedNames = Collections.unmodifiableSet(names);
      }
      return importedNames;
    }
  }
}
