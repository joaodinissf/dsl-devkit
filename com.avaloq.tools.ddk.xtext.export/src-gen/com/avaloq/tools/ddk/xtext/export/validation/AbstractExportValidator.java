/*
 * generated by Xtext 2.27.0.M1
 */
package com.avaloq.tools.ddk.xtext.export.validation;

import com.avaloq.tools.ddk.xtext.expression.validation.ExpressionValidator;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;

public abstract class AbstractExportValidator extends ExpressionValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>(super.getEPackages());
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.avaloq.com/tools/ddk/xtext/export/Export"));
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.avaloq.com/tools/ddk/xtext/expression/Expression"));
		return result;
	}
}
