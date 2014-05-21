package my.home.is.my.castle;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;
import my.home.is.my.castle.Radiator;
import my.home.is.my.castle.TV;
import my.home.is.my.castle.Window;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.xtextcon.xbase.smarthome.lib.CalendarLiterals;
import org.xtextcon.xbase.smarthome.lib.Simulator;
import org.xtextcon.xbase.smarthome.lib.TimeDependent;

@SuppressWarnings("all")
public class MyRuleEngine implements TimeDependent {
  private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("my.home.is.my.castle.MyRuleEngine");;
  
  private static String localize(final String key) {
    return RESOURCE_BUNDLE.getString(key);
  }
  
  public void trigger(final Calendar time) {
    if (isTime(time, time_5())) {
    	System.out.printf(localize("current_time"), time);
    	trigger(then_5());
    }
    if (isTime(time, time_6())) {
    	System.out.printf(localize("current_time"), time);
    	trigger(then_6());
    }
    if (isTime(time, time_7())) {
    	System.out.printf(localize("current_time"), time);
    	trigger(then_7());
    }
  }
  
  private boolean isTime(final Calendar c1, final Calendar c2) {
    return c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY)
      && c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE);
  }
  
  public Calendar time_5() {
    final Calendar _calendar = Calendar.getInstance();
    _calendar.set(0, 0, 0, 20, 15, 0);
    return _calendar;
  }
  
  public Calendar time_6() {
    final Calendar _calendar = Calendar.getInstance();
    _calendar.set(0, 0, 0, 20, 0, 0);
    return _calendar;
  }
  
  public Calendar time_7() {
    final Calendar _calendar = Calendar.getInstance();
    _calendar.set(0, 0, 0, 22, 0, 0);
    return _calendar;
  }
  
  protected void trigger(final Enum<?> event) {
    System.out.printf(localize("received_signal"), event.getClass().getSimpleName(), event);
    if (event == Window.open) {
    	trigger(then_3());
    }
    if (event == Radiator.on) {
    	trigger(then_4());
    }
    if (event == TV.on) {
    	then_8();
    }
  }
  
  public void run() {
    Simulator simulator = new Simulator(localize("set_time"));
    simulator.submit(this);
    Scanner sc = new Scanner(System.in);
    System.out.println(localize("simulator_started"));
    System.out.println(" - Set time HH:mm");
    System.out.println(" - Window open");
    System.out.println(" - Window closed");
    System.out.println(" - Radiator on");
    System.out.println(" - Radiator off");
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
    		case "Window":
    			switch(split[1]) {
    				case "open":
    					trigger(Window.open);
    					break;
    				case "closed":
    					trigger(Window.closed);
    					break;
    				default:
    					System.err.printf(localize("state_unknown"), split[1], split[0]);
    			}
    			break;
    		case "Radiator":
    			switch(split[1]) {
    				case "on":
    					trigger(Radiator.on);
    					break;
    				case "off":
    					trigger(Radiator.off);
    					break;
    				default:
    					System.err.printf(localize("state_unknown"), split[1], split[0]);
    			}
    			break;
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
  
  public Radiator then_3() {
    return Radiator.off;
  }
  
  public Window then_4() {
    return Window.closed;
  }
  
  public TV then_5() {
    return TV.off;
  }
  
  public TV then_6() {
    return TV.on;
  }
  
  public TV then_7() {
    return TV.off;
  }
  
  public void then_8() {
    Calendar _time = CalendarLiterals.time();
    final Calendar _calendar = Calendar.getInstance();
    _calendar.set(0, 0, 0, 22, 0, 0);
    boolean _greaterThan = (_time.compareTo(_calendar) > 0);
    if (_greaterThan) {
      InputOutput.<String>println("Isn\'t it too late for TV?");
      this.trigger(TV.off);
    }
  }
  
  public static void main(final String[] args) {
    new MyRuleEngine().run();
  }
}
