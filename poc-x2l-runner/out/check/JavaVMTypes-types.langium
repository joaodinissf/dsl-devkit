
type JvmVisibility = JvmVisibility_DEFAULT | JvmVisibility_PRIVATE | JvmVisibility_PROTECTED | JvmVisibility_PUBLIC;
type JvmVisibility_DEFAULT = 'DEFAULT';
type JvmVisibility_PRIVATE = 'PRIVATE';
type JvmVisibility_PROTECTED = 'PROTECTED';
type JvmVisibility_PUBLIC = 'PUBLIC';

interface JvmOperation extends JvmExecutable {
	static: boolean
	final: boolean
	abstract: boolean
	returnType?: JvmTypeReference
	defaultValue?: JvmAnnotationValue
	synchronized: boolean
	default: boolean
	native: boolean
	strictFloatingPoint: boolean
}

interface JvmExecutable extends JvmFeature, JvmTypeParameterDeclarator {
	parameters: JvmFormalParameter[]
	exceptions: JvmTypeReference[]
	varArgs: boolean
}

interface JvmFeature extends JvmMember {
	localClasses: JvmGenericType[]
}

interface JvmMember extends JvmAnnotationTarget {
	declaringType?: @JvmDeclaredType
	visibility?: JvmVisibility
	simpleName?: string
	deprecated: boolean
}

interface JvmAnnotationTarget extends JvmIdentifiableElement {
	annotations: JvmAnnotationReference[]
}

interface JvmIdentifiableElement {
}

interface JvmAnnotationReference {
	annotation?: @JvmAnnotationType
	explicitValues: JvmAnnotationValue[]
}

interface JvmAnnotationType extends JvmDeclaredType {
}

interface JvmDeclaredType extends JvmMember, JvmComponentType {
	superTypes: JvmTypeReference[]
	members: JvmMember[]
	abstract: boolean
	static: boolean
	final: boolean
	packageName?: string
}

interface JvmComponentType extends JvmType {
	arrayType?: JvmArrayType
}

interface JvmType extends JvmIdentifiableElement {
}

interface JvmArrayType extends JvmComponentType {
	componentType?: @JvmComponentType
}

interface JvmTypeReference {
}

interface JvmAnnotationValue {
	operation?: @JvmOperation
}

interface JvmGenericType extends JvmDeclaredType, JvmTypeParameterDeclarator {
	^interface: boolean
	strictFloatingPoint: boolean
	anonymous: boolean
}

interface JvmTypeParameterDeclarator {
	typeParameters: JvmTypeParameter[]
}

interface JvmTypeParameter extends JvmComponentType, JvmConstraintOwner {
	name?: string
	declarator?: @JvmTypeParameterDeclarator
}

interface JvmConstraintOwner {
	constraints: JvmTypeConstraint[]
}

interface JvmTypeConstraint {
	typeReference?: JvmTypeReference
	owner?: @JvmConstraintOwner
}

interface JvmFormalParameter extends JvmAnnotationTarget {
	name?: string
	parameterType?: JvmTypeReference
}

