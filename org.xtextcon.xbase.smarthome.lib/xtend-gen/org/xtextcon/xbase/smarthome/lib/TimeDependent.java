package org.xtextcon.xbase.smarthome.lib;

import java.util.Calendar;

@SuppressWarnings("all")
public interface TimeDependent {
  public abstract void trigger(final Calendar time);
}
