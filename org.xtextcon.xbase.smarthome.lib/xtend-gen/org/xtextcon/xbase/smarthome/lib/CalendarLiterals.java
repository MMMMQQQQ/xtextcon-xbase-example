package org.xtextcon.xbase.smarthome.lib;

import java.util.Calendar;

@SuppressWarnings("all")
public class CalendarLiterals {
  public static Calendar time() {
    Calendar _instance = Calendar.getInstance();
    return CalendarLiterals.timeOfDay(_instance);
  }
  
  private static Calendar timeOfDay(final Calendar in) {
    final Calendar result = Calendar.getInstance();
    int _get = in.get(Calendar.HOUR_OF_DAY);
    int _get_1 = in.get(Calendar.MINUTE);
    result.set(0, 0, 0, _get, _get_1, 0);
    return result;
  }
}
