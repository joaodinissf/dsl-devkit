grammar Xtype
import 'JavaVMTypes-types'
import 'Xtype-types'

entry JvmTypeReference returns JvmTypeReference:
    JvmParameterizedTypeReference {JvmGenericArrayTypeReference.componentType=current} ArrayBrackets  *  | XFunctionTypeRef 
;

ArrayBrackets returns string:
    '[' ']'  
;

XFunctionTypeRef returns XFunctionTypeRef:
    ('(' (paramTypes+=JvmTypeReference  (',' paramTypes+=JvmTypeReference  )* )? ')' )? '=>' returnType=JvmTypeReference   
;

JvmParameterizedTypeReference returns JvmParameterizedTypeReference:
    ^type=[JvmType:QualifiedName ] (=>'<' arguments+=JvmArgumentTypeReference  (',' arguments+=JvmArgumentTypeReference  )* '>' ({JvmInnerTypeReference.outer=current} '.'   ^type=[JvmType:ValidID ] (=>'<' arguments+=JvmArgumentTypeReference  (',' arguments+=JvmArgumentTypeReference  )* '>' )? )* )?  
;

JvmArgumentTypeReference returns JvmTypeReference:
    JvmTypeReference | JvmWildcardTypeReference 
;

JvmWildcardTypeReference returns JvmWildcardTypeReference:
    {JvmWildcardTypeReference} '?' (constraints+=JvmUpperBound  (constraints+=JvmUpperBoundAnded )*  | constraints+=JvmLowerBound  (constraints+=JvmLowerBoundAnded )*  )? 
;

JvmUpperBound returns JvmUpperBound:
    'extends' typeReference=JvmTypeReference   
;

JvmUpperBoundAnded returns JvmUpperBound:
    '&' typeReference=JvmTypeReference   
;

JvmLowerBound returns JvmLowerBound:
    'super' typeReference=JvmTypeReference   
;

JvmLowerBoundAnded returns JvmLowerBound:
    '&' typeReference=JvmTypeReference   
;

JvmTypeParameter returns JvmTypeParameter:
    name=ValidID  (constraints+=JvmUpperBound  (constraints+=JvmUpperBoundAnded )* )?  
;

QualifiedName returns string:
    ValidID ('.' ValidID )*  
;

QualifiedNameWithWildcard returns string:
    QualifiedName '.' '*'  
;

ValidID returns string:
    ID 
;

XImportSection returns XImportSection:
    importDeclarations+=XImportDeclaration + 
;

XImportDeclaration returns XImportDeclaration:
    'import' ((static?='static'  extension?='extension' ? importedType=[JvmDeclaredType:QualifiedNameInStaticImport ] (wildcard?='*'  | memberName=ValidID  )) | importedType=[JvmDeclaredType:QualifiedName ] | importedNamespace=QualifiedNameWithWildcard  )';'?  
;

QualifiedNameInStaticImport returns string:
    (ValidID '.' )+ 
;

terminal ID returns string:'^'? ('a' ..'z' | 'A' ..'Z' | '$' | '_' )('a' ..'z' | 'A' ..'Z' | '$' | '_' | '0' ..'9' )* ;
terminal STRING returns string:'"' ('\\' . |  !('\\' | '"' ))*'"'?  | "'" ('\\' . |  !('\\' | "'" ))*"'"?  ;
hidden terminal ML_COMMENT returns string:'/*'  -> '*/'  ;
hidden terminal SL_COMMENT returns string:'//'  !('\n' | '\r' )('\r'? '\n' )?  ;
hidden terminal WS returns string:(' ' | '\t' | '\r' | '\n' )+;
terminal ANY_OTHER returns string:.;
