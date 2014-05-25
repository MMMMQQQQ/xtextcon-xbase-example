package org.xtextcon.xbase.smarthome.jvmmodel;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.common.types.JvmEnumerationLiteral;
import org.eclipse.xtext.common.types.JvmEnumerationType;
import org.eclipse.xtext.common.types.JvmField;
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
import org.xtextcon.xbase.smarthome.lib.Simulator;
import org.xtextcon.xbase.smarthome.lib.TimeDependent;
import org.xtextcon.xbase.smarthome.rules.Declaration;
import org.xtextcon.xbase.smarthome.rules.Device;
import org.xtextcon.xbase.smarthome.rules.Model;
import org.xtextcon.xbase.smarthome.rules.Rule;
import org.xtextcon.xbase.smarthome.rules.State;
import org.xtextcon.xbase.smarthome.rules.TimeLiteral;

/**
 * <p>Infers a JVM model from the source model.</p>
 * 
 * <p>The JVM model should contain all elements that would appear in the Java code
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>
 */
@SuppressWarnings("all")
public class RulesJvmModelInferrer extends AbstractModelInferrer {
  /**
   * Standard service to create JVM types, e.g. classes, fields and methods
   */
  @Inject
  @Extension
  private JvmTypesBuilder _jvmTypesBuilder;
  
  /**
   * Utility to create type references, e.g. used to produce the signature {@code trigger(Enum<?>)}
   */
  @Inject
  @Extension
  private TypeReferences _typeReferences;
  
  /**
   * Type inferencer. May only be used to compute the body of a method or the initializer of a field.
   */
  @Inject
  private IBatchTypeResolver batchTypeResolver;
  
  /**
   * Infers a couple of classes from a model, e.g. enums for the devices and a state machines for the simulator.
   */
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
          RulesJvmModelInferrer.this.initializeResourceBundle(it, model, machineName);
          final Function1<Rule, Boolean> _function = new Function1<Rule, Boolean>() {
            public Boolean apply(final Rule it) {
              TimeLiteral _time = it.getTime();
              return Boolean.valueOf((!Objects.equal(_time, null)));
            }
          };
          Iterable<Rule> _filter = IterableExtensions.<Rule>filter(rules, _function);
          RulesJvmModelInferrer.this.initializeTimeEvents(it, model, _filter);
          final Function1<Rule, Boolean> _function_1 = new Function1<Rule, Boolean>() {
            public Boolean apply(final Rule it) {
              State _when = it.getWhen();
              return Boolean.valueOf((!Objects.equal(_when, null)));
            }
          };
          Iterable<Rule> _filter_1 = IterableExtensions.<Rule>filter(rules, _function_1);
          RulesJvmModelInferrer.this.initializeStateEvents(it, model, _filter_1);
          final Function1<Rule, Boolean> _function_2 = new Function1<Rule, Boolean>() {
            public Boolean apply(final Rule it) {
              State _when = it.getWhen();
              return Boolean.valueOf((!Objects.equal(_when, null)));
            }
          };
          Iterable<Rule> _filter_2 = IterableExtensions.<Rule>filter(rules, _function_2);
          final Function1<Rule, Boolean> _function_3 = new Function1<Rule, Boolean>() {
            public Boolean apply(final Rule it) {
              TimeLiteral _time = it.getTime();
              return Boolean.valueOf((!Objects.equal(_time, null)));
            }
          };
          Iterable<Rule> _filter_3 = IterableExtensions.<Rule>filter(rules, _function_3);
          RulesJvmModelInferrer.this.initializeRuleEngine(it, model, _filter_2, _filter_3);
          RulesJvmModelInferrer.this.initializeActions(it, model, rules);
          RulesJvmModelInferrer.this.initializeMain(it, model);
        }
      };
      _accept.initializeLater(_function_1);
    }
  }
  
  /**
   * Creates a field for the generated resource bundle (see {@link SmarthomeGenerator}) and a getter
   * to obtain the strings from that bundle.
   */
  public boolean initializeResourceBundle(final JvmGenericType type, final Model model, final String bundleName) {
    boolean _xblockexpression = false;
    {
      EList<JvmMember> _members = type.getMembers();
      JvmTypeReference _newTypeRef = this._jvmTypesBuilder.newTypeRef(model, ResourceBundle.class);
      final Procedure1<JvmField> _function = new Procedure1<JvmField>() {
        public void apply(final JvmField it) {
          it.setStatic(true);
          it.setFinal(true);
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              _builder.append(ResourceBundle.class, "");
              _builder.append(".getBundle(\"");
              _builder.append(bundleName, "");
              _builder.append("\");");
              _builder.newLineIfNotEmpty();
            }
          };
          RulesJvmModelInferrer.this._jvmTypesBuilder.setInitializer(it, _client);
        }
      };
      JvmField _field = this._jvmTypesBuilder.toField(model, "RESOURCE_BUNDLE", _newTypeRef, _function);
      this._jvmTypesBuilder.<JvmField>operator_add(_members, _field);
      EList<JvmMember> _members_1 = type.getMembers();
      JvmTypeReference _newTypeRef_1 = this._jvmTypesBuilder.newTypeRef(model, String.class);
      final Procedure1<JvmOperation> _function_1 = new Procedure1<JvmOperation>() {
        public void apply(final JvmOperation it) {
          EList<JvmFormalParameter> _parameters = it.getParameters();
          JvmTypeReference _newTypeRef = RulesJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(model, String.class);
          JvmFormalParameter _parameter = RulesJvmModelInferrer.this._jvmTypesBuilder.toParameter(model, "key", _newTypeRef);
          RulesJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
          it.setVisibility(JvmVisibility.PRIVATE);
          it.setStatic(true);
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              _builder.append("return RESOURCE_BUNDLE.getString(key);");
              _builder.newLine();
            }
          };
          RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
        }
      };
      JvmOperation _method = this._jvmTypesBuilder.toMethod(model, "localize", _newTypeRef_1, _function_1);
      _xblockexpression = this._jvmTypesBuilder.<JvmOperation>operator_add(_members_1, _method);
    }
    return _xblockexpression;
  }
  
  /**
   * Produces the logic in the simulator that handles time events, e.g. boolean utility
   * to compare two instances of {@link Calendar} by the time of the day, and the necessary
   * trigger code. One could imagine that this utility could also be implemented in a super type
   * of the simulator.
   */
  public void initializeTimeEvents(final JvmGenericType type, final Model model, final Iterable<? extends Rule> rules) {
    boolean _isEmpty = IterableExtensions.isEmpty(rules);
    boolean _not = (!_isEmpty);
    if (_not) {
      EList<JvmTypeReference> _superTypes = type.getSuperTypes();
      JvmTypeReference _newTypeRef = this._jvmTypesBuilder.newTypeRef(model, TimeDependent.class);
      this._jvmTypesBuilder.<JvmTypeReference>operator_add(_superTypes, _newTypeRef);
    }
    EList<JvmMember> _members = type.getMembers();
    JvmTypeReference _newTypeRef_1 = this._jvmTypesBuilder.newTypeRef(model, Void.TYPE);
    final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
      public void apply(final JvmOperation it) {
        EList<JvmFormalParameter> _parameters = it.getParameters();
        JvmTypeReference _newTypeRef = RulesJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(model, Calendar.class);
        JvmFormalParameter _parameter = RulesJvmModelInferrer.this._jvmTypesBuilder.toParameter(model, "time", _newTypeRef);
        RulesJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            {
              for(final Rule rule : rules) {
                _builder.append("if (isTime(time, ");
                String _timeMethod = RulesJvmModelInferrer.this.getTimeMethod(rule);
                _builder.append(_timeMethod, "");
                _builder.append("())) {");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append(System.class, "\t");
                _builder.append(".out.printf(localize(\"current_time\"), time);");
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
    JvmOperation _method = this._jvmTypesBuilder.toMethod(model, "trigger", _newTypeRef_1, _function);
    this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
    EList<JvmMember> _members_1 = type.getMembers();
    JvmTypeReference _newTypeRef_2 = this._jvmTypesBuilder.newTypeRef(model, boolean.class);
    final Procedure1<JvmOperation> _function_1 = new Procedure1<JvmOperation>() {
      public void apply(final JvmOperation it) {
        EList<JvmFormalParameter> _parameters = it.getParameters();
        JvmTypeReference _newTypeRef = RulesJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(model, Calendar.class);
        JvmFormalParameter _parameter = RulesJvmModelInferrer.this._jvmTypesBuilder.toParameter(model, "c1", _newTypeRef);
        RulesJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
        EList<JvmFormalParameter> _parameters_1 = it.getParameters();
        JvmTypeReference _newTypeRef_1 = RulesJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(model, Calendar.class);
        JvmFormalParameter _parameter_1 = RulesJvmModelInferrer.this._jvmTypesBuilder.toParameter(model, "c2", _newTypeRef_1);
        RulesJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters_1, _parameter_1);
        it.setVisibility(JvmVisibility.PRIVATE);
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append("return c1.get(");
            _builder.append(Calendar.class, "");
            _builder.append(".HOUR_OF_DAY) == c2.get(");
            _builder.append(Calendar.class, "");
            _builder.append(".HOUR_OF_DAY)");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("&& c1.get(");
            _builder.append(Calendar.class, "  ");
            _builder.append(".MINUTE) == c2.get(");
            _builder.append(Calendar.class, "  ");
            _builder.append(".MINUTE);");
            _builder.newLineIfNotEmpty();
          }
        };
        RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
      }
    };
    JvmOperation _method_1 = this._jvmTypesBuilder.toMethod(model, "isTime", _newTypeRef_2, _function_1);
    this._jvmTypesBuilder.<JvmOperation>operator_add(_members_1, _method_1);
    for (final Rule rule : rules) {
      EList<JvmMember> _members_2 = type.getMembers();
      String _timeMethod = this.getTimeMethod(rule);
      TimeLiteral _time = rule.getTime();
      JvmTypeReference _inferredType = this._jvmTypesBuilder.inferredType(_time);
      final Procedure1<JvmOperation> _function_2 = new Procedure1<JvmOperation>() {
        public void apply(final JvmOperation it) {
          TimeLiteral _time = rule.getTime();
          RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _time);
        }
      };
      JvmOperation _method_2 = this._jvmTypesBuilder.toMethod(rule, _timeMethod, _inferredType, _function_2);
      this._jvmTypesBuilder.<JvmOperation>operator_add(_members_2, _method_2);
    }
  }
  
  /**
   * Add the dispatcher for all state-change events, the dispatcher for simulated user
   * interaction like {@code Window open}.
   */
  public boolean initializeStateEvents(final JvmGenericType type, final Model model, final Iterable<? extends Rule> rules) {
    EList<JvmMember> _members = type.getMembers();
    JvmTypeReference _newTypeRef = this._jvmTypesBuilder.newTypeRef(model, Void.TYPE);
    final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
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
            _builder.append(".out.printf(localize(\"received_signal\"), event.getClass().getSimpleName(), event);");
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
    JvmOperation _method = this._jvmTypesBuilder.toMethod(model, "trigger", _newTypeRef, _function);
    return this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
  }
  
  /**
   * Creates the actions that are triggered by the available rules.
   */
  public void initializeActions(final JvmGenericType type, final Model model, final Iterable<? extends Rule> rules) {
    final Function1<Rule, Boolean> _function = new Function1<Rule, Boolean>() {
      public Boolean apply(final Rule it) {
        XExpression _then = it.getThen();
        return Boolean.valueOf((!Objects.equal(_then, null)));
      }
    };
    Iterable<? extends Rule> _filter = IterableExtensions.filter(rules, _function);
    for (final Rule rule : _filter) {
      EList<JvmMember> _members = type.getMembers();
      String _thenMethod = this.getThenMethod(rule);
      XExpression _then = rule.getThen();
      JvmTypeReference _inferredType = this._jvmTypesBuilder.inferredType(_then);
      final Procedure1<JvmOperation> _function_1 = new Procedure1<JvmOperation>() {
        public void apply(final JvmOperation it) {
          XExpression _then = rule.getThen();
          RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _then);
        }
      };
      JvmOperation _method = this._jvmTypesBuilder.toMethod(rule, _thenMethod, _inferredType, _function_1);
      this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
    }
  }
  
  /**
   * Add the {@code run()} method for this state machine. It implements the parsing of the user
   * input and the dispatching of the simulated events. Thereby it complements the time based events.
   */
  public boolean initializeRuleEngine(final JvmGenericType type, final Model model, final Iterable<? extends Rule> stateRules, final Iterable<? extends Rule> timeRules) {
    EList<JvmMember> _members = type.getMembers();
    JvmTypeReference _newTypeRef = this._jvmTypesBuilder.newTypeRef(model, Void.TYPE);
    final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
      public void apply(final JvmOperation it) {
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            {
              boolean _isEmpty = IterableExtensions.isEmpty(timeRules);
              boolean _not = (!_isEmpty);
              if (_not) {
                _builder.append(Simulator.class, "");
                _builder.append(" simulator = new ");
                _builder.append(Simulator.class, "");
                _builder.append("(localize(\"set_time\"));");
                _builder.newLineIfNotEmpty();
                _builder.append("simulator.submit(this);");
                _builder.newLine();
              }
            }
            _builder.append(Scanner.class, "");
            _builder.append(" sc = new ");
            _builder.append(Scanner.class, "");
            _builder.append("(");
            _builder.append(System.class, "");
            _builder.append(".in);");
            _builder.newLineIfNotEmpty();
            _builder.append(System.class, "");
            _builder.append(".out.println(localize(\"simulator_started\"));");
            _builder.newLineIfNotEmpty();
            {
              boolean _isEmpty_1 = IterableExtensions.isEmpty(timeRules);
              boolean _not_1 = (!_isEmpty_1);
              if (_not_1) {
                _builder.append(System.class, "");
                _builder.append(".out.println(\" - Set time HH:mm\");");
                _builder.newLineIfNotEmpty();
              }
            }
            {
              final Function1<Rule, Device> _function = new Function1<Rule, Device>() {
                public Device apply(final Rule it) {
                  State _when = it.getWhen();
                  return RulesJvmModelInferrer.this.getDevice(_when);
                }
              };
              Iterable<Device> _map = IterableExtensions.map(stateRules, _function);
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
            _builder.append(".out.println(localize(\"waiting\"));");
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
            {
              boolean _isEmpty_2 = IterableExtensions.isEmpty(timeRules);
              boolean _not_2 = (!_isEmpty_2);
              if (_not_2) {
                _builder.append("\t");
                _builder.append("if (split.length == 3) {");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("String[] time = split[2].split(\":\");");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("simulator.setTime(Integer.parseInt(time[0]), Integer.parseInt(time[1]));");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("continue;");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("}");
                _builder.newLine();
              }
            }
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
              Iterable<Device> _map_2 = IterableExtensions.map(stateRules, _function_2);
              Iterable<Device> _filterNull = IterableExtensions.<Device>filterNull(_map_2);
              for(final Device device : _filterNull) {
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
                _builder.append(".err.printf(localize(\"state_unknown\"), split[1], split[0]);");
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
            _builder.append(".err.printf(localize(\"device_unknown\"), split[0]);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(System.class, "\t");
            _builder.append(".out.println(localize(\"waiting\"));");
            _builder.newLineIfNotEmpty();
            _builder.append("}");
            _builder.newLine();
          }
        };
        RulesJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
      }
    };
    JvmOperation _method = this._jvmTypesBuilder.toMethod(model, "run", _newTypeRef, _function);
    return this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
  }
  
  /**
   * Create a java main method that will call the {@code run()} function of this class.
   */
  public boolean initializeMain(final JvmGenericType type, final Model model) {
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
    return this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
  }
  
  /**
   * Query the return type of the rule's action. May only be used during the body processing.
   * That means, it cannot be queried before the model was linked but only at code generation time.
   */
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
  
  public String getTimeMethod(final Rule rule) {
    EObject _eContainer = rule.eContainer();
    EList<EObject> _eContents = _eContainer.eContents();
    int _indexOf = _eContents.indexOf(rule);
    return ("time_" + Integer.valueOf(_indexOf));
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
