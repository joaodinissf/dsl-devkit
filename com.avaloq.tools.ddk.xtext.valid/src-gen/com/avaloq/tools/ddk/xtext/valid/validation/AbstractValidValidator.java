/*
 * generated by Xtext 2.27.0.M1
 */
package com.avaloq.tools.ddk.xtext.valid.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;

public abstract class AbstractValidValidator extends AbstractDeclarativeValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>();
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.avaloq.com/tools/ddk/xtext/valid/Valid"));
		return result;
	}
}
