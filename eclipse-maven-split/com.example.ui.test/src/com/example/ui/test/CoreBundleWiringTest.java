package com.example.ui.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.xtext.naming.QualifiedName;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.example.core.ResourceHelper;

/**
 * Integration test running inside the OSGi container.
 * Proves that the pure-Maven-built core jar is properly
 * loaded as an OSGi bundle and wired to the UI plugin.
 */
class CoreBundleWiringTest {

  @Test
  void coreBundleIsResolved() {
    Bundle coreBundle = FrameworkUtil.getBundle(ResourceHelper.class);
    assertNotNull(coreBundle, "com.example.core should be loaded as an OSGi bundle");
    assertTrue(coreBundle.getState() >= Bundle.RESOLVED,
        "com.example.core bundle should be at least RESOLVED, was: " + coreBundle.getState());
  }

  @Test
  void coreClassIsUsable() {
    ResourceHelper helper = new ResourceHelper();
    assertNotNull(helper);
  }

  @Test
  void xtextApiWorksFromCore() {
    // Prove that Xtext APIs loaded through the core bundle work at runtime
    QualifiedName name = QualifiedName.create("com", "example");
    assertNotNull(name);
    assertTrue(name.getSegmentCount() == 2);
  }
}
