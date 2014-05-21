package org.xtextcon.xbase.smarthome.typesystem;

import java.util.List;
import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedFeatures;
import org.xtextcon.xbase.smarthome.lib.CalendarLiterals;

@SuppressWarnings("all")
public class CalendarLiteralsProvider extends ImplicitlyImportedFeatures {
  protected List<Class<?>> getStaticImportClasses() {
    final List<Class<?>> result = super.getStaticImportClasses();
    result.add(CalendarLiterals.class);
    return result;
  }
}
