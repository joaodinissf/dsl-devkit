grammar Xbase
import 'Xtype'
import 'Xbase-types'
import 'JavaVMTypes-types'

entry XExpression returns XExpression:
    XAssignment 
;

XAssignment returns XExpression:
    {XAssignment} feature=[JvmIdentifiableElement:FeatureCallID ] OpSingleAssign value=XAssignment   | XOrExpression ({XBinaryOperation.leftOperand=current} feature=[JvmIdentifiableElement:OpMultiAssign ]   rightOperand=XAssignment  )?  
;

OpSingleAssign returns string:
    '=' 
;

OpMultiAssign returns string:
    '+=' | '-=' | '*=' | '/=' | '%=' | '<' '<' '='  | '>' '>'? '>='  
;

XOrExpression returns XExpression:
    XAndExpression ({XBinaryOperation.leftOperand=current} feature=[JvmIdentifiableElement:OpOr ]   rightOperand=XAndExpression  )*  
;

OpOr returns string:
    '||' 
;

XAndExpression returns XExpression:
    XEqualityExpression ({XBinaryOperation.leftOperand=current} feature=[JvmIdentifiableElement:OpAnd ]   rightOperand=XEqualityExpression  )*  
;

OpAnd returns string:
    '&&' 
;

XEqualityExpression returns XExpression:
    XRelationalExpression ({XBinaryOperation.leftOperand=current} feature=[JvmIdentifiableElement:OpEquality ]   rightOperand=XRelationalExpression  )*  
;

OpEquality returns string:
    '==' | '!=' | '===' | '!==' 
;

XRelationalExpression returns XExpression:
    XOtherOperatorExpression ({XInstanceOfExpression.expression=current} 'instanceof'   ^type=JvmTypeReference   | {XBinaryOperation.leftOperand=current} feature=[JvmIdentifiableElement:OpCompare ]   rightOperand=XOtherOperatorExpression   )* 
;

OpCompare returns string:
    '>=' | '<' '='  | '>' | '<' 
;

XOtherOperatorExpression returns XExpression:
    XAdditiveExpression ({XBinaryOperation.leftOperand=current} feature=[JvmIdentifiableElement:OpOther ]   rightOperand=XAdditiveExpression  )*  
;

OpOther returns string:
    '->' | '..<' | '>' '..'  | '..' | '=>' | '>' ('>' '>'   | '>' ) | '<' ('<' '<'   | '<' | '=>' ) | '<>' | '?:' 
;

XAdditiveExpression returns XExpression:
    XMultiplicativeExpression ({XBinaryOperation.leftOperand=current} feature=[JvmIdentifiableElement:OpAdd ]   rightOperand=XMultiplicativeExpression  )*  
;

OpAdd returns string:
    '+' | '-' 
;

XMultiplicativeExpression returns XExpression:
    XUnaryOperation ({XBinaryOperation.leftOperand=current} feature=[JvmIdentifiableElement:OpMulti ]   rightOperand=XUnaryOperation  )*  
;

OpMulti returns string:
    '*' | '**' | '/' | '%' 
;

XUnaryOperation returns XExpression:
    {XUnaryOperation} feature=[JvmIdentifiableElement:OpUnary ] operand=XUnaryOperation   | XCastedExpression 
;

OpUnary returns string:
    "!" | "-" | "+" 
;

XCastedExpression returns XExpression:
    XPostfixOperation ({XCastedExpression.target=current} 'as'   ^type=JvmTypeReference  )*  
;

XPostfixOperation returns XExpression:
    XMemberFeatureCall {XPostfixOperation.operand=current} feature=[JvmIdentifiableElement:OpPostfix ]  ?  
;

OpPostfix returns string:
    "++" | "--" 
;

XMemberFeatureCall returns XExpression:
    XPrimaryExpression ({XAssignment.assignable=current} ('.' | explicitStatic?="::"  )feature=[JvmIdentifiableElement:FeatureCallID ] OpSingleAssign   value=XAssignment   | {XMemberFeatureCall.memberCallTarget=current} ("." | nullSafe?="?."  | explicitStatic?="::"  )  ('<' typeArguments+=JvmArgumentTypeReference  (',' typeArguments+=JvmArgumentTypeReference  )* '>' )? feature=[JvmIdentifiableElement:IdOrSuper ] (explicitOperationCall?='('  (memberCallArguments+=XShortClosure  | memberCallArguments+=XExpression  (',' memberCallArguments+=XExpression  )*  )?')' )? memberCallArguments+=XClosure ?  )* 
;

XPrimaryExpression returns XExpression:
    XConstructorCall | XBlockExpression | XSwitchExpression | XSynchronizedExpression | XFeatureCall | XLiteral | XIfExpression | XForLoopExpression | XBasicForLoopExpression | XWhileExpression | XDoWhileExpression | XThrowExpression | XReturnExpression | XTryCatchFinallyExpression | XParenthesizedExpression 
;

XLiteral returns XExpression:
    XCollectionLiteral | XClosure | XBooleanLiteral | XNumberLiteral | XNullLiteral | XStringLiteral | XTypeLiteral 
;

XCollectionLiteral returns XCollectionLiteral:
    XSetLiteral | XListLiteral 
;

XSetLiteral returns XSetLiteral:
    {XSetLiteral} '#' '{' (elements+=XExpression  (',' elements+=XExpression  )* )? '}'  
;

XListLiteral returns XListLiteral:
    {XListLiteral} '#' '[' (elements+=XExpression  (',' elements+=XExpression  )* )? ']'  
;

XClosure returns XExpression:
    {XClosure} '['   (declaredFormalParameters+=JvmFormalParameter  (',' declaredFormalParameters+=JvmFormalParameter  )* )? explicitSyntax?='|'   ? expression=XExpressionInClosure  ']'  
;

XExpressionInClosure returns XExpression:
    {XBlockExpression} (expressions+=XExpressionOrVarDeclaration  ';'? )*  
;

XShortClosure returns XExpression:
    {XClosure} (declaredFormalParameters+=JvmFormalParameter  (',' declaredFormalParameters+=JvmFormalParameter  )* )? explicitSyntax?='|'    expression=XExpression   
;

XParenthesizedExpression returns XExpression:
    '(' XExpression ')'  
;

XIfExpression returns XExpression:
    {XIfExpression} 'if' '(' if=XExpression  ')' then=XExpression  (=>'else' else=XExpression  )?  
;

XSwitchExpression returns XExpression:
    {XSwitchExpression} 'switch' ('(' declaredParam=JvmFormalParameter  ':'   switch=XExpression  ')'  | declaredParam=JvmFormalParameter  ':'  ? switch=XExpression   )'{' (cases+=XCasePart )* ('default' ':' default=XExpression  )? '}'  
;

XCasePart returns XCasePart:
    {XCasePart} typeGuard=JvmTypeReference ? ('case' case=XExpression  )? (':' then=XExpression   | fallThrough?=','  ) 
;

XForLoopExpression returns XExpression:
    {XForLoopExpression} 'for' '(' declaredParam=JvmFormalParameter  ':'   forExpression=XExpression  ')' eachExpression=XExpression   
;

XBasicForLoopExpression returns XExpression:
    {XBasicForLoopExpression} 'for' '(' (initExpressions+=XExpressionOrVarDeclaration  (',' initExpressions+=XExpressionOrVarDeclaration  )* )? ';' expression=XExpression ? ';' (updateExpressions+=XExpression  (',' updateExpressions+=XExpression  )* )? ')' eachExpression=XExpression   
;

XWhileExpression returns XExpression:
    {XWhileExpression} 'while' '(' predicate=XExpression  ')' body=XExpression   
;

XDoWhileExpression returns XExpression:
    {XDoWhileExpression} 'do' body=XExpression  'while' '(' predicate=XExpression  ')'  
;

XBlockExpression returns XExpression:
    {XBlockExpression} '{' (expressions+=XExpressionOrVarDeclaration  ';'? )* '}'  
;

XExpressionOrVarDeclaration returns XExpression:
    XVariableDeclaration | XExpression 
;

XVariableDeclaration returns XExpression:
    {XVariableDeclaration} (writeable?='var'  | 'val' )(^type=JvmTypeReference  name=ValidID    | name=ValidID  )('=' right=XExpression  )?  
;

JvmFormalParameter returns JvmFormalParameter:
    (parameterType=JvmTypeReference )? name=ValidID   
;

FullJvmFormalParameter returns JvmFormalParameter:
    parameterType=JvmTypeReference  name=ValidID   
;

XFeatureCall returns XExpression:
    {XFeatureCall} ('<' typeArguments+=JvmArgumentTypeReference  (',' typeArguments+=JvmArgumentTypeReference  )* '>' )? feature=[JvmIdentifiableElement:IdOrSuper ] (explicitOperationCall?='('  (featureCallArguments+=XShortClosure  | featureCallArguments+=XExpression  (',' featureCallArguments+=XExpression  )*  )?')' )? featureCallArguments+=XClosure ?  
;

FeatureCallID returns string:
    ValidID | 'extends' | 'static' | 'import' | 'extension' 
;

IdOrSuper returns string:
    FeatureCallID | 'super' 
;

XConstructorCall returns XExpression:
    {XConstructorCall} 'new' constructor=[JvmConstructor:QualifiedName ] (=>'<' typeArguments+=JvmArgumentTypeReference  (',' typeArguments+=JvmArgumentTypeReference  )* '>' )? (explicitConstructorCall?='('  (arguments+=XShortClosure  | arguments+=XExpression  (',' arguments+=XExpression  )*  )?')' )? arguments+=XClosure ?  
;

XBooleanLiteral returns XExpression:
    {XBooleanLiteral} ('false' | isTrue?='true'  ) 
;

XNullLiteral returns XExpression:
    {XNullLiteral} 'null'  
;

XNumberLiteral returns XExpression:
    {XNumberLiteral} value=Number   
;

XStringLiteral returns XExpression:
    {XStringLiteral} value=STRING   
;

XTypeLiteral returns XExpression:
    {XTypeLiteral} 'typeof' '(' ^type=[JvmType:QualifiedName ] (arrayDimensions+=ArrayBrackets )* ')'  
;

XThrowExpression returns XExpression:
    {XThrowExpression} 'throw' expression=XExpression   
;

XReturnExpression returns XExpression:
    {XReturnExpression} 'return' (expression=XExpression )?  
;

XTryCatchFinallyExpression returns XExpression:
    {XTryCatchFinallyExpression} 'try' expression=XExpression  (catchClauses+=XCatchClause + (=>'finally' finallyExpression=XExpression  )?  | 'finally' finallyExpression=XExpression   ) 
;

XSynchronizedExpression returns XExpression:
    {XSynchronizedExpression} 'synchronized' '('   param=XExpression  ')' expression=XExpression   
;

XCatchClause returns XCatchClause:
    =>'catch' '(' declaredParam=FullJvmFormalParameter  ')' expression=XExpression   
;

QualifiedName returns string:
    ValidID (=>'.' ValidID )*  
;

Number returns string:
    HEX | (INT | DECIMAL )('.' (INT | DECIMAL ))?  
;

StaticQualifier returns string:
    (ValidID '::' )+ 
;

terminal HEX returns string:('0x' | '0X' )('0' ..'9' | 'a' ..'f' | 'A' ..'F' | '_' )+('#' ((('b' | 'B' )('i' | 'I' )) | ('l' | 'L' )))?  ;
terminal INT returns number:'0' ..'9' ('0' ..'9' | '_' )* ;
terminal DECIMAL returns string:INT (('e' | 'E' )('+' | '-' )?INT )? ((('b' | 'B' )('i' | 'I' | 'd' | 'D' )) | ('l' | 'L' | 'd' | 'D' | 'f' | 'F' ))? ;
