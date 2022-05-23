/*
 * generated by Xtext 2.27.0.M3
 */
package com.avaloq.tools.ddk.sample.helloworld.ui;

import com.avaloq.tools.ddk.sample.helloworld.ui.internal.HelloworldActivator;
import com.google.inject.Injector;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class HelloWorldExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(HelloworldActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		HelloworldActivator activator = HelloworldActivator.getInstance();
		return activator != null ? activator.getInjector(HelloworldActivator.COM_AVALOQ_TOOLS_DDK_SAMPLE_HELLOWORLD_HELLOWORLD) : null;
	}

}
