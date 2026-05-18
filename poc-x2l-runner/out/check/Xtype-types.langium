import 'JavaVMTypes-types'

interface XFunctionTypeRef extends JvmSpecializedTypeReference {
	paramTypes: JvmTypeReference[]
	returnType?: JvmTypeReference
	instanceContext: boolean
}

interface XImportSection {
	importDeclarations: XImportDeclaration[]
}

interface XImportDeclaration {
	wildcard: boolean
	extension: boolean
	static: boolean
	importedType?: @JvmDeclaredType
	memberName?: string
	importedNamespace?: string
}

