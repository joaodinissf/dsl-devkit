/*
 * generated by Xtext 2.27.0.M1
 */
package com.avaloq.tools.ddk.xtext.scope.parser.antlr;

import com.avaloq.tools.ddk.xtext.scope.parser.antlr.internal.InternalScopeParser;
import com.avaloq.tools.ddk.xtext.scope.services.ScopeGrammarAccess;
import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class ScopeParser extends AbstractAntlrParser {

	@Inject
	private ScopeGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalScopeParser createParser(XtextTokenStream stream) {
		return new InternalScopeParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "ScopeModel";
	}

	public ScopeGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(ScopeGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
