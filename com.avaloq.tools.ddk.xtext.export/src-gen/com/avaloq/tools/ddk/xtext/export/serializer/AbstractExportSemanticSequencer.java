/*
 * generated by Xtext 2.25.0
 */
package com.avaloq.tools.ddk.xtext.export.serializer;

import com.avaloq.tools.ddk.xtext.export.export.Attribute;
import com.avaloq.tools.ddk.xtext.export.export.Export;
import com.avaloq.tools.ddk.xtext.export.export.ExportModel;
import com.avaloq.tools.ddk.xtext.export.export.ExportPackage;
import com.avaloq.tools.ddk.xtext.export.export.Extension;
import com.avaloq.tools.ddk.xtext.export.export.Import;
import com.avaloq.tools.ddk.xtext.export.export.Interface;
import com.avaloq.tools.ddk.xtext.export.export.InterfaceExpression;
import com.avaloq.tools.ddk.xtext.export.export.InterfaceField;
import com.avaloq.tools.ddk.xtext.export.export.InterfaceNavigation;
import com.avaloq.tools.ddk.xtext.export.export.UserData;
import com.avaloq.tools.ddk.xtext.export.services.ExportGrammarAccess;
import com.avaloq.tools.ddk.xtext.expression.expression.BooleanLiteral;
import com.avaloq.tools.ddk.xtext.expression.expression.BooleanOperation;
import com.avaloq.tools.ddk.xtext.expression.expression.Case;
import com.avaloq.tools.ddk.xtext.expression.expression.CastedExpression;
import com.avaloq.tools.ddk.xtext.expression.expression.ChainExpression;
import com.avaloq.tools.ddk.xtext.expression.expression.CollectionExpression;
import com.avaloq.tools.ddk.xtext.expression.expression.ConstructorCallExpression;
import com.avaloq.tools.ddk.xtext.expression.expression.ExpressionPackage;
import com.avaloq.tools.ddk.xtext.expression.expression.FeatureCall;
import com.avaloq.tools.ddk.xtext.expression.expression.GlobalVarExpression;
import com.avaloq.tools.ddk.xtext.expression.expression.Identifier;
import com.avaloq.tools.ddk.xtext.expression.expression.IfExpression;
import com.avaloq.tools.ddk.xtext.expression.expression.IntegerLiteral;
import com.avaloq.tools.ddk.xtext.expression.expression.LetExpression;
import com.avaloq.tools.ddk.xtext.expression.expression.ListLiteral;
import com.avaloq.tools.ddk.xtext.expression.expression.NullLiteral;
import com.avaloq.tools.ddk.xtext.expression.expression.OperationCall;
import com.avaloq.tools.ddk.xtext.expression.expression.RealLiteral;
import com.avaloq.tools.ddk.xtext.expression.expression.StringLiteral;
import com.avaloq.tools.ddk.xtext.expression.expression.SwitchExpression;
import com.avaloq.tools.ddk.xtext.expression.expression.TypeSelectExpression;
import com.avaloq.tools.ddk.xtext.expression.serializer.ExpressionSemanticSequencer;
import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public abstract class AbstractExportSemanticSequencer extends ExpressionSemanticSequencer {

	@Inject
	private ExportGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == ExportPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case ExportPackage.ATTRIBUTE:
				sequence_Attribute(context, (Attribute) semanticObject); 
				return; 
			case ExportPackage.EXPORT:
				sequence_Export(context, (Export) semanticObject); 
				return; 
			case ExportPackage.EXPORT_MODEL:
				sequence_ExportModel(context, (ExportModel) semanticObject); 
				return; 
			case ExportPackage.EXTENSION:
				sequence_Extension(context, (Extension) semanticObject); 
				return; 
			case ExportPackage.IMPORT:
				sequence_Import(context, (Import) semanticObject); 
				return; 
			case ExportPackage.INTERFACE:
				sequence_Interface(context, (Interface) semanticObject); 
				return; 
			case ExportPackage.INTERFACE_EXPRESSION:
				sequence_InterfaceExpression(context, (InterfaceExpression) semanticObject); 
				return; 
			case ExportPackage.INTERFACE_FIELD:
				sequence_InterfaceField(context, (InterfaceField) semanticObject); 
				return; 
			case ExportPackage.INTERFACE_NAVIGATION:
				sequence_InterfaceNavigation(context, (InterfaceNavigation) semanticObject); 
				return; 
			case ExportPackage.USER_DATA:
				sequence_UserData(context, (UserData) semanticObject); 
				return; 
			}
		else if (epackage == ExpressionPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case ExpressionPackage.BOOLEAN_LITERAL:
				sequence_BooleanLiteral(context, (BooleanLiteral) semanticObject); 
				return; 
			case ExpressionPackage.BOOLEAN_OPERATION:
				sequence_AndExpression_ImpliesExpression_OrExpression_RelationalExpression(context, (BooleanOperation) semanticObject); 
				return; 
			case ExpressionPackage.CASE:
				sequence_Case(context, (Case) semanticObject); 
				return; 
			case ExpressionPackage.CASTED_EXPRESSION:
				sequence_CastedExpression(context, (CastedExpression) semanticObject); 
				return; 
			case ExpressionPackage.CHAIN_EXPRESSION:
				sequence_ChainExpression(context, (ChainExpression) semanticObject); 
				return; 
			case ExpressionPackage.COLLECTION_EXPRESSION:
				if (rule == grammarAccess.getFeatureCallRule()
						|| rule == grammarAccess.getCollectionExpressionRule()) {
					sequence_CollectionExpression(context, (CollectionExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getExpressionRule()
						|| rule == grammarAccess.getSyntaxElementRule()
						|| rule == grammarAccess.getChainExpressionRule()
						|| action == grammarAccess.getChainExpressionAccess().getChainExpressionFirstAction_1_0()
						|| rule == grammarAccess.getChainedExpressionRule()
						|| rule == grammarAccess.getIfExpressionTriRule()
						|| action == grammarAccess.getIfExpressionTriAccess().getIfExpressionConditionAction_1_0()
						|| rule == grammarAccess.getOrExpressionRule()
						|| action == grammarAccess.getOrExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAndExpressionRule()
						|| action == grammarAccess.getAndExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getImpliesExpressionRule()
						|| action == grammarAccess.getImpliesExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getUnaryOrInfixExpressionRule()
						|| rule == grammarAccess.getInfixExpressionRule()
						|| action == grammarAccess.getInfixExpressionAccess().getOperationCallTargetAction_1_0_0()
						|| action == grammarAccess.getInfixExpressionAccess().getFeatureCallTargetAction_1_1_0()
						|| action == grammarAccess.getInfixExpressionAccess().getTypeSelectExpressionTargetAction_1_2_0()
						|| action == grammarAccess.getInfixExpressionAccess().getCollectionExpressionTargetAction_1_3_0()
						|| rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getParanthesizedExpressionRule()) {
					sequence_CollectionExpression_InfixExpression(context, (CollectionExpression) semanticObject); 
					return; 
				}
				else break;
			case ExpressionPackage.CONSTRUCTOR_CALL_EXPRESSION:
				sequence_ConstructorCallExpression(context, (ConstructorCallExpression) semanticObject); 
				return; 
			case ExpressionPackage.FEATURE_CALL:
				if (rule == grammarAccess.getFeatureCallRule()) {
					sequence_FeatureCall(context, (FeatureCall) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getExpressionRule()
						|| rule == grammarAccess.getSyntaxElementRule()
						|| rule == grammarAccess.getChainExpressionRule()
						|| action == grammarAccess.getChainExpressionAccess().getChainExpressionFirstAction_1_0()
						|| rule == grammarAccess.getChainedExpressionRule()
						|| rule == grammarAccess.getIfExpressionTriRule()
						|| action == grammarAccess.getIfExpressionTriAccess().getIfExpressionConditionAction_1_0()
						|| rule == grammarAccess.getOrExpressionRule()
						|| action == grammarAccess.getOrExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAndExpressionRule()
						|| action == grammarAccess.getAndExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getImpliesExpressionRule()
						|| action == grammarAccess.getImpliesExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getUnaryOrInfixExpressionRule()
						|| rule == grammarAccess.getInfixExpressionRule()
						|| action == grammarAccess.getInfixExpressionAccess().getOperationCallTargetAction_1_0_0()
						|| action == grammarAccess.getInfixExpressionAccess().getFeatureCallTargetAction_1_1_0()
						|| action == grammarAccess.getInfixExpressionAccess().getTypeSelectExpressionTargetAction_1_2_0()
						|| action == grammarAccess.getInfixExpressionAccess().getCollectionExpressionTargetAction_1_3_0()
						|| rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getParanthesizedExpressionRule()) {
					sequence_FeatureCall_InfixExpression(context, (FeatureCall) semanticObject); 
					return; 
				}
				else break;
			case ExpressionPackage.GLOBAL_VAR_EXPRESSION:
				sequence_GlobalVarExpression(context, (GlobalVarExpression) semanticObject); 
				return; 
			case ExpressionPackage.IDENTIFIER:
				if (rule == grammarAccess.getCollectionTypeRule()) {
					sequence_CollectionType(context, (Identifier) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getSyntaxElementRule()
						|| rule == grammarAccess.getTypeRule()) {
					sequence_CollectionType_SimpleType(context, (Identifier) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getSimpleTypeRule()) {
					sequence_SimpleType(context, (Identifier) semanticObject); 
					return; 
				}
				else break;
			case ExpressionPackage.IF_EXPRESSION:
				if (rule == grammarAccess.getIfExpressionKwRule()) {
					sequence_IfExpressionKw(context, (IfExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getExpressionRule()
						|| rule == grammarAccess.getSyntaxElementRule()
						|| rule == grammarAccess.getChainExpressionRule()
						|| action == grammarAccess.getChainExpressionAccess().getChainExpressionFirstAction_1_0()
						|| rule == grammarAccess.getChainedExpressionRule()
						|| rule == grammarAccess.getIfExpressionTriRule()
						|| action == grammarAccess.getIfExpressionTriAccess().getIfExpressionConditionAction_1_0()
						|| rule == grammarAccess.getOrExpressionRule()
						|| action == grammarAccess.getOrExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAndExpressionRule()
						|| action == grammarAccess.getAndExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getImpliesExpressionRule()
						|| action == grammarAccess.getImpliesExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getUnaryOrInfixExpressionRule()
						|| rule == grammarAccess.getInfixExpressionRule()
						|| action == grammarAccess.getInfixExpressionAccess().getOperationCallTargetAction_1_0_0()
						|| action == grammarAccess.getInfixExpressionAccess().getFeatureCallTargetAction_1_1_0()
						|| action == grammarAccess.getInfixExpressionAccess().getTypeSelectExpressionTargetAction_1_2_0()
						|| action == grammarAccess.getInfixExpressionAccess().getCollectionExpressionTargetAction_1_3_0()
						|| rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getParanthesizedExpressionRule()) {
					sequence_IfExpressionKw_IfExpressionTri(context, (IfExpression) semanticObject); 
					return; 
				}
				else break;
			case ExpressionPackage.INTEGER_LITERAL:
				sequence_IntegerLiteral(context, (IntegerLiteral) semanticObject); 
				return; 
			case ExpressionPackage.LET_EXPRESSION:
				sequence_LetExpression(context, (LetExpression) semanticObject); 
				return; 
			case ExpressionPackage.LIST_LITERAL:
				sequence_ListLiteral(context, (ListLiteral) semanticObject); 
				return; 
			case ExpressionPackage.NULL_LITERAL:
				sequence_NullLiteral(context, (NullLiteral) semanticObject); 
				return; 
			case ExpressionPackage.OPERATION_CALL:
				if (rule == grammarAccess.getExpressionRule()
						|| rule == grammarAccess.getSyntaxElementRule()
						|| rule == grammarAccess.getChainExpressionRule()
						|| action == grammarAccess.getChainExpressionAccess().getChainExpressionFirstAction_1_0()
						|| rule == grammarAccess.getChainedExpressionRule()
						|| rule == grammarAccess.getIfExpressionTriRule()
						|| action == grammarAccess.getIfExpressionTriAccess().getIfExpressionConditionAction_1_0()
						|| rule == grammarAccess.getOrExpressionRule()
						|| action == grammarAccess.getOrExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAndExpressionRule()
						|| action == grammarAccess.getAndExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getImpliesExpressionRule()
						|| action == grammarAccess.getImpliesExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getUnaryOrInfixExpressionRule()
						|| rule == grammarAccess.getInfixExpressionRule()
						|| action == grammarAccess.getInfixExpressionAccess().getOperationCallTargetAction_1_0_0()
						|| action == grammarAccess.getInfixExpressionAccess().getFeatureCallTargetAction_1_1_0()
						|| action == grammarAccess.getInfixExpressionAccess().getTypeSelectExpressionTargetAction_1_2_0()
						|| action == grammarAccess.getInfixExpressionAccess().getCollectionExpressionTargetAction_1_3_0()
						|| rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getParanthesizedExpressionRule()) {
					sequence_AdditiveExpression_InfixExpression_MultiplicativeExpression_OperationCall_UnaryExpression(context, (OperationCall) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getFeatureCallRule()
						|| rule == grammarAccess.getOperationCallRule()) {
					sequence_OperationCall(context, (OperationCall) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getUnaryExpressionRule()) {
					sequence_UnaryExpression(context, (OperationCall) semanticObject); 
					return; 
				}
				else break;
			case ExpressionPackage.REAL_LITERAL:
				sequence_RealLiteral(context, (RealLiteral) semanticObject); 
				return; 
			case ExpressionPackage.STRING_LITERAL:
				sequence_StringLiteral(context, (StringLiteral) semanticObject); 
				return; 
			case ExpressionPackage.SWITCH_EXPRESSION:
				sequence_SwitchExpression(context, (SwitchExpression) semanticObject); 
				return; 
			case ExpressionPackage.TYPE_SELECT_EXPRESSION:
				if (rule == grammarAccess.getExpressionRule()
						|| rule == grammarAccess.getSyntaxElementRule()
						|| rule == grammarAccess.getChainExpressionRule()
						|| action == grammarAccess.getChainExpressionAccess().getChainExpressionFirstAction_1_0()
						|| rule == grammarAccess.getChainedExpressionRule()
						|| rule == grammarAccess.getIfExpressionTriRule()
						|| action == grammarAccess.getIfExpressionTriAccess().getIfExpressionConditionAction_1_0()
						|| rule == grammarAccess.getOrExpressionRule()
						|| action == grammarAccess.getOrExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAndExpressionRule()
						|| action == grammarAccess.getAndExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getImpliesExpressionRule()
						|| action == grammarAccess.getImpliesExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getBooleanOperationLeftAction_1_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getOperationCallParamsAction_1_0()
						|| rule == grammarAccess.getUnaryOrInfixExpressionRule()
						|| rule == grammarAccess.getInfixExpressionRule()
						|| action == grammarAccess.getInfixExpressionAccess().getOperationCallTargetAction_1_0_0()
						|| action == grammarAccess.getInfixExpressionAccess().getFeatureCallTargetAction_1_1_0()
						|| action == grammarAccess.getInfixExpressionAccess().getTypeSelectExpressionTargetAction_1_2_0()
						|| action == grammarAccess.getInfixExpressionAccess().getCollectionExpressionTargetAction_1_3_0()
						|| rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getParanthesizedExpressionRule()) {
					sequence_InfixExpression_TypeSelectExpression(context, (TypeSelectExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getFeatureCallRule()
						|| rule == grammarAccess.getTypeSelectExpressionRule()) {
					sequence_TypeSelectExpression(context, (TypeSelectExpression) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     Attribute returns Attribute
	 *
	 * Constraint:
	 *     attribute=[EAttribute|ID]
	 */
	protected void sequence_Attribute(ISerializationContext context, Attribute semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ExportPackage.Literals.ATTRIBUTE__ATTRIBUTE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ExportPackage.Literals.ATTRIBUTE__ATTRIBUTE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAttributeAccess().getAttributeEAttributeIDTerminalRuleCall_0_1(), semanticObject.eGet(ExportPackage.Literals.ATTRIBUTE__ATTRIBUTE, false));
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ExportModel returns ExportModel
	 *
	 * Constraint:
	 *     (
	 *         (extension?='extension'? name=ID targetGrammar=[Grammar|QualifiedID])? 
	 *         imports+=Import+ 
	 *         extensions+=Extension* 
	 *         interfaces+=Interface* 
	 *         exports+=Export+
	 *     )
	 */
	protected void sequence_ExportModel(ISerializationContext context, ExportModel semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     DeclarationForType returns Export
	 *     Export returns Export
	 *
	 * Constraint:
	 *     (
	 *         (lookup?='lookup' lookupPredicate=Expression?)? 
	 *         type=[EClass|QualifiedID] 
	 *         (qualifiedName?='qualified'? naming=Expression)? 
	 *         guard=Expression? 
	 *         (fragmentUnique?='unique'? fragmentAttribute=[EAttribute|ID])? 
	 *         (fingerprint?='object-fingerprint' | resourceFingerprint?='resource-fingerprint')? 
	 *         ((attributes+=Attribute attributes+=Attribute*) | (userData+=UserData userData+=UserData*))*
	 *     )
	 */
	protected void sequence_Export(ISerializationContext context, Export semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Extension returns Extension
	 *
	 * Constraint:
	 *     extension=QualifiedID
	 */
	protected void sequence_Extension(ISerializationContext context, Extension semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ExportPackage.Literals.EXTENSION__EXTENSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ExportPackage.Literals.EXTENSION__EXTENSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getExtensionAccess().getExtensionQualifiedIDParserRuleCall_1_0(), semanticObject.getExtension());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Import returns Import
	 *
	 * Constraint:
	 *     (package=[EPackage|STRING] name=ID?)
	 */
	protected void sequence_Import(ISerializationContext context, Import semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     InterfaceItem returns InterfaceExpression
	 *     InterfaceExpression returns InterfaceExpression
	 *
	 * Constraint:
	 *     (ref?='@'? unordered?='+'? expr=Expression)
	 */
	protected void sequence_InterfaceExpression(ISerializationContext context, InterfaceExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     InterfaceItem returns InterfaceField
	 *     InterfaceField returns InterfaceField
	 *
	 * Constraint:
	 *     (unordered?='+'? field=[EStructuralFeature|ID])
	 */
	protected void sequence_InterfaceField(ISerializationContext context, InterfaceField semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     InterfaceItem returns InterfaceNavigation
	 *     InterfaceNavigation returns InterfaceNavigation
	 *
	 * Constraint:
	 *     (unordered?='+'? ref=[EReference|ID])
	 */
	protected void sequence_InterfaceNavigation(ISerializationContext context, InterfaceNavigation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     DeclarationForType returns Interface
	 *     Interface returns Interface
	 *
	 * Constraint:
	 *     (type=[EClass|QualifiedID] guard=Expression? (items+=InterfaceItem items+=InterfaceItem*)*)
	 */
	protected void sequence_Interface(ISerializationContext context, Interface semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     UserData returns UserData
	 *
	 * Constraint:
	 *     (name=ID expr=Expression)
	 */
	protected void sequence_UserData(ISerializationContext context, UserData semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ExportPackage.Literals.USER_DATA__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ExportPackage.Literals.USER_DATA__NAME));
			if (transientValues.isValueTransient(semanticObject, ExportPackage.Literals.USER_DATA__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ExportPackage.Literals.USER_DATA__EXPR));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getUserDataAccess().getNameIDTerminalRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getUserDataAccess().getExprExpressionParserRuleCall_2_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
}
