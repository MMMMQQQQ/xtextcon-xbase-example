package org.xtextcon.xbase.smarthome.generator;

import java.util.Arrays;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.xtextcon.xbase.smarthome.rules.Model;

@SuppressWarnings("all")
public class SmarthomeGenerator extends JvmModelGenerator {
  protected void _internalDoGenerate(final Model model, final IFileSystemAccess fsa) {
    Resource _eResource = model.eResource();
    URI _uRI = _eResource.getURI();
    URI _trimFileExtension = _uRI.trimFileExtension();
    String _lastSegment = _trimFileExtension.lastSegment();
    String _firstUpper = StringExtensions.toFirstUpper(_lastSegment);
    String _plus = ("my/home/is/my/castle/" + _firstUpper);
    final String resourceName = (_plus + "RuleEngine.properties");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("current_time = Current time \'%tR\'.\\\\n");
    _builder.newLine();
    _builder.append("received_signal = Received signal \'%s %s\'.\\n");
    _builder.newLine();
    _builder.append("simulator_started = Simulator started. These commands are available:");
    _builder.newLine();
    _builder.append("waiting = Waiting for input...");
    _builder.newLine();
    _builder.append("state_unknown = The state %s is not defined for device %s.\\n");
    _builder.newLine();
    _builder.append("device_unknown = Unknown device %s.\\n");
    _builder.newLine();
    _builder.append("set_time = Time set to %tR\\n");
    _builder.newLine();
    fsa.generateFile(resourceName, _builder);
  }
  
  public void internalDoGenerate(final EObject model, final IFileSystemAccess fsa) {
    if (model instanceof JvmDeclaredType) {
      _internalDoGenerate((JvmDeclaredType)model, fsa);
      return;
    } else if (model instanceof Model) {
      _internalDoGenerate((Model)model, fsa);
      return;
    } else if (model != null) {
      _internalDoGenerate(model, fsa);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(model, fsa).toString());
    }
  }
}
