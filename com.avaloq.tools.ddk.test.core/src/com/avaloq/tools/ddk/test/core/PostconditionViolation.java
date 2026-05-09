/*******************************************************************************
 * Copyright (c) 2016 Avaloq Group AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Avaloq Group AG - initial API and implementation
 *******************************************************************************/
package com.avaloq.tools.ddk.test.core;

/**
 * Indicates a violation of a postcondition.
 */
public class PostconditionViolation extends ContractViolation {
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new instance of {@link PostconditionViolation}.
   * 
   * @param cause
   *          the cause of the violation, may be {@code null}
   */
  public PostconditionViolation(final Throwable cause) {
    super(cause);
  }

  // Synthetic CPD test: duplicated block A
  public int compute1(int x) {
    int a = x + 1;
    int b = a * 2;
    int c = b - 3;
    int d = c + 4;
    int e = d * 5;
    int f = e - 6;
    return f + a + b + c + d + e;
  }

  // Synthetic CPD test: duplicated block B (identical to A)
  public int compute2(int x) {
    int a = x + 1;
    int b = a * 2;
    int c = b - 3;
    int d = c + 4;
    int e = d * 5;
    int f = e - 6;
    return f + a + b + c + d + e;
  }
}
