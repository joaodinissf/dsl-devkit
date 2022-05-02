/*
 * generated by Xtext 2.27.0.M1
 */
package com.avaloq.tools.ddk.sample.helloworld.parser.antlr;

import com.avaloq.tools.ddk.sample.helloworld.grammar.AbstractHelloWorldSemanticPredicates;
import com.avaloq.tools.ddk.sample.helloworld.parser.antlr.internal.InternalHelloWorldParser;
import com.avaloq.tools.ddk.sample.helloworld.services.HelloWorldGrammarAccess;
import com.avaloq.tools.ddk.xtext.parser.ISemanticPredicates;
import com.avaloq.tools.ddk.xtext.parser.antlr.AbstractContextualAntlrParser;
import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class HelloWorldParser extends AbstractContextualAntlrParser {

  @Inject
  private HelloWorldGrammarAccess grammarAccess;

  @Inject
  private ISemanticPredicates predicates;

  @Override
  protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
    tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
  }


  @Override
  protected InternalHelloWorldParser createParser(XtextTokenStream stream) {
    return new InternalHelloWorldParser(stream, getGrammarAccess(), createParserContext(), (AbstractHelloWorldSemanticPredicates) predicates);
  }

  @Override
  protected String getDefaultRuleName() {
    return "Model";
  }

  public HelloWorldGrammarAccess getGrammarAccess() {
    return this.grammarAccess;
  }

  public void setGrammarAccess(HelloWorldGrammarAccess grammarAccess) {
    this.grammarAccess = grammarAccess;
  }
}
