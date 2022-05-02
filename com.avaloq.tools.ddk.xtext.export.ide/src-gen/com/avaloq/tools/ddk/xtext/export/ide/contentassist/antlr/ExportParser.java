/*
 * generated by Xtext 2.27.0.M1
 */
package com.avaloq.tools.ddk.xtext.export.ide.contentassist.antlr;

import com.avaloq.tools.ddk.xtext.export.ide.contentassist.antlr.internal.InternalExportParser;
import com.avaloq.tools.ddk.xtext.export.services.ExportGrammarAccess;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class ExportParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(ExportGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, ExportGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getDeclarationForTypeAccess().getAlternatives(), "rule__DeclarationForType__Alternatives");
			builder.put(grammarAccess.getInterfaceItemAccess().getAlternatives(), "rule__InterfaceItem__Alternatives");
			builder.put(grammarAccess.getExportAccess().getAlternatives_7_0(), "rule__Export__Alternatives_7_0");
			builder.put(grammarAccess.getExportAccess().getAlternatives_8(), "rule__Export__Alternatives_8");
			builder.put(grammarAccess.getExpressionAccess().getAlternatives(), "rule__Expression__Alternatives");
			builder.put(grammarAccess.getSyntaxElementAccess().getAlternatives(), "rule__SyntaxElement__Alternatives");
			builder.put(grammarAccess.getChainedExpressionAccess().getAlternatives(), "rule__ChainedExpression__Alternatives");
			builder.put(grammarAccess.getRelationalExpressionAccess().getOperatorAlternatives_1_1_0(), "rule__RelationalExpression__OperatorAlternatives_1_1_0");
			builder.put(grammarAccess.getAdditiveExpressionAccess().getNameAlternatives_1_1_0(), "rule__AdditiveExpression__NameAlternatives_1_1_0");
			builder.put(grammarAccess.getMultiplicativeExpressionAccess().getNameAlternatives_1_1_0(), "rule__MultiplicativeExpression__NameAlternatives_1_1_0");
			builder.put(grammarAccess.getUnaryOrInfixExpressionAccess().getAlternatives(), "rule__UnaryOrInfixExpression__Alternatives");
			builder.put(grammarAccess.getUnaryExpressionAccess().getNameAlternatives_0_0(), "rule__UnaryExpression__NameAlternatives_0_0");
			builder.put(grammarAccess.getInfixExpressionAccess().getAlternatives_1(), "rule__InfixExpression__Alternatives_1");
			builder.put(grammarAccess.getInfixExpressionAccess().getNameAlternatives_1_3_2_0(), "rule__InfixExpression__NameAlternatives_1_3_2_0");
			builder.put(grammarAccess.getPrimaryExpressionAccess().getAlternatives(), "rule__PrimaryExpression__Alternatives");
			builder.put(grammarAccess.getLiteralAccess().getAlternatives(), "rule__Literal__Alternatives");
			builder.put(grammarAccess.getBooleanLiteralAccess().getValAlternatives_0(), "rule__BooleanLiteral__ValAlternatives_0");
			builder.put(grammarAccess.getFeatureCallAccess().getAlternatives(), "rule__FeatureCall__Alternatives");
			builder.put(grammarAccess.getCollectionExpressionAccess().getNameAlternatives_0_0(), "rule__CollectionExpression__NameAlternatives_0_0");
			builder.put(grammarAccess.getTypeAccess().getAlternatives(), "rule__Type__Alternatives");
			builder.put(grammarAccess.getCollectionTypeAccess().getClAlternatives_0_0(), "rule__CollectionType__ClAlternatives_0_0");
			builder.put(grammarAccess.getExportModelAccess().getGroup(), "rule__ExportModel__Group__0");
			builder.put(grammarAccess.getExportModelAccess().getGroup_0(), "rule__ExportModel__Group_0__0");
			builder.put(grammarAccess.getExportModelAccess().getGroup_3(), "rule__ExportModel__Group_3__0");
			builder.put(grammarAccess.getImportAccess().getGroup(), "rule__Import__Group__0");
			builder.put(grammarAccess.getImportAccess().getGroup_2(), "rule__Import__Group_2__0");
			builder.put(grammarAccess.getExtensionAccess().getGroup(), "rule__Extension__Group__0");
			builder.put(grammarAccess.getInterfaceAccess().getGroup(), "rule__Interface__Group__0");
			builder.put(grammarAccess.getInterfaceAccess().getGroup_1(), "rule__Interface__Group_1__0");
			builder.put(grammarAccess.getInterfaceAccess().getGroup_2(), "rule__Interface__Group_2__0");
			builder.put(grammarAccess.getInterfaceAccess().getGroup_2_2(), "rule__Interface__Group_2_2__0");
			builder.put(grammarAccess.getInterfaceFieldAccess().getGroup(), "rule__InterfaceField__Group__0");
			builder.put(grammarAccess.getInterfaceNavigationAccess().getGroup(), "rule__InterfaceNavigation__Group__0");
			builder.put(grammarAccess.getInterfaceExpressionAccess().getGroup(), "rule__InterfaceExpression__Group__0");
			builder.put(grammarAccess.getExportAccess().getGroup(), "rule__Export__Group__0");
			builder.put(grammarAccess.getExportAccess().getGroup_1(), "rule__Export__Group_1__0");
			builder.put(grammarAccess.getExportAccess().getGroup_1_1(), "rule__Export__Group_1_1__0");
			builder.put(grammarAccess.getExportAccess().getGroup_3(), "rule__Export__Group_3__0");
			builder.put(grammarAccess.getExportAccess().getGroup_4(), "rule__Export__Group_4__0");
			builder.put(grammarAccess.getExportAccess().getGroup_6(), "rule__Export__Group_6__0");
			builder.put(grammarAccess.getExportAccess().getGroup_7(), "rule__Export__Group_7__0");
			builder.put(grammarAccess.getExportAccess().getGroup_8_0(), "rule__Export__Group_8_0__0");
			builder.put(grammarAccess.getExportAccess().getGroup_8_0_2(), "rule__Export__Group_8_0_2__0");
			builder.put(grammarAccess.getExportAccess().getGroup_8_1(), "rule__Export__Group_8_1__0");
			builder.put(grammarAccess.getExportAccess().getGroup_8_1_2(), "rule__Export__Group_8_1_2__0");
			builder.put(grammarAccess.getUserDataAccess().getGroup(), "rule__UserData__Group__0");
			builder.put(grammarAccess.getQualifiedIDAccess().getGroup(), "rule__QualifiedID__Group__0");
			builder.put(grammarAccess.getQualifiedIDAccess().getGroup_1(), "rule__QualifiedID__Group_1__0");
			builder.put(grammarAccess.getLetExpressionAccess().getGroup(), "rule__LetExpression__Group__0");
			builder.put(grammarAccess.getCastedExpressionAccess().getGroup(), "rule__CastedExpression__Group__0");
			builder.put(grammarAccess.getChainExpressionAccess().getGroup(), "rule__ChainExpression__Group__0");
			builder.put(grammarAccess.getChainExpressionAccess().getGroup_1(), "rule__ChainExpression__Group_1__0");
			builder.put(grammarAccess.getIfExpressionTriAccess().getGroup(), "rule__IfExpressionTri__Group__0");
			builder.put(grammarAccess.getIfExpressionTriAccess().getGroup_1(), "rule__IfExpressionTri__Group_1__0");
			builder.put(grammarAccess.getIfExpressionKwAccess().getGroup(), "rule__IfExpressionKw__Group__0");
			builder.put(grammarAccess.getIfExpressionKwAccess().getGroup_4(), "rule__IfExpressionKw__Group_4__0");
			builder.put(grammarAccess.getIfExpressionKwAccess().getGroup_4_0(), "rule__IfExpressionKw__Group_4_0__0");
			builder.put(grammarAccess.getSwitchExpressionAccess().getGroup(), "rule__SwitchExpression__Group__0");
			builder.put(grammarAccess.getSwitchExpressionAccess().getGroup_1(), "rule__SwitchExpression__Group_1__0");
			builder.put(grammarAccess.getCaseAccess().getGroup(), "rule__Case__Group__0");
			builder.put(grammarAccess.getOrExpressionAccess().getGroup(), "rule__OrExpression__Group__0");
			builder.put(grammarAccess.getOrExpressionAccess().getGroup_1(), "rule__OrExpression__Group_1__0");
			builder.put(grammarAccess.getAndExpressionAccess().getGroup(), "rule__AndExpression__Group__0");
			builder.put(grammarAccess.getAndExpressionAccess().getGroup_1(), "rule__AndExpression__Group_1__0");
			builder.put(grammarAccess.getImpliesExpressionAccess().getGroup(), "rule__ImpliesExpression__Group__0");
			builder.put(grammarAccess.getImpliesExpressionAccess().getGroup_1(), "rule__ImpliesExpression__Group_1__0");
			builder.put(grammarAccess.getRelationalExpressionAccess().getGroup(), "rule__RelationalExpression__Group__0");
			builder.put(grammarAccess.getRelationalExpressionAccess().getGroup_1(), "rule__RelationalExpression__Group_1__0");
			builder.put(grammarAccess.getAdditiveExpressionAccess().getGroup(), "rule__AdditiveExpression__Group__0");
			builder.put(grammarAccess.getAdditiveExpressionAccess().getGroup_1(), "rule__AdditiveExpression__Group_1__0");
			builder.put(grammarAccess.getMultiplicativeExpressionAccess().getGroup(), "rule__MultiplicativeExpression__Group__0");
			builder.put(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1(), "rule__MultiplicativeExpression__Group_1__0");
			builder.put(grammarAccess.getUnaryExpressionAccess().getGroup(), "rule__UnaryExpression__Group__0");
			builder.put(grammarAccess.getInfixExpressionAccess().getGroup(), "rule__InfixExpression__Group__0");
			builder.put(grammarAccess.getInfixExpressionAccess().getGroup_1_0(), "rule__InfixExpression__Group_1_0__0");
			builder.put(grammarAccess.getInfixExpressionAccess().getGroup_1_0_4(), "rule__InfixExpression__Group_1_0_4__0");
			builder.put(grammarAccess.getInfixExpressionAccess().getGroup_1_0_4_1(), "rule__InfixExpression__Group_1_0_4_1__0");
			builder.put(grammarAccess.getInfixExpressionAccess().getGroup_1_1(), "rule__InfixExpression__Group_1_1__0");
			builder.put(grammarAccess.getInfixExpressionAccess().getGroup_1_2(), "rule__InfixExpression__Group_1_2__0");
			builder.put(grammarAccess.getInfixExpressionAccess().getGroup_1_3(), "rule__InfixExpression__Group_1_3__0");
			builder.put(grammarAccess.getInfixExpressionAccess().getGroup_1_3_4(), "rule__InfixExpression__Group_1_3_4__0");
			builder.put(grammarAccess.getParanthesizedExpressionAccess().getGroup(), "rule__ParanthesizedExpression__Group__0");
			builder.put(grammarAccess.getGlobalVarExpressionAccess().getGroup(), "rule__GlobalVarExpression__Group__0");
			builder.put(grammarAccess.getOperationCallAccess().getGroup(), "rule__OperationCall__Group__0");
			builder.put(grammarAccess.getOperationCallAccess().getGroup_2(), "rule__OperationCall__Group_2__0");
			builder.put(grammarAccess.getOperationCallAccess().getGroup_2_1(), "rule__OperationCall__Group_2_1__0");
			builder.put(grammarAccess.getListLiteralAccess().getGroup(), "rule__ListLiteral__Group__0");
			builder.put(grammarAccess.getListLiteralAccess().getGroup_2(), "rule__ListLiteral__Group_2__0");
			builder.put(grammarAccess.getListLiteralAccess().getGroup_2_1(), "rule__ListLiteral__Group_2_1__0");
			builder.put(grammarAccess.getConstructorCallExpressionAccess().getGroup(), "rule__ConstructorCallExpression__Group__0");
			builder.put(grammarAccess.getTypeSelectExpressionAccess().getGroup(), "rule__TypeSelectExpression__Group__0");
			builder.put(grammarAccess.getCollectionExpressionAccess().getGroup(), "rule__CollectionExpression__Group__0");
			builder.put(grammarAccess.getCollectionExpressionAccess().getGroup_2(), "rule__CollectionExpression__Group_2__0");
			builder.put(grammarAccess.getCollectionTypeAccess().getGroup(), "rule__CollectionType__Group__0");
			builder.put(grammarAccess.getSimpleTypeAccess().getGroup(), "rule__SimpleType__Group__0");
			builder.put(grammarAccess.getSimpleTypeAccess().getGroup_1(), "rule__SimpleType__Group_1__0");
			builder.put(grammarAccess.getExportModelAccess().getExtensionAssignment_0_1(), "rule__ExportModel__ExtensionAssignment_0_1");
			builder.put(grammarAccess.getExportModelAccess().getNameAssignment_0_2(), "rule__ExportModel__NameAssignment_0_2");
			builder.put(grammarAccess.getExportModelAccess().getTargetGrammarAssignment_0_4(), "rule__ExportModel__TargetGrammarAssignment_0_4");
			builder.put(grammarAccess.getExportModelAccess().getImportsAssignment_1(), "rule__ExportModel__ImportsAssignment_1");
			builder.put(grammarAccess.getExportModelAccess().getExtensionsAssignment_2(), "rule__ExportModel__ExtensionsAssignment_2");
			builder.put(grammarAccess.getExportModelAccess().getInterfacesAssignment_3_2(), "rule__ExportModel__InterfacesAssignment_3_2");
			builder.put(grammarAccess.getExportModelAccess().getExportsAssignment_4(), "rule__ExportModel__ExportsAssignment_4");
			builder.put(grammarAccess.getImportAccess().getPackageAssignment_1(), "rule__Import__PackageAssignment_1");
			builder.put(grammarAccess.getImportAccess().getNameAssignment_2_1(), "rule__Import__NameAssignment_2_1");
			builder.put(grammarAccess.getExtensionAccess().getExtensionAssignment_1(), "rule__Extension__ExtensionAssignment_1");
			builder.put(grammarAccess.getInterfaceAccess().getTypeAssignment_0(), "rule__Interface__TypeAssignment_0");
			builder.put(grammarAccess.getInterfaceAccess().getGuardAssignment_1_1(), "rule__Interface__GuardAssignment_1_1");
			builder.put(grammarAccess.getInterfaceAccess().getItemsAssignment_2_1(), "rule__Interface__ItemsAssignment_2_1");
			builder.put(grammarAccess.getInterfaceAccess().getItemsAssignment_2_2_1(), "rule__Interface__ItemsAssignment_2_2_1");
			builder.put(grammarAccess.getInterfaceFieldAccess().getUnorderedAssignment_0(), "rule__InterfaceField__UnorderedAssignment_0");
			builder.put(grammarAccess.getInterfaceFieldAccess().getFieldAssignment_1(), "rule__InterfaceField__FieldAssignment_1");
			builder.put(grammarAccess.getInterfaceNavigationAccess().getUnorderedAssignment_1(), "rule__InterfaceNavigation__UnorderedAssignment_1");
			builder.put(grammarAccess.getInterfaceNavigationAccess().getRefAssignment_2(), "rule__InterfaceNavigation__RefAssignment_2");
			builder.put(grammarAccess.getInterfaceExpressionAccess().getRefAssignment_1(), "rule__InterfaceExpression__RefAssignment_1");
			builder.put(grammarAccess.getInterfaceExpressionAccess().getUnorderedAssignment_2(), "rule__InterfaceExpression__UnorderedAssignment_2");
			builder.put(grammarAccess.getInterfaceExpressionAccess().getExprAssignment_4(), "rule__InterfaceExpression__ExprAssignment_4");
			builder.put(grammarAccess.getExportAccess().getLookupAssignment_1_0(), "rule__Export__LookupAssignment_1_0");
			builder.put(grammarAccess.getExportAccess().getLookupPredicateAssignment_1_1_1(), "rule__Export__LookupPredicateAssignment_1_1_1");
			builder.put(grammarAccess.getExportAccess().getTypeAssignment_2(), "rule__Export__TypeAssignment_2");
			builder.put(grammarAccess.getExportAccess().getQualifiedNameAssignment_3_1(), "rule__Export__QualifiedNameAssignment_3_1");
			builder.put(grammarAccess.getExportAccess().getNamingAssignment_3_2(), "rule__Export__NamingAssignment_3_2");
			builder.put(grammarAccess.getExportAccess().getGuardAssignment_4_1(), "rule__Export__GuardAssignment_4_1");
			builder.put(grammarAccess.getExportAccess().getFragmentUniqueAssignment_6_2(), "rule__Export__FragmentUniqueAssignment_6_2");
			builder.put(grammarAccess.getExportAccess().getFragmentAttributeAssignment_6_5(), "rule__Export__FragmentAttributeAssignment_6_5");
			builder.put(grammarAccess.getExportAccess().getFingerprintAssignment_7_0_0(), "rule__Export__FingerprintAssignment_7_0_0");
			builder.put(grammarAccess.getExportAccess().getResourceFingerprintAssignment_7_0_1(), "rule__Export__ResourceFingerprintAssignment_7_0_1");
			builder.put(grammarAccess.getExportAccess().getAttributesAssignment_8_0_1(), "rule__Export__AttributesAssignment_8_0_1");
			builder.put(grammarAccess.getExportAccess().getAttributesAssignment_8_0_2_1(), "rule__Export__AttributesAssignment_8_0_2_1");
			builder.put(grammarAccess.getExportAccess().getUserDataAssignment_8_1_1(), "rule__Export__UserDataAssignment_8_1_1");
			builder.put(grammarAccess.getExportAccess().getUserDataAssignment_8_1_2_1(), "rule__Export__UserDataAssignment_8_1_2_1");
			builder.put(grammarAccess.getUserDataAccess().getNameAssignment_0(), "rule__UserData__NameAssignment_0");
			builder.put(grammarAccess.getUserDataAccess().getExprAssignment_2(), "rule__UserData__ExprAssignment_2");
			builder.put(grammarAccess.getAttributeAccess().getAttributeAssignment(), "rule__Attribute__AttributeAssignment");
			builder.put(grammarAccess.getLetExpressionAccess().getIdentifierAssignment_1(), "rule__LetExpression__IdentifierAssignment_1");
			builder.put(grammarAccess.getLetExpressionAccess().getVarExprAssignment_3(), "rule__LetExpression__VarExprAssignment_3");
			builder.put(grammarAccess.getLetExpressionAccess().getTargetAssignment_5(), "rule__LetExpression__TargetAssignment_5");
			builder.put(grammarAccess.getCastedExpressionAccess().getTypeAssignment_1(), "rule__CastedExpression__TypeAssignment_1");
			builder.put(grammarAccess.getCastedExpressionAccess().getTargetAssignment_3(), "rule__CastedExpression__TargetAssignment_3");
			builder.put(grammarAccess.getChainExpressionAccess().getNextAssignment_1_2(), "rule__ChainExpression__NextAssignment_1_2");
			builder.put(grammarAccess.getIfExpressionTriAccess().getThenPartAssignment_1_2(), "rule__IfExpressionTri__ThenPartAssignment_1_2");
			builder.put(grammarAccess.getIfExpressionTriAccess().getElsePartAssignment_1_4(), "rule__IfExpressionTri__ElsePartAssignment_1_4");
			builder.put(grammarAccess.getIfExpressionKwAccess().getConditionAssignment_1(), "rule__IfExpressionKw__ConditionAssignment_1");
			builder.put(grammarAccess.getIfExpressionKwAccess().getThenPartAssignment_3(), "rule__IfExpressionKw__ThenPartAssignment_3");
			builder.put(grammarAccess.getIfExpressionKwAccess().getElsePartAssignment_4_0_1(), "rule__IfExpressionKw__ElsePartAssignment_4_0_1");
			builder.put(grammarAccess.getSwitchExpressionAccess().getSwitchExprAssignment_1_1(), "rule__SwitchExpression__SwitchExprAssignment_1_1");
			builder.put(grammarAccess.getSwitchExpressionAccess().getCaseAssignment_3(), "rule__SwitchExpression__CaseAssignment_3");
			builder.put(grammarAccess.getSwitchExpressionAccess().getDefaultExprAssignment_6(), "rule__SwitchExpression__DefaultExprAssignment_6");
			builder.put(grammarAccess.getCaseAccess().getConditionAssignment_1(), "rule__Case__ConditionAssignment_1");
			builder.put(grammarAccess.getCaseAccess().getThenParAssignment_3(), "rule__Case__ThenParAssignment_3");
			builder.put(grammarAccess.getOrExpressionAccess().getOperatorAssignment_1_1(), "rule__OrExpression__OperatorAssignment_1_1");
			builder.put(grammarAccess.getOrExpressionAccess().getRightAssignment_1_2(), "rule__OrExpression__RightAssignment_1_2");
			builder.put(grammarAccess.getAndExpressionAccess().getOperatorAssignment_1_1(), "rule__AndExpression__OperatorAssignment_1_1");
			builder.put(grammarAccess.getAndExpressionAccess().getRightAssignment_1_2(), "rule__AndExpression__RightAssignment_1_2");
			builder.put(grammarAccess.getImpliesExpressionAccess().getOperatorAssignment_1_1(), "rule__ImpliesExpression__OperatorAssignment_1_1");
			builder.put(grammarAccess.getImpliesExpressionAccess().getRightAssignment_1_2(), "rule__ImpliesExpression__RightAssignment_1_2");
			builder.put(grammarAccess.getRelationalExpressionAccess().getOperatorAssignment_1_1(), "rule__RelationalExpression__OperatorAssignment_1_1");
			builder.put(grammarAccess.getRelationalExpressionAccess().getRightAssignment_1_2(), "rule__RelationalExpression__RightAssignment_1_2");
			builder.put(grammarAccess.getAdditiveExpressionAccess().getNameAssignment_1_1(), "rule__AdditiveExpression__NameAssignment_1_1");
			builder.put(grammarAccess.getAdditiveExpressionAccess().getParamsAssignment_1_2(), "rule__AdditiveExpression__ParamsAssignment_1_2");
			builder.put(grammarAccess.getMultiplicativeExpressionAccess().getNameAssignment_1_1(), "rule__MultiplicativeExpression__NameAssignment_1_1");
			builder.put(grammarAccess.getMultiplicativeExpressionAccess().getParamsAssignment_1_2(), "rule__MultiplicativeExpression__ParamsAssignment_1_2");
			builder.put(grammarAccess.getUnaryExpressionAccess().getNameAssignment_0(), "rule__UnaryExpression__NameAssignment_0");
			builder.put(grammarAccess.getUnaryExpressionAccess().getParamsAssignment_1(), "rule__UnaryExpression__ParamsAssignment_1");
			builder.put(grammarAccess.getInfixExpressionAccess().getNameAssignment_1_0_2(), "rule__InfixExpression__NameAssignment_1_0_2");
			builder.put(grammarAccess.getInfixExpressionAccess().getParamsAssignment_1_0_4_0(), "rule__InfixExpression__ParamsAssignment_1_0_4_0");
			builder.put(grammarAccess.getInfixExpressionAccess().getParamsAssignment_1_0_4_1_1(), "rule__InfixExpression__ParamsAssignment_1_0_4_1_1");
			builder.put(grammarAccess.getInfixExpressionAccess().getTypeAssignment_1_1_2(), "rule__InfixExpression__TypeAssignment_1_1_2");
			builder.put(grammarAccess.getInfixExpressionAccess().getNameAssignment_1_2_2(), "rule__InfixExpression__NameAssignment_1_2_2");
			builder.put(grammarAccess.getInfixExpressionAccess().getTypeAssignment_1_2_4(), "rule__InfixExpression__TypeAssignment_1_2_4");
			builder.put(grammarAccess.getInfixExpressionAccess().getNameAssignment_1_3_2(), "rule__InfixExpression__NameAssignment_1_3_2");
			builder.put(grammarAccess.getInfixExpressionAccess().getVarAssignment_1_3_4_0(), "rule__InfixExpression__VarAssignment_1_3_4_0");
			builder.put(grammarAccess.getInfixExpressionAccess().getExpAssignment_1_3_5(), "rule__InfixExpression__ExpAssignment_1_3_5");
			builder.put(grammarAccess.getBooleanLiteralAccess().getValAssignment(), "rule__BooleanLiteral__ValAssignment");
			builder.put(grammarAccess.getIntegerLiteralAccess().getValAssignment(), "rule__IntegerLiteral__ValAssignment");
			builder.put(grammarAccess.getNullLiteralAccess().getValAssignment(), "rule__NullLiteral__ValAssignment");
			builder.put(grammarAccess.getRealLiteralAccess().getValAssignment(), "rule__RealLiteral__ValAssignment");
			builder.put(grammarAccess.getStringLiteralAccess().getValAssignment(), "rule__StringLiteral__ValAssignment");
			builder.put(grammarAccess.getGlobalVarExpressionAccess().getNameAssignment_1(), "rule__GlobalVarExpression__NameAssignment_1");
			builder.put(grammarAccess.getFeatureCallAccess().getTypeAssignment_1(), "rule__FeatureCall__TypeAssignment_1");
			builder.put(grammarAccess.getOperationCallAccess().getNameAssignment_0(), "rule__OperationCall__NameAssignment_0");
			builder.put(grammarAccess.getOperationCallAccess().getParamsAssignment_2_0(), "rule__OperationCall__ParamsAssignment_2_0");
			builder.put(grammarAccess.getOperationCallAccess().getParamsAssignment_2_1_1(), "rule__OperationCall__ParamsAssignment_2_1_1");
			builder.put(grammarAccess.getListLiteralAccess().getElementsAssignment_2_0(), "rule__ListLiteral__ElementsAssignment_2_0");
			builder.put(grammarAccess.getListLiteralAccess().getElementsAssignment_2_1_1(), "rule__ListLiteral__ElementsAssignment_2_1_1");
			builder.put(grammarAccess.getConstructorCallExpressionAccess().getTypeAssignment_1(), "rule__ConstructorCallExpression__TypeAssignment_1");
			builder.put(grammarAccess.getTypeSelectExpressionAccess().getNameAssignment_0(), "rule__TypeSelectExpression__NameAssignment_0");
			builder.put(grammarAccess.getTypeSelectExpressionAccess().getTypeAssignment_2(), "rule__TypeSelectExpression__TypeAssignment_2");
			builder.put(grammarAccess.getCollectionExpressionAccess().getNameAssignment_0(), "rule__CollectionExpression__NameAssignment_0");
			builder.put(grammarAccess.getCollectionExpressionAccess().getVarAssignment_2_0(), "rule__CollectionExpression__VarAssignment_2_0");
			builder.put(grammarAccess.getCollectionExpressionAccess().getExpAssignment_3(), "rule__CollectionExpression__ExpAssignment_3");
			builder.put(grammarAccess.getCollectionTypeAccess().getClAssignment_0(), "rule__CollectionType__ClAssignment_0");
			builder.put(grammarAccess.getCollectionTypeAccess().getId1Assignment_2(), "rule__CollectionType__Id1Assignment_2");
			builder.put(grammarAccess.getSimpleTypeAccess().getIdAssignment_0(), "rule__SimpleType__IdAssignment_0");
			builder.put(grammarAccess.getSimpleTypeAccess().getIdAssignment_1_1(), "rule__SimpleType__IdAssignment_1_1");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private ExportGrammarAccess grammarAccess;

	@Override
	protected InternalExportParser createParser() {
		InternalExportParser result = new InternalExportParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public ExportGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(ExportGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}
