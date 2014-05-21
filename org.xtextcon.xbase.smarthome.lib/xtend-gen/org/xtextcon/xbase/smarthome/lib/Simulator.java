package org.xtextcon.xbase.smarthome.lib;

import com.google.common.util.concurrent.RateLimiter;
import java.util.Calendar;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.xtextcon.xbase.smarthome.lib.TimeDependent;

@SuppressWarnings("all")
public class Simulator {
  private Calendar _calendar;
  
  public Calendar getCalendar() {
    return this._calendar;
  }
  
  public void setCalendar(final Calendar calendar) {
    this._calendar = calendar;
  }
  
  public Simulator() {
    Calendar _instance = Calendar.getInstance();
    this.setCalendar(_instance);
    Calendar _calendar = this.getCalendar();
    _calendar.set(Calendar.SECOND, 0);
  }
  
  public void setTime(final int hour, final int min) {
    this.getCalendar();
    synchronized (this.getCalendar()) {
      {
        Calendar _calendar = this.getCalendar();
        _calendar.set(Calendar.HOUR_OF_DAY, hour);
        Calendar _calendar_1 = this.getCalendar();
        _calendar_1.set(Calendar.MINUTE, min);
        InputOutput.<String>println(((("Time set to " + Integer.valueOf(hour)) + ":") + Integer.valueOf(min)));
      }
    }
  }
  
  public void submit(final TimeDependent instance) {
    final Runnable _function = new Runnable() {
      public void run() {
        final Runnable runnable = new Runnable() {
          public void run() {
            Simulator.this.getCalendar();
            synchronized (Simulator.this.getCalendar()) {
              {
                Calendar _calendar = Simulator.this.getCalendar();
                _calendar.add(Calendar.MINUTE, 1);
                Calendar _calendar_1 = Simulator.this.getCalendar();
                instance.trigger(_calendar_1);
              }
            }
          }
        };
        final RateLimiter rateLimiter = RateLimiter.create(1.0);
        boolean _while = true;
        while (_while) {
          {
            rateLimiter.acquire();
            runnable.run();
          }
          _while = true;
        }
      }
    };
    Thread _thread = new Thread(_function);
    _thread.start();
  }
}
