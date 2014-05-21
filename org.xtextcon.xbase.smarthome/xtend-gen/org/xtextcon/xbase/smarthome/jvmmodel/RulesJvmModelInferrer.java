package org.xtextcon.xbase.smarthome.jvmmodel;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.Scanner;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.common.types.JvmEnumerationLiteral;
import org.eclipse.xtext.common.types.JvmEnumerationType;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.JvmVisibility;
import org.eclipse.xtext.common.types.JvmWildcardTypeReference;
import org.eclipse.xtext.common.types.util.TypeReferences;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor;
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.typesystem.IBatchTypeResolver;
import org.eclipse.xtext.xbase.typesystem.IResolvedTypes;
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;
import org.xtextcon.xbase.smarthome.rules.Declaration;
import org.xtextcon.xbase.smarthome.rules.Device;
import org.xtextcon.xbase.smarthome.rules.Model;
import org.xtextcon.xbase.smarthome.rules.Rule;
import org.xtextcon.xbase.smarthome.rules.State;

/**
 * <p>Infers a JVM model from the source model.</p>
 * 
 * <p>The JVM model should contain all elements that would appear in the Java code
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>
 */
@SuppressWarnings("all")
public class RulesJvmModelInferrer extends AbstractModelInferrer {
  @Inject
  @Extension
  private JvmTypesBuilder _jvmTypesBuilder;
  
  @Inject
  @Extension
  private TypeReferences _typeReferences;
  
  @Inject
  private IBatchTypeResolver batchTypeResolver;
  
  protected void _infer(final Model model, final IJvmDeclaredTypeAcceptor acceptor, final boolean isPreIndexingPhase) {
    final String packageName = "my.home.is.my.castle";
    EList<Declaration> _declarations = model.getDeclarations();
    Iterable<Device> _filter = Iterables.<Device>filter(_declarations, Device.class);
    final Procedure1<Device> _function = new Procedure1<Device>() {
      public void apply(final Device device) {
        String _name = device.getName();
        String _plus = ((packageName + ".") + _name);
        final Procedure1<JvmEnumerationType> _function = new Procedure1<JvmEnumerationType>() {
          public void apply(final JvmEnumerationType it) {
            EList<State> _states = device.getStates();
            for (final State state : _states) {
              EList<JvmMember> _members = it.getMembers();
              String _name = state.getName();
              JvmEnumerationLiteral _enumerationLiteral = RulesJvmModelInferrer.this._jvmTypesBuilder.toEnumerationLiteral(state, _name);
              RulesJvmModelInferrer.this._jvmTypesBuilder.<JvmEnumerationLiteral>operator_add(_members, _enumerationLiteral);
            }
          }
        };
        JvmEnumerationType _enumerationType = RulesJvmModelInferrer.this._jvmTypesBuilder.toEnumerationType(device, _plus, _function);
        acceptor.<JvmEnumerationType>accept(_enumerationType);
      }
    };
    IterableExtensions.<Device>forEach(_filter, _function);
    EList<Declaration> _declarations_1 = model.getDeclarations();
    final Iterable<Rule> rules = Iterables.<Rule>filter(_declarations_1, Rule.class);
    boolean _isEmpty = IterableExtensions.isEmpty(rules);
    boolean _not = (!_isEmpty);
    if (_not) {
      Resource _eResource = model.eResource();
      URI _uRI = _eResource.getURI();
      URI _trimFileExtension = _uRI.trimFileExtension();
      String _lastSegment = _trimFileExtension.lastSegment();
      String _firstUpper = StringExtensions.toFirstUpper(_lastSegment);
      String _plus = ((packageName + ".") + _firstUpper);
      final String machineName = (_plus + "RuleEngine");
      JvmGenericType _class = this._jvmTypesBuilder.toClass(model, machineName);
      IJvmDeclaredTypeAcceptor.IPostIndexingInitializing<JvmGenericType> _accept = acceptor.<JvmGenericType>accept(_class);
      final Procedure1<JvmGenericType> _function_1 = new Procedure1<JvmGenericType>() {
        public void apply(final JvmGenericType it) {
          RulesJvmModelInferrer.this.initializeRuleEngine(it, model, rules);
        }
      };
      _accept.initializeLater(_function_1);
    }
  }
  
  public void initializeRuleEngine(final JvmGenericType type, final Model model, final Iterable<? extends Rule> rules) {
    EList<JvmMember> _members = type.getMembers();
    JvmTypeReference _newTypeRef = this._jvmTypesBuilder.newTypeRef(model, Void.TYPE);
    final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
      public void apply(final JvmOperation it) {
        EList<JvmFormalParameter> _parameters = it.getParameters();
        JvmTypeReference _newTypeRef = RulesJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(model, String.class);
        JvmTypeReference _addArrayTypeDimension = RulesJvmModelInferrer.this._jvmTypesBuilder.addArrayTypeDimension(_newTypeRef);
        JvmFormalParameter _parameter = RulesJvmModelInferrer.this._jvmTypesBuilder.toParameter(model, "args", _addArrayTypeDimension);
        RulesJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
        it.setStatic(true);
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append("new ");
            String _simpleName = type.getSimpleName();
            _builder.append(_simpleName, "");
            _builder.append("().run();");
            _builder.newLineIfNotEmpty();
          }
        };
        RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
      }
    };
    JvmOperation _method = this._jvmTypesBuilder.toMethod(model, "main", _newTypeRef, _function);
    this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
    EList<JvmMember> _members_1 = type.getMembers();
    JvmTypeReference _newTypeRef_1 = this._jvmTypesBuilder.newTypeRef(model, Void.TYPE);
    final Procedure1<JvmOperation> _function_1 = new Procedure1<JvmOperation>() {
      public void apply(final JvmOperation it) {
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append(Scanner.class, "");
            _builder.append(" sc = new ");
            _builder.append(Scanner.class, "");
            _builder.append("(");
            _builder.append(System.class, "");
            _builder.append(".in);");
            _builder.newLineIfNotEmpty();
            _builder.append(System.class, "");
            _builder.append(".out.println(\"Simulator started. These commands are available: \");");
            _builder.newLineIfNotEmpty();
            {
              final Function1<Rule, Device> _function = new Function1<Rule, Device>() {
                public Device apply(final Rule it) {
                  State _when = it.getWhen();
                  return RulesJvmModelInferrer.this.getDevice(_when);
                }
              };
              Iterable<Device> _map = IterableExtensions.map(rules, _function);
              final Function1<Device, EList<State>> _function_1 = new Function1<Device, EList<State>>() {
                public EList<State> apply(final Device it) {
                  return it.getStates();
                }
              };
              Iterable<EList<State>> _map_1 = IterableExtensions.<Device, EList<State>>map(_map, _function_1);
              Iterable<State> _flatten = Iterables.<State>concat(_map_1);
              for(final State state : _flatten) {
                _builder.append(System.class, "");
                _builder.append(".out.println(\" - ");
                Device _device = RulesJvmModelInferrer.this.getDevice(state);
                String _name = _device.getName();
                _builder.append(_name, "");
                _builder.append(" ");
                String _name_1 = state.getName();
                _builder.append(_name_1, "");
                _builder.append("\");");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append(System.class, "");
            _builder.append(".out.println(\"Waiting for input...\");");
            _builder.newLineIfNotEmpty();
            _builder.append("while(sc.hasNextLine()) {");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(String.class, "\t");
            _builder.append(" command = sc.nextLine();");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(String.class, "\t");
            _builder.append("[] split = command.split(\" \");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("switch(split[0]) {");
            _builder.newLine();
            {
              final Function1<Rule, Device> _function_2 = new Function1<Rule, Device>() {
                public Device apply(final Rule it) {
                  State _when = it.getWhen();
                  return RulesJvmModelInferrer.this.getDevice(_when);
                }
              };
              Iterable<Device> _map_2 = IterableExtensions.map(rules, _function_2);
              for(final Device device : _map_2) {
                _builder.append("\t\t");
                _builder.append("case \"");
                String _name_2 = device.getName();
                _builder.append(_name_2, "\t\t");
                _builder.append("\":");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("switch(split[1]) {");
                _builder.newLine();
                {
                  EList<State> _states = device.getStates();
                  for(final State state_1 : _states) {
                    _builder.append("\t\t");
                    _builder.append("\t\t");
                    _builder.append("case \"");
                    String _name_3 = state_1.getName();
                    _builder.append(_name_3, "\t\t\t\t");
                    _builder.append("\":");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("trigger(");
                    String _name_4 = device.getName();
                    _builder.append(_name_4, "\t\t\t\t\t");
                    _builder.append(".");
                    String _name_5 = state_1.getName();
                    _builder.append(_name_5, "\t\t\t\t\t");
                    _builder.append(");");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("break;");
                    _builder.newLine();
                  }
                }
                _builder.append("\t\t");
                _builder.append("\t\t");
                _builder.append("default:");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("\t\t\t");
                _builder.append(System.class, "\t\t\t\t\t");
                _builder.append(".err.println(\"The state \"+split[1]+\" is not defined for device \"+split[0]+\".\");");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("}");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("break;");
                _builder.newLine();
              }
            }
            _builder.append("\t\t");
            _builder.append("default:");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append(System.class, "\t\t\t");
            _builder.append(".err.println(\"Unknown device \"+split[0]+ \".\");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(System.class, "\t");
            _builder.append(".out.println(\"Waiting for input...\");");
            _builder.newLineIfNotEmpty();
            _builder.append("}");
            _builder.newLine();
          }
        };
        RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
      }
    };
    JvmOperation _method_1 = this._jvmTypesBuilder.toMethod(model, "run", _newTypeRef_1, _function_1);
    this._jvmTypesBuilder.<JvmOperation>operator_add(_members_1, _method_1);
    EList<JvmMember> _members_2 = type.getMembers();
    JvmTypeReference _newTypeRef_2 = this._jvmTypesBuilder.newTypeRef(model, Void.TYPE);
    final Procedure1<JvmOperation> _function_2 = new Procedure1<JvmOperation>() {
      public void apply(final JvmOperation it) {
        EList<JvmFormalParameter> _parameters = it.getParameters();
        JvmTypeReference _newTypeRef = RulesJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(model, Object.class);
        JvmWildcardTypeReference _wildCardExtends = RulesJvmModelInferrer.this._typeReferences.wildCardExtends(_newTypeRef);
        JvmTypeReference _newTypeRef_1 = RulesJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(model, Enum.class, _wildCardExtends);
        JvmFormalParameter _parameter = RulesJvmModelInferrer.this._jvmTypesBuilder.toParameter(model, "event", _newTypeRef_1);
        RulesJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
        it.setVisibility(JvmVisibility.PROTECTED);
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append(System.class, "");
            _builder.append(".out.println(\"Received signal \'\"+event.getClass().getSimpleName()+\" \"+event+\"\'.\");");
            _builder.newLineIfNotEmpty();
            {
              for(final Rule rule : rules) {
                _builder.append("if (event == ");
                State _when = rule.getWhen();
                Device _device = RulesJvmModelInferrer.this.getDevice(_when);
                String _name = _device.getName();
                _builder.append(_name, "");
                _builder.append(".");
                State _when_1 = rule.getWhen();
                String _name_1 = _when_1.getName();
                _builder.append(_name_1, "");
                _builder.append(") {");
                _builder.newLineIfNotEmpty();
                {
                  boolean _triggersEvent = RulesJvmModelInferrer.this.triggersEvent(rule);
                  if (_triggersEvent) {
                    _builder.append("\t");
                    _builder.append("trigger(");
                    String _thenMethod = RulesJvmModelInferrer.this.getThenMethod(rule);
                    _builder.append(_thenMethod, "\t");
                    _builder.append("());");
                    _builder.newLineIfNotEmpty();
                  } else {
                    _builder.append("\t");
                    String _thenMethod_1 = RulesJvmModelInferrer.this.getThenMethod(rule);
                    _builder.append(_thenMethod_1, "\t");
                    _builder.append("();");
                    _builder.newLineIfNotEmpty();
                  }
                }
                _builder.append("}");
                _builder.newLine();
              }
            }
          }
        };
        RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
      }
    };
    JvmOperation _method_2 = this._jvmTypesBuilder.toMethod(model, "trigger", _newTypeRef_2, _function_2);
    this._jvmTypesBuilder.<JvmOperation>operator_add(_members_2, _method_2);
    final Function1<Rule, Boolean> _function_3 = new Function1<Rule, Boolean>() {
      public Boolean apply(final Rule it) {
        XExpression _then = it.getThen();
        return Boolean.valueOf((!Objects.equal(_then, null)));
      }
    };
    Iterable<? extends Rule> _filter = IterableExtensions.filter(rules, _function_3);
    for (final Rule rule : _filter) {
      EList<JvmMember> _members_3 = type.getMembers();
      String _thenMethod = this.getThenMethod(rule);
      XExpression _then = rule.getThen();
      JvmTypeReference _inferredType = this._jvmTypesBuilder.inferredType(_then);
      final Procedure1<JvmOperation> _function_4 = new Procedure1<JvmOperation>() {
        public void apply(final JvmOperation it) {
          XExpression _then = rule.getThen();
          RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _then);
        }
      };
      JvmOperation _method_3 = this._jvmTypesBuilder.toMethod(rule, _thenMethod, _inferredType, _function_4);
      this._jvmTypesBuilder.<JvmOperation>operator_add(_members_3, _method_3);
    }
  }
  
  public boolean triggersEvent(final Rule rule) {
    XExpression _then = rule.getThen();
    final IResolvedTypes types = this.batchTypeResolver.resolveTypes(_then);
    XExpression _then_1 = rule.getThen();
    final LightweightTypeReference returnType = types.getReturnType(_then_1);
    boolean _isSubtypeOf = returnType.isSubtypeOf(Enum.class);
    if (_isSubtypeOf) {
      return true;
    }
    return false;
  }
  
  public String getThenMethod(final Rule rule) {
    EObject _eContainer = rule.eContainer();
    EList<EObject> _eContents = _eContainer.eContents();
    int _indexOf = _eContents.indexOf(rule);
    return ("then_" + Integer.valueOf(_indexOf));
  }
  
  public Device getDevice(final State state) {
    EObject _eContainer = state.eContainer();
    return ((Device) _eContainer);
  }
  
  public void infer(final EObject model, final IJvmDeclaredTypeAcceptor acceptor, final boolean isPreIndexingPhase) {
    if (model instanceof Model) {
      _infer((Model)model, acceptor, isPreIndexingPhase);
      return;
    } else if (model != null) {
      _infer(model, acceptor, isPreIndexingPhase);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(model, acceptor, isPreIndexingPhase).toString());
    }
  }
}
