/*
 * generated by Xtext 2.25.0
 */
package com.avaloq.tools.ddk.xtext.export.parser.antlr;

import com.avaloq.tools.ddk.xtext.export.parser.antlr.internal.InternalExportParser;
import com.avaloq.tools.ddk.xtext.export.services.ExportGrammarAccess;
import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class ExportParser extends AbstractAntlrParser {

	@Inject
	private ExportGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalExportParser createParser(XtextTokenStream stream) {
		return new InternalExportParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "ExportModel";
	}

	public ExportGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(ExportGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
