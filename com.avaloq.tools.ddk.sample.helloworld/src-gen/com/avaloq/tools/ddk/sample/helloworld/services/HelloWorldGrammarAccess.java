/*
 * generated by Xtext 2.27.0.M3
 */
package com.avaloq.tools.ddk.sample.helloworld.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class HelloWorldGrammarAccess extends AbstractElementFinder.AbstractGrammarElementFinder {
	
	public class ModelElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.Model");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cGreetingsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cGreetingsGreetingParserRuleCall_0_0 = (RuleCall)cGreetingsAssignment_0.eContents().get(0);
		private final Assignment cKeywordsExampleAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cKeywordsExampleKeywordsExampleParserRuleCall_1_0 = (RuleCall)cKeywordsExampleAssignment_1.eContents().get(0);
		
		//Model:
		//  greetings+=Greeting*
		//  ( keywordsExample = KeywordsExample )?
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//greetings+=Greeting*
		//( keywordsExample = KeywordsExample )?
		public Group getGroup() { return cGroup; }
		
		//greetings+=Greeting*
		public Assignment getGreetingsAssignment_0() { return cGreetingsAssignment_0; }
		
		//Greeting
		public RuleCall getGreetingsGreetingParserRuleCall_0_0() { return cGreetingsGreetingParserRuleCall_0_0; }
		
		//( keywordsExample = KeywordsExample )?
		public Assignment getKeywordsExampleAssignment_1() { return cKeywordsExampleAssignment_1; }
		
		//KeywordsExample
		public RuleCall getKeywordsExampleKeywordsExampleParserRuleCall_1_0() { return cKeywordsExampleKeywordsExampleParserRuleCall_1_0; }
	}
	public class GreetingElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.Greeting");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cHelloKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cExclamationMarkKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//Greeting:
		//  'Hello' name=ID '!'
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'Hello' name=ID '!'
		public Group getGroup() { return cGroup; }
		
		//'Hello'
		public Keyword getHelloKeyword_0() { return cHelloKeyword_0; }
		
		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//'!'
		public Keyword getExclamationMarkKeyword_2() { return cExclamationMarkKeyword_2; }
	}
	public class KeywordsExampleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.KeywordsExample");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cOptionAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final RuleCall cOptionOptionOneParserRuleCall_0_0 = (RuleCall)cOptionAssignment_0.eContents().get(0);
		private final Assignment cOptionAssignment_1 = (Assignment)cAlternatives.eContents().get(1);
		private final RuleCall cOptionOptionTwoParserRuleCall_1_0 = (RuleCall)cOptionAssignment_1.eContents().get(0);
		private final Assignment cOptionAssignment_2 = (Assignment)cAlternatives.eContents().get(2);
		private final RuleCall cOptionOptionThreeParserRuleCall_2_0 = (RuleCall)cOptionAssignment_2.eContents().get(0);
		private final Assignment cOptionAssignment_3 = (Assignment)cAlternatives.eContents().get(3);
		private final RuleCall cOptionOptionFourParserRuleCall_3_0 = (RuleCall)cOptionAssignment_3.eContents().get(0);
		
		///**
		// * This example illustrated keyword annotation.
		// * Predicates are propagated into alternative before Xtext-generated actions.
		// */
		//KeywordsExample:
		// option = OptionOne | option = OptionTwo | option = OptionThree | option = OptionFour
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//option = OptionOne | option = OptionTwo | option = OptionThree | option = OptionFour
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//option = OptionOne
		public Assignment getOptionAssignment_0() { return cOptionAssignment_0; }
		
		//OptionOne
		public RuleCall getOptionOptionOneParserRuleCall_0_0() { return cOptionOptionOneParserRuleCall_0_0; }
		
		//option = OptionTwo
		public Assignment getOptionAssignment_1() { return cOptionAssignment_1; }
		
		//OptionTwo
		public RuleCall getOptionOptionTwoParserRuleCall_1_0() { return cOptionOptionTwoParserRuleCall_1_0; }
		
		//option = OptionThree
		public Assignment getOptionAssignment_2() { return cOptionAssignment_2; }
		
		//OptionThree
		public RuleCall getOptionOptionThreeParserRuleCall_2_0() { return cOptionOptionThreeParserRuleCall_2_0; }
		
		//option = OptionFour
		public Assignment getOptionAssignment_3() { return cOptionAssignment_3; }
		
		//OptionFour
		public RuleCall getOptionOptionFourParserRuleCall_3_0() { return cOptionOptionFourParserRuleCall_3_0; }
	}
	public class OptionOneElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.OptionOne");
		private final RuleCall cKeyOneParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//OptionOne:
		//  KeyOne
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//KeyOne
		public RuleCall getKeyOneParserRuleCall() { return cKeyOneParserRuleCall; }
	}
	public class OptionTwoElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.OptionTwo");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cKeyTwoParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final RuleCall cKeyOtherParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		///**
		// * In this example the second rule has no alternatives,
		// * so we only get validating predicate.
		// */
		//OptionTwo:
		//  KeyTwo KeyOther
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//KeyTwo KeyOther
		public Group getGroup() { return cGroup; }
		
		//KeyTwo
		public RuleCall getKeyTwoParserRuleCall_0() { return cKeyTwoParserRuleCall_0; }
		
		//KeyOther
		public RuleCall getKeyOtherParserRuleCall_1() { return cKeyOtherParserRuleCall_1; }
	}
	public class OptionThreeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.OptionThree");
		private final RuleCall cSimpleKeyFourParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//OptionThree:
		//  SimpleKeyFour
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//SimpleKeyFour
		public RuleCall getSimpleKeyFourParserRuleCall() { return cSimpleKeyFourParserRuleCall; }
	}
	public class OptionFourElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.OptionFour");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cSimpleKeyFiveParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final RuleCall cEnumLikeOtherParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//OptionFour:
		//  SimpleKeyFive EnumLikeOther
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//SimpleKeyFive EnumLikeOther
		public Group getGroup() { return cGroup; }
		
		//SimpleKeyFive
		public RuleCall getSimpleKeyFiveParserRuleCall_0() { return cSimpleKeyFiveParserRuleCall_0; }
		
		//EnumLikeOther
		public RuleCall getEnumLikeOtherParserRuleCall_1() { return cEnumLikeOtherParserRuleCall_1; }
	}
	public class KeyOneElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.KeyOne");
		private final RuleCall cIDTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		///**
		// * @KeywordRule(one)
		// */
		//KeyOne:
		//  ID
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//ID
		public RuleCall getIDTerminalRuleCall() { return cIDTerminalRuleCall; }
	}
	public class KeyTwoElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.KeyTwo");
		private final RuleCall cIDTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		///**
		// * @KeywordRule(two)
		// */
		//KeyTwo:
		//  ID
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//ID
		public RuleCall getIDTerminalRuleCall() { return cIDTerminalRuleCall; }
	}
	public class KeyOtherElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.KeyOther");
		private final RuleCall cIDTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		///**
		// * @KeywordRule(one,two,three)
		// */
		//KeyOther:
		//  ID
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//ID
		public RuleCall getIDTerminalRuleCall() { return cIDTerminalRuleCall; }
	}
	public class SimpleKeyFourElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.SimpleKeyFour");
		private final Keyword cFourKeyword = (Keyword)rule.eContents().get(1);
		
		//SimpleKeyFour:
		//  "four"
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//"four"
		public Keyword getFourKeyword() { return cFourKeyword; }
	}
	public class SimpleKeyFiveElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.SimpleKeyFive");
		private final Keyword cFiveKeyword = (Keyword)rule.eContents().get(1);
		
		//SimpleKeyFive:
		//  "five"
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//"five"
		public Keyword getFiveKeyword() { return cFiveKeyword; }
	}
	public class EnumLikeOtherElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "com.avaloq.tools.ddk.sample.helloworld.HelloWorld.EnumLikeOther");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cFourKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cFiveKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cSixKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		
		//EnumLikeOther:
		//  "four" | "five" | "six"
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//"four" | "five" | "six"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//"four"
		public Keyword getFourKeyword_0() { return cFourKeyword_0; }
		
		//"five"
		public Keyword getFiveKeyword_1() { return cFiveKeyword_1; }
		
		//"six"
		public Keyword getSixKeyword_2() { return cSixKeyword_2; }
	}
	
	
	private final ModelElements pModel;
	private final GreetingElements pGreeting;
	private final KeywordsExampleElements pKeywordsExample;
	private final OptionOneElements pOptionOne;
	private final OptionTwoElements pOptionTwo;
	private final OptionThreeElements pOptionThree;
	private final OptionFourElements pOptionFour;
	private final KeyOneElements pKeyOne;
	private final KeyTwoElements pKeyTwo;
	private final KeyOtherElements pKeyOther;
	private final SimpleKeyFourElements pSimpleKeyFour;
	private final SimpleKeyFiveElements pSimpleKeyFive;
	private final EnumLikeOtherElements pEnumLikeOther;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public HelloWorldGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pModel = new ModelElements();
		this.pGreeting = new GreetingElements();
		this.pKeywordsExample = new KeywordsExampleElements();
		this.pOptionOne = new OptionOneElements();
		this.pOptionTwo = new OptionTwoElements();
		this.pOptionThree = new OptionThreeElements();
		this.pOptionFour = new OptionFourElements();
		this.pKeyOne = new KeyOneElements();
		this.pKeyTwo = new KeyTwoElements();
		this.pKeyOther = new KeyOtherElements();
		this.pSimpleKeyFour = new SimpleKeyFourElements();
		this.pSimpleKeyFive = new SimpleKeyFiveElements();
		this.pEnumLikeOther = new EnumLikeOtherElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("com.avaloq.tools.ddk.sample.helloworld.HelloWorld".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//Model:
	//  greetings+=Greeting*
	//  ( keywordsExample = KeywordsExample )?
	//;
	public ModelElements getModelAccess() {
		return pModel;
	}
	
	public ParserRule getModelRule() {
		return getModelAccess().getRule();
	}
	
	//Greeting:
	//  'Hello' name=ID '!'
	//;
	public GreetingElements getGreetingAccess() {
		return pGreeting;
	}
	
	public ParserRule getGreetingRule() {
		return getGreetingAccess().getRule();
	}
	
	///**
	// * This example illustrated keyword annotation.
	// * Predicates are propagated into alternative before Xtext-generated actions.
	// */
	//KeywordsExample:
	// option = OptionOne | option = OptionTwo | option = OptionThree | option = OptionFour
	//;
	public KeywordsExampleElements getKeywordsExampleAccess() {
		return pKeywordsExample;
	}
	
	public ParserRule getKeywordsExampleRule() {
		return getKeywordsExampleAccess().getRule();
	}
	
	//OptionOne:
	//  KeyOne
	//;
	public OptionOneElements getOptionOneAccess() {
		return pOptionOne;
	}
	
	public ParserRule getOptionOneRule() {
		return getOptionOneAccess().getRule();
	}
	
	///**
	// * In this example the second rule has no alternatives,
	// * so we only get validating predicate.
	// */
	//OptionTwo:
	//  KeyTwo KeyOther
	//;
	public OptionTwoElements getOptionTwoAccess() {
		return pOptionTwo;
	}
	
	public ParserRule getOptionTwoRule() {
		return getOptionTwoAccess().getRule();
	}
	
	//OptionThree:
	//  SimpleKeyFour
	//;
	public OptionThreeElements getOptionThreeAccess() {
		return pOptionThree;
	}
	
	public ParserRule getOptionThreeRule() {
		return getOptionThreeAccess().getRule();
	}
	
	//OptionFour:
	//  SimpleKeyFive EnumLikeOther
	//;
	public OptionFourElements getOptionFourAccess() {
		return pOptionFour;
	}
	
	public ParserRule getOptionFourRule() {
		return getOptionFourAccess().getRule();
	}
	
	///**
	// * @KeywordRule(one)
	// */
	//KeyOne:
	//  ID
	//;
	public KeyOneElements getKeyOneAccess() {
		return pKeyOne;
	}
	
	public ParserRule getKeyOneRule() {
		return getKeyOneAccess().getRule();
	}
	
	///**
	// * @KeywordRule(two)
	// */
	//KeyTwo:
	//  ID
	//;
	public KeyTwoElements getKeyTwoAccess() {
		return pKeyTwo;
	}
	
	public ParserRule getKeyTwoRule() {
		return getKeyTwoAccess().getRule();
	}
	
	///**
	// * @KeywordRule(one,two,three)
	// */
	//KeyOther:
	//  ID
	//;
	public KeyOtherElements getKeyOtherAccess() {
		return pKeyOther;
	}
	
	public ParserRule getKeyOtherRule() {
		return getKeyOtherAccess().getRule();
	}
	
	//SimpleKeyFour:
	//  "four"
	//;
	public SimpleKeyFourElements getSimpleKeyFourAccess() {
		return pSimpleKeyFour;
	}
	
	public ParserRule getSimpleKeyFourRule() {
		return getSimpleKeyFourAccess().getRule();
	}
	
	//SimpleKeyFive:
	//  "five"
	//;
	public SimpleKeyFiveElements getSimpleKeyFiveAccess() {
		return pSimpleKeyFive;
	}
	
	public ParserRule getSimpleKeyFiveRule() {
		return getSimpleKeyFiveAccess().getRule();
	}
	
	//EnumLikeOther:
	//  "four" | "five" | "six"
	//;
	public EnumLikeOtherElements getEnumLikeOtherAccess() {
		return pEnumLikeOther;
	}
	
	public ParserRule getEnumLikeOtherRule() {
		return getEnumLikeOtherAccess().getRule();
	}
	
	//terminal ID: '^'?('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt: ('0'..'9')+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//            '"' ( '\\' . /* 'b'|'t'|'n'|'f'|'r'|'u'|'"'|"'"|'\\' */ | !('\\'|'"') )* '"' |
	//            "'" ( '\\' . /* 'b'|'t'|'n'|'f'|'r'|'u'|'"'|"'"|'\\' */ | !('\\'|"'") )* "'"
	//        ;
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT : '/*' -> '*/';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT : '//' !('\n'|'\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS         : (' '|'\t'|'\r'|'\n')+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER: .;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}
