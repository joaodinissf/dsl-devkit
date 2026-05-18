package com.avaloq.poc;

import java.io.File;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.XtextStandaloneSetup;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.XbaseStandaloneSetup;

import io.typefox.xtext2langium.Xtext2LangiumFragment;

/**
 * Standalone driver for the xtext2langium MWE2 fragment. Invokes
 * Xtext2LangiumFragment.generate() against a grammar loaded from the file
 * system, bypassing MWE2's workflow parser (which trips on JDK type lookup
 * when run from the Maven CLI). Optional .ecore files are preloaded so that
 * cross-resource references in the grammar resolve.
 *
 * Usage: RunX2L &lt;grammar.xtext path&gt; &lt;output dir&gt; [ecore1 ecore2 ...]
 */
public class RunX2L {

  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage: RunX2L <grammar.xtext path> <output dir> [ecore...]");
      System.exit(2);
    }

    String grammarPath = args[0];
    String outputPath = args[1];

    // Initialize Xtext + Xbase metamodels (Xbase is needed for any grammar
    // that 'with org.eclipse.xtext.xbase.Xbase' or similar)
    new XtextStandaloneSetup().createInjectorAndDoEMFRegistration();
    new XbaseStandaloneSetup().createInjectorAndDoEMFRegistration();

    XtextResourceSet rs = new XtextResourceSet();
    rs.addLoadOption(org.eclipse.xtext.resource.XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);

    // Preload .ecore files so the grammar can reference EPackages by URI/import
    for (int i = 2; i < args.length; i++) {
      File ecore = new File(args[i]);
      URI ecoreUri = URI.createFileURI(ecore.getAbsolutePath());
      Resource ecoreRes = rs.getResource(ecoreUri, true);
      System.out.println("Loaded ecore: " + ecoreUri + " (" + ecoreRes.getContents().size() + " roots)");
      // Register the EPackage(s) so cross-references can find them
      for (EObject root : ecoreRes.getContents()) {
        if (root instanceof org.eclipse.emf.ecore.EPackage) {
          org.eclipse.emf.ecore.EPackage pkg = (org.eclipse.emf.ecore.EPackage) root;
          rs.getPackageRegistry().put(pkg.getNsURI(), pkg);
          System.out.println("  registered EPackage: " + pkg.getNsURI());
        }
      }
    }

    File grammarFile = new File(grammarPath);
    URI grammarUri = URI.createFileURI(grammarFile.getAbsolutePath());
    Resource grammarRes = rs.getResource(grammarUri, true);
    EcoreUtil.resolveAll(rs);

    // Diagnose unresolved references
    List<Resource.Diagnostic> errors = grammarRes.getErrors();
    if (!errors.isEmpty()) {
      System.err.println("Grammar load errors:");
      for (Resource.Diagnostic d : errors) {
        System.err.println("  " + d.getMessage() + " @ line " + d.getLine());
      }
    }

    if (grammarRes.getContents().isEmpty()) {
      System.err.println("Grammar resource is empty: " + grammarUri);
      System.exit(1);
    }
    final Grammar grammar = (Grammar) grammarRes.getContents().get(0);
    System.out.println("Loaded grammar: " + grammar.getName());

    new File(outputPath).mkdirs();

    Xtext2LangiumFragment frag = new Xtext2LangiumFragment() {
      @Override
      protected Grammar getGrammar() {
        return grammar;
      }
    };
    frag.setOutputPath(outputPath);
    // Use defaults for: prefixEnumLiterals=true, useStringAsEnumRuleType=false,
    // generateEcoreTypes=false, removeOverridenRules=true

    try {
      frag.generate();
      System.out.println("Done. Output in: " + new File(outputPath).getAbsolutePath());
    } catch (Throwable t) {
      System.err.println("Generation failed: " + t.getClass().getSimpleName() + ": " + t.getMessage());
      t.printStackTrace();
      System.exit(1);
    }
  }
}
