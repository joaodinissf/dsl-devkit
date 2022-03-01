/*
 * generated by Xtext 2.26.0
 */
package com.avaloq.tools.ddk.checkcfg.ui;

import com.avaloq.tools.ddk.checkcfg.ui.internal.CheckcfgActivator;
import com.google.inject.Injector;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class CheckCfgExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(CheckcfgActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		CheckcfgActivator activator = CheckcfgActivator.getInstance();
		return activator != null ? activator.getInjector(CheckcfgActivator.COM_AVALOQ_TOOLS_DDK_CHECKCFG_CHECKCFG) : null;
	}

}
