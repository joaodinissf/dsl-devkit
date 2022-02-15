/*
 * generated by Xtext 2.25.0
 */
package com.avaloq.tools.ddk.xtext.export.ide;

import org.eclipse.xtext.util.Modules2;

import com.avaloq.tools.ddk.xtext.export.ExportRuntimeModule;
import com.avaloq.tools.ddk.xtext.export.ExportStandaloneSetup;
import com.google.inject.Guice;
import com.google.inject.Injector;


/**
 * Initialization support for running Xtext languages as language servers.
 */
public class ExportIdeSetup extends ExportStandaloneSetup {

  @Override
  public Injector createInjector() {
    return Guice.createInjector(Modules2.mixin(new ExportRuntimeModule(), new ExportIdeModule()));
  }

}
