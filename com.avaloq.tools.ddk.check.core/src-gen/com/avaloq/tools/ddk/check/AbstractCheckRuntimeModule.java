/*
 * generated by Xtext 2.25.0
 */
package com.avaloq.tools.ddk.check;

import com.avaloq.tools.ddk.check.jvmmodel.CheckJvmModelInferrer;
import com.avaloq.tools.ddk.check.parser.antlr.CheckAntlrTokenFileProvider;
import com.avaloq.tools.ddk.check.parser.antlr.CheckParser;
import com.avaloq.tools.ddk.check.parser.antlr.internal.InternalCheckLexer;
import com.avaloq.tools.ddk.check.serializer.CheckSemanticSequencer;
import com.avaloq.tools.ddk.check.serializer.CheckSyntacticSequencer;
import com.avaloq.tools.ddk.check.services.CheckGrammarAccess;
import com.avaloq.tools.ddk.check.validation.CheckValidator;
import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import java.util.Properties;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.common.types.xtext.TypesAwareDefaultGlobalScopeProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.parser.ITokenToStringConverter;
import org.eclipse.xtext.parser.antlr.AntlrTokenDefProvider;
import org.eclipse.xtext.parser.antlr.AntlrTokenToStringConverter;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;
import org.eclipse.xtext.parser.antlr.ITokenDefProvider;
import org.eclipse.xtext.parser.antlr.Lexer;
import org.eclipse.xtext.parser.antlr.LexerBindings;
import org.eclipse.xtext.parser.antlr.LexerProvider;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersStateProvider;
import org.eclipse.xtext.resource.containers.StateBasedContainerManager;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ISyntacticSequencer;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.xbase.annotations.DefaultXbaseWithAnnotationsRuntimeModule;
import org.eclipse.xtext.xbase.annotations.validation.DerivedStateAwareResourceValidator;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelInferrer;
import org.eclipse.xtext.xbase.jvmmodel.JvmLocationInFileProvider;
import org.eclipse.xtext.xbase.scoping.XbaseQualifiedNameProvider;
import org.eclipse.xtext.xbase.typesystem.internal.DefaultBatchTypeResolver;
import org.eclipse.xtext.xbase.typesystem.internal.DefaultReentrantTypeResolver;
import org.eclipse.xtext.xbase.typesystem.internal.LogicalContainerAwareBatchTypeResolver;
import org.eclipse.xtext.xbase.typesystem.internal.LogicalContainerAwareReentrantTypeResolver;
import org.eclipse.xtext.xbase.validation.FeatureNameValidator;
import org.eclipse.xtext.xbase.validation.LogicalContainerAwareFeatureNameValidator;

/**
 * Manual modifications go to {@link CheckRuntimeModule}.
 */
@SuppressWarnings("all")
public abstract class AbstractCheckRuntimeModule extends DefaultXbaseWithAnnotationsRuntimeModule {

	protected Properties properties = null;

	@Override
	public void configure(Binder binder) {
		properties = tryBindProperties(binder, "com/avaloq/tools/ddk/check/Check.properties");
		super.configure(binder);
	}
	
	public void configureLanguageName(Binder binder) {
		binder.bind(String.class).annotatedWith(Names.named(Constants.LANGUAGE_NAME)).toInstance("com.avaloq.tools.ddk.check.Check");
	}
	
	public void configureFileExtensions(Binder binder) {
		if (properties == null || properties.getProperty(Constants.FILE_EXTENSIONS) == null)
			binder.bind(String.class).annotatedWith(Names.named(Constants.FILE_EXTENSIONS)).toInstance("check");
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.grammarAccess.GrammarAccessFragment2
	public ClassLoader bindClassLoaderToInstance() {
		return getClass().getClassLoader();
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.grammarAccess.GrammarAccessFragment2
	public Class<? extends IGrammarAccess> bindIGrammarAccess() {
		return CheckGrammarAccess.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.serializer.SerializerFragment2
	public Class<? extends ISemanticSequencer> bindISemanticSequencer() {
		return CheckSemanticSequencer.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.serializer.SerializerFragment2
	public Class<? extends ISyntacticSequencer> bindISyntacticSequencer() {
		return CheckSyntacticSequencer.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.serializer.SerializerFragment2
	public Class<? extends ISerializer> bindISerializer() {
		return Serializer.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends IParser> bindIParser() {
		return CheckParser.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends ITokenToStringConverter> bindITokenToStringConverter() {
		return AntlrTokenToStringConverter.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends IAntlrTokenFileProvider> bindIAntlrTokenFileProvider() {
		return CheckAntlrTokenFileProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends Lexer> bindLexer() {
		return InternalCheckLexer.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends ITokenDefProvider> bindITokenDefProvider() {
		return AntlrTokenDefProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Provider<? extends InternalCheckLexer> provideInternalCheckLexer() {
		return LexerProvider.create(InternalCheckLexer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureRuntimeLexer(Binder binder) {
		binder.bind(Lexer.class)
			.annotatedWith(Names.named(LexerBindings.RUNTIME))
			.to(InternalCheckLexer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.validation.ValidatorFragment2
	@SingletonBinding(eager=true)
	public Class<? extends CheckValidator> bindCheckValidator() {
		return CheckValidator.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public Class<? extends IContainer.Manager> bindIContainer$Manager() {
		return StateBasedContainerManager.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public Class<? extends IAllContainersState.Provider> bindIAllContainersState$Provider() {
		return ResourceSetBasedAllContainersStateProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public void configureIResourceDescriptions(Binder binder) {
		binder.bind(IResourceDescriptions.class).to(ResourceSetBasedResourceDescriptions.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
	public void configureIResourceDescriptionsPersisted(Binder binder) {
		binder.bind(IResourceDescriptions.class).annotatedWith(Names.named(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS)).to(ResourceSetBasedResourceDescriptions.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return XbaseQualifiedNameProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
	public Class<? extends ILocationInFileProvider> bindILocationInFileProvider() {
		return JvmLocationInFileProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
	public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return TypesAwareDefaultGlobalScopeProvider.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
	public Class<? extends FeatureNameValidator> bindFeatureNameValidator() {
		return LogicalContainerAwareFeatureNameValidator.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
	public Class<? extends DefaultBatchTypeResolver> bindDefaultBatchTypeResolver() {
		return LogicalContainerAwareBatchTypeResolver.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
	public Class<? extends DefaultReentrantTypeResolver> bindDefaultReentrantTypeResolver() {
		return LogicalContainerAwareReentrantTypeResolver.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
	public Class<? extends IResourceValidator> bindIResourceValidator() {
		return DerivedStateAwareResourceValidator.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
	public Class<? extends IJvmModelInferrer> bindIJvmModelInferrer() {
		return CheckJvmModelInferrer.class;
	}
	
}
