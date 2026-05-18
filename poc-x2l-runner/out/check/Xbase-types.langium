
interface XExpression {
}

interface XListLiteral extends XCollectionLiteral {
}

interface XCollectionLiteral extends XExpression {
	elements: XExpression[]
}

