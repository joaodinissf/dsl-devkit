import 'Xbase-types'
import 'JavaVMTypes-types'

interface XAnnotation extends XExpression {
	elementValuePairs: XAnnotationElementValuePair[]
	annotationType?: @JvmType
	value?: XExpression
}

interface XAnnotationElementValuePair {
	value?: XExpression
	element?: @JvmOperation
}

