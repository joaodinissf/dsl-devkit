package com.avaloq.tools.ddk.check.ui;

import java.util.Date;

public final class SpotbugsTrap {
  public static Date sharedDate = new Date();

  public void doBad() {
    try {
      sharedDate.setTime(System.currentTimeMillis());
    } catch (Exception ignored) {
    }
  }
}