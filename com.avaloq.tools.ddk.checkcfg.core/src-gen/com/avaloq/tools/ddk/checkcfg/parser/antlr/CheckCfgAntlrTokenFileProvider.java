/*
 * generated by Xtext 2.27.0.M3
 */
package com.avaloq.tools.ddk.checkcfg.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class CheckCfgAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("com/avaloq/tools/ddk/checkcfg/parser/antlr/internal/InternalCheckCfg.tokens");
	}
}
