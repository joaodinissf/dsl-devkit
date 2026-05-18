grammar XbaseWithAnnotations
import 'Xbase'
import 'XAnnotations-types'
import 'Xbase-types'
import 'JavaVMTypes-types'

entry XAnnotation returns XAnnotation:
    {XAnnotation} '@' annotationType=[JvmAnnotationType:QualifiedName ] (=>'(' (elementValuePairs+=XAnnotationElementValuePair  (',' elementValuePairs+=XAnnotationElementValuePair  )*  | value=XAnnotationElementValueOrCommaList  )?')' )?  
;

XAnnotationElementValuePair returns XAnnotationElementValuePair:
    element=[JvmOperation:ValidID ] '='   value=XAnnotationElementValue   
;

XAnnotationElementValueOrCommaList returns XExpression:
    {XListLiteral} '#' '['   (elements+=XAnnotationOrExpression  (',' elements+=XAnnotationOrExpression  )* )? ']'  | XAnnotationOrExpression ({XListLiteral.elements+=current} (',' elements+=XAnnotationOrExpression  )+ )?  
;

XAnnotationElementValue returns XExpression:
    {XListLiteral} '#' '['   (elements+=XAnnotationOrExpression  (',' elements+=XAnnotationOrExpression  )* )? ']'  | XAnnotationOrExpression 
;

XAnnotationOrExpression returns XExpression:
    XAnnotation | XExpression 
;

