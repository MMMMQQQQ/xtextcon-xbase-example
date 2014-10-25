package my.home.is.my.castle;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;
import my.home.is.my.castle.TV;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.xtextcon.xbase.smarthome.lib.CalendarLiterals;
import org.xtextcon.xbase.smarthome.lib.Simulator;
import org.xtextcon.xbase.smarthome.lib.TimeDependent;

@SuppressWarnings("all")
public class MoreRuleEngine implements TimeDependent {
  private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("my.home.is.my.castle.MoreRuleEngine");;
  
  private static String localize(final String key) {
    return RESOURCE_BUNDLE.getString(key);
  }
  
  public void trigger(final Calendar time) {
    if (isTime(time, time_1())) {
    	System.out.printf(localize("current_time"), time);
    	trigger(then_1());
    }
    if (isTime(time, time_2())) {
    	System.out.printf(localize("current_time"), time);
    	trigger(then_2());
    }
    if (isTime(time, time_3())) {
    	System.out.printf(localize("current_time"), time);
    	trigger(then_3());
    }
  }
  
  private boolean isTime(final Calendar c1, final Calendar c2) {
    return c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY)
      && c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE);
  }
  
  public Calendar time_1() {
    final Calendar _calendar = Calendar.getInstance();
    _calendar.set(0, 0, 0, 20, 15, 0);
    return _calendar;
  }
  
  public Calendar time_2() {
    final Calendar _calendar = Calendar.getInstance();
    _calendar.set(0, 0, 0, 20, 0, 0);
    return _calendar;
  }
  
  public Calendar time_3() {
    final Calendar _calendar = Calendar.getInstance();
    _calendar.set(0, 0, 0, 22, 0, 0);
    return _calendar;
  }
  
  protected void trigger(final Enum<?> event) {
    System.out.printf(localize("received_signal"), event.getClass().getSimpleName(), event);
    if (event == TV.on) {
    	then_4();
    }
  }
  
  public void run() {
    Simulator simulator = new Simulator(localize("set_time"));
    simulator.submit(this);
    Scanner sc = new Scanner(System.in);
    System.out.println(localize("simulator_started"));
    System.out.println(" - Set time HH:mm");
    System.out.println(" - TV on");
    System.out.println(" - TV off");
    System.out.println(localize("waiting"));
    while(sc.hasNextLine()) {
    	String command = sc.nextLine();
    	String[] split = command.split(" ");
    	if (split.length == 3) {
    		String[] time = split[2].split(":");
    		simulator.setTime(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
    		continue;
    	}
    	switch(split[0]) {
    		case "TV":
    			switch(split[1]) {
    				case "on":
    					trigger(TV.on);
    					break;
    				case "off":
    					trigger(TV.off);
    					break;
    				default:
    					System.err.printf(localize("state_unknown"), split[1], split[0]);
    			}
    			break;
    		default:
    			System.err.printf(localize("device_unknown"), split[0]);
    	}
    	System.out.println(localize("waiting"));
    }
  }
  
  public TV then_1() {
    return TV.off;
  }
  
  public TV then_2() {
    return TV.on;
  }
  
  public TV then_3() {
    return TV.off;
  }
  
  public void then_4() {
    Calendar _time = CalendarLiterals.time();
    final Calendar _calendar = Calendar.getInstance();
    _calendar.set(0, 0, 0, 22, 0, 0);
    boolean _greaterThan = (_time.compareTo(_calendar) > 0);
    if (_greaterThan) {
      InputOutput.<String>println("Isn\'t it too late for TV?");
      this.trigger(TV.off);
    }
  }
  
  public static void main(final String... args) {
    new MoreRuleEngine().run();
  }
}
