package com.example.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import com.example.core.ResourceHelper;

/**
 * A minimal Eclipse view that uses the core module.
 * This proves the Tycho-built UI plugin can consume
 * the pure-Maven-built core jar.
 */
public class CoreInfoView extends ViewPart {

  public static final String ID = "com.example.ui.coreInfoView";

  @Override
  public void createPartControl(Composite parent) {
    ResourceHelper helper = new ResourceHelper();
    Label label = new Label(parent, SWT.NONE);
    label.setText("Core module loaded: " + helper.getClass().getName()
        + " from " + helper.getClass().getProtectionDomain().getCodeSource().getLocation());
  }

  @Override
  public void setFocus() {
    // nothing
  }
}
