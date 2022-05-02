/*
 * generated by Xtext 2.27.0.M1
 */
grammar InternalHelloWorld;

options {
	superClass=AbstractInternalAntlrParser;
}

@lexer::header {
package com.avaloq.tools.ddk.sample.helloworld.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import.
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package com.avaloq.tools.ddk.sample.helloworld.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import com.avaloq.tools.ddk.sample.helloworld.services.HelloWorldGrammarAccess;
import com.avaloq.tools.ddk.sample.helloworld.grammar.AbstractHelloWorldSemanticPredicates;
import com.avaloq.tools.ddk.xtext.parser.antlr.ParserContext;

}

@parser::members {

    private HelloWorldGrammarAccess grammarAccess;
    private AbstractHelloWorldSemanticPredicates predicates;
    private ParserContext parserContext;

    public InternalHelloWorldParser(TokenStream input, HelloWorldGrammarAccess grammarAccess, ParserContext parserContext, AbstractHelloWorldSemanticPredicates predicates) {
        this(input);
        this.grammarAccess = grammarAccess;
        this.predicates = predicates;
        this.parserContext = parserContext;
        parserContext.setTokenStream(input);
        registerRules(grammarAccess.getGrammar());
    }

    /**
     * Set token stream in parser context.
     * @param input Token stream
     */
    @Override
    public void setTokenStream(TokenStream input) {
      super.setTokenStream(input);
      if(parserContext != null){
        parserContext.setTokenStream(input);
      }
    }

    @Override
    protected String getFirstRuleName() {
      return "Model";
    }

    @Override
    protected HelloWorldGrammarAccess getGrammarAccess() {
      return grammarAccess;
    }

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleModel
entryRuleModel returns [EObject current=null]:
  { newCompositeNode(grammarAccess.getModelRule()); }
  iv_ruleModel=ruleModel
  { $current=$iv_ruleModel.current; }
  EOF;

// Rule Model
ruleModel returns [EObject current=null]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  (
    (
      (
      	{
      	  newCompositeNode(grammarAccess.getModelAccess().getGreetingsGreetingParserRuleCall_0_0());
      	}
      	lv_greetings_0_0=ruleGreeting
      	{
      	  if ($current==null) {
      	    $current = createModelElementForParent(grammarAccess.getModelRule());
      	  }
      	  add(
      	    $current,
      	    "greetings",
      	    lv_greetings_0_0,
      	    "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.Greeting");
      	  afterParserOrEnumRuleCall();
      	}
      )
    )*
    (
      (
      	{
      	  newCompositeNode(grammarAccess.getModelAccess().getKeywordsExampleKeywordsExampleParserRuleCall_1_0());
      	}
      	lv_keywordsExample_1_0=ruleKeywordsExample
      	{
      	  if ($current==null) {
      	    $current = createModelElementForParent(grammarAccess.getModelRule());
      	  }
      	  set(
      	    $current,
      	    "keywordsExample",
      	    lv_keywordsExample_1_0,
      	    "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.KeywordsExample");
      	  afterParserOrEnumRuleCall();
      	}
      )
    )?
  )
;

// Entry rule entryRuleGreeting
entryRuleGreeting returns [EObject current=null]:
  { newCompositeNode(grammarAccess.getGreetingRule()); }
  iv_ruleGreeting=ruleGreeting
  { $current=$iv_ruleGreeting.current; }
  EOF;

// Rule Greeting
ruleGreeting returns [EObject current=null]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  (
    otherlv_0='Hello'
    {
      newLeafNode(otherlv_0, grammarAccess.getGreetingAccess().getHelloKeyword_0());
    }
    (
      (
      	lv_name_1_0=RULE_ID
      	{
      	  newLeafNode(lv_name_1_0, grammarAccess.getGreetingAccess().getNameIDTerminalRuleCall_1_0());
      	}
      	{
      	  if ($current==null) {
      	    $current = createModelElement(grammarAccess.getGreetingRule());
      	  }
      	  setWithLastConsumed(
      	    $current,
      	    "name",
      	    lv_name_1_0,
      	    "org.eclipse.xtext.common.Terminals.ID");
      	}
      )
    )
    otherlv_2='!'
    {
      newLeafNode(otherlv_2, grammarAccess.getGreetingAccess().getExclamationMarkKeyword_2());
    }
  )
;

// Entry rule entryRuleKeywordsExample
entryRuleKeywordsExample returns [EObject current=null]:
  { newCompositeNode(grammarAccess.getKeywordsExampleRule()); }
  iv_ruleKeywordsExample=ruleKeywordsExample
  { $current=$iv_ruleKeywordsExample.current; }
  EOF;

// Rule KeywordsExample
ruleKeywordsExample returns [EObject current=null]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  (
    (
      (
      	{predicates.isKeyOneEnabled(parserContext)}?=>
      	{
      	  newCompositeNode(grammarAccess.getKeywordsExampleAccess().getOptionOptionOneParserRuleCall_0_0());
      	}
      	lv_option_0_0=ruleOptionOne
      	{
      	  if ($current==null) {
      	    $current = createModelElementForParent(grammarAccess.getKeywordsExampleRule());
      	  }
      	  set(
      	    $current,
      	    "option",
      	    lv_option_0_0,
      	    "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.OptionOne");
      	  afterParserOrEnumRuleCall();
      	}
      )
    )
        |
    (
      (
      	{predicates.isKeyTwoEnabled(parserContext)}?=>
      	{
      	  newCompositeNode(grammarAccess.getKeywordsExampleAccess().getOptionOptionTwoParserRuleCall_1_0());
      	}
      	lv_option_1_0=ruleOptionTwo
      	{
      	  if ($current==null) {
      	    $current = createModelElementForParent(grammarAccess.getKeywordsExampleRule());
      	  }
      	  set(
      	    $current,
      	    "option",
      	    lv_option_1_0,
      	    "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.OptionTwo");
      	  afterParserOrEnumRuleCall();
      	}
      )
    )
        |
    (
      (
      	{
      	  newCompositeNode(grammarAccess.getKeywordsExampleAccess().getOptionOptionThreeParserRuleCall_2_0());
      	}
      	lv_option_2_0=ruleOptionThree
      	{
      	  if ($current==null) {
      	    $current = createModelElementForParent(grammarAccess.getKeywordsExampleRule());
      	  }
      	  set(
      	    $current,
      	    "option",
      	    lv_option_2_0,
      	    "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.OptionThree");
      	  afterParserOrEnumRuleCall();
      	}
      )
    )
        |
    (
      (
      	{
      	  newCompositeNode(grammarAccess.getKeywordsExampleAccess().getOptionOptionFourParserRuleCall_3_0());
      	}
      	lv_option_3_0=ruleOptionFour
      	{
      	  if ($current==null) {
      	    $current = createModelElementForParent(grammarAccess.getKeywordsExampleRule());
      	  }
      	  set(
      	    $current,
      	    "option",
      	    lv_option_3_0,
      	    "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.OptionFour");
      	  afterParserOrEnumRuleCall();
      	}
      )
    )
  )
;

// Entry rule entryRuleOptionOne
entryRuleOptionOne returns [String current=null]:
  { newCompositeNode(grammarAccess.getOptionOneRule()); }
  iv_ruleOptionOne=ruleOptionOne
  { $current=$iv_ruleOptionOne.current.getText(); }
  EOF;

// Rule OptionOne
ruleOptionOne returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  {
    newCompositeNode(grammarAccess.getOptionOneAccess().getKeyOneParserRuleCall());
  }
  this_KeyOne_0=ruleKeyOne
  {
    $current.merge(this_KeyOne_0);
  }
  {
    afterParserOrEnumRuleCall();
  }
;

// Entry rule entryRuleOptionTwo
entryRuleOptionTwo returns [String current=null]:
  { newCompositeNode(grammarAccess.getOptionTwoRule()); }
  iv_ruleOptionTwo=ruleOptionTwo
  { $current=$iv_ruleOptionTwo.current.getText(); }
  EOF;

// Rule OptionTwo
ruleOptionTwo returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  (
    {
      newCompositeNode(grammarAccess.getOptionTwoAccess().getKeyTwoParserRuleCall_0());
    }
    this_KeyTwo_0=ruleKeyTwo
    {
      $current.merge(this_KeyTwo_0);
    }
    {
      afterParserOrEnumRuleCall();
    }
    {
      newCompositeNode(grammarAccess.getOptionTwoAccess().getKeyOtherParserRuleCall_1());
    }
    this_KeyOther_1=ruleKeyOther
    {
      $current.merge(this_KeyOther_1);
    }
    {
      afterParserOrEnumRuleCall();
    }
  )
;

// Entry rule entryRuleOptionThree
entryRuleOptionThree returns [String current=null]:
  { newCompositeNode(grammarAccess.getOptionThreeRule()); }
  iv_ruleOptionThree=ruleOptionThree
  { $current=$iv_ruleOptionThree.current.getText(); }
  EOF;

// Rule OptionThree
ruleOptionThree returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  {
    newCompositeNode(grammarAccess.getOptionThreeAccess().getSimpleKeyFourParserRuleCall());
  }
  this_SimpleKeyFour_0=ruleSimpleKeyFour
  {
    $current.merge(this_SimpleKeyFour_0);
  }
  {
    afterParserOrEnumRuleCall();
  }
;

// Entry rule entryRuleOptionFour
entryRuleOptionFour returns [String current=null]:
  { newCompositeNode(grammarAccess.getOptionFourRule()); }
  iv_ruleOptionFour=ruleOptionFour
  { $current=$iv_ruleOptionFour.current.getText(); }
  EOF;

// Rule OptionFour
ruleOptionFour returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  (
    {
      newCompositeNode(grammarAccess.getOptionFourAccess().getSimpleKeyFiveParserRuleCall_0());
    }
    this_SimpleKeyFive_0=ruleSimpleKeyFive
    {
      $current.merge(this_SimpleKeyFive_0);
    }
    {
      afterParserOrEnumRuleCall();
    }
    {
      newCompositeNode(grammarAccess.getOptionFourAccess().getEnumLikeOtherParserRuleCall_1());
    }
    this_EnumLikeOther_1=ruleEnumLikeOther
    {
      $current.merge(this_EnumLikeOther_1);
    }
    {
      afterParserOrEnumRuleCall();
    }
  )
;

// Entry rule entryRuleKeyOne
entryRuleKeyOne returns [String current=null]:
  { newCompositeNode(grammarAccess.getKeyOneRule()); }
  iv_ruleKeyOne=ruleKeyOne
  { $current=$iv_ruleKeyOne.current.getText(); }
  EOF;

// Rule KeyOne
ruleKeyOne returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  {predicates.isKeyOneEnabled(parserContext) /* @ErrorMessage(getKeyOneEnabledMessage) */}?
  this_ID_0=RULE_ID
  {
    $current.merge(this_ID_0);
  }
  {
    newLeafNode(this_ID_0, grammarAccess.getKeyOneAccess().getIDTerminalRuleCall());
  }
;

// Entry rule entryRuleKeyTwo
entryRuleKeyTwo returns [String current=null]:
  { newCompositeNode(grammarAccess.getKeyTwoRule()); }
  iv_ruleKeyTwo=ruleKeyTwo
  { $current=$iv_ruleKeyTwo.current.getText(); }
  EOF;

// Rule KeyTwo
ruleKeyTwo returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  {predicates.isKeyTwoEnabled(parserContext) /* @ErrorMessage(getKeyTwoEnabledMessage) */}?
  this_ID_0=RULE_ID
  {
    $current.merge(this_ID_0);
  }
  {
    newLeafNode(this_ID_0, grammarAccess.getKeyTwoAccess().getIDTerminalRuleCall());
  }
;

// Entry rule entryRuleKeyOther
entryRuleKeyOther returns [String current=null]:
  { newCompositeNode(grammarAccess.getKeyOtherRule()); }
  iv_ruleKeyOther=ruleKeyOther
  { $current=$iv_ruleKeyOther.current.getText(); }
  EOF;

// Rule KeyOther
ruleKeyOther returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  {predicates.isKeyOtherEnabled(parserContext) /* @ErrorMessage(getKeyOtherEnabledMessage) */}?
  this_ID_0=RULE_ID
  {
    $current.merge(this_ID_0);
  }
  {
    newLeafNode(this_ID_0, grammarAccess.getKeyOtherAccess().getIDTerminalRuleCall());
  }
;

// Entry rule entryRuleSimpleKeyFour
entryRuleSimpleKeyFour returns [String current=null]:
  { newCompositeNode(grammarAccess.getSimpleKeyFourRule()); }
  iv_ruleSimpleKeyFour=ruleSimpleKeyFour
  { $current=$iv_ruleSimpleKeyFour.current.getText(); }
  EOF;

// Rule SimpleKeyFour
ruleSimpleKeyFour returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  kw='four'
  {
    $current.merge(kw);
    newLeafNode(kw, grammarAccess.getSimpleKeyFourAccess().getFourKeyword());
  }
;

// Entry rule entryRuleSimpleKeyFive
entryRuleSimpleKeyFive returns [String current=null]:
  { newCompositeNode(grammarAccess.getSimpleKeyFiveRule()); }
  iv_ruleSimpleKeyFive=ruleSimpleKeyFive
  { $current=$iv_ruleSimpleKeyFive.current.getText(); }
  EOF;

// Rule SimpleKeyFive
ruleSimpleKeyFive returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  kw='five'
  {
    $current.merge(kw);
    newLeafNode(kw, grammarAccess.getSimpleKeyFiveAccess().getFiveKeyword());
  }
;

// Entry rule entryRuleEnumLikeOther
entryRuleEnumLikeOther returns [String current=null]:
  { newCompositeNode(grammarAccess.getEnumLikeOtherRule()); }
  iv_ruleEnumLikeOther=ruleEnumLikeOther
  { $current=$iv_ruleEnumLikeOther.current.getText(); }
  EOF;

// Rule EnumLikeOther
ruleEnumLikeOther returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
  enterRule();
}
@after {
  leaveRule();
}:
  (
    kw='four'
    {
      $current.merge(kw);
      newLeafNode(kw, grammarAccess.getEnumLikeOtherAccess().getFourKeyword_0());
    }
        |
    kw='five'
    {
      $current.merge(kw);
      newLeafNode(kw, grammarAccess.getEnumLikeOtherAccess().getFiveKeyword_1());
    }
        |
    kw='six'
    {
      $current.merge(kw);
      newLeafNode(kw, grammarAccess.getEnumLikeOtherAccess().getSixKeyword_2());
    }
  )
;

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
