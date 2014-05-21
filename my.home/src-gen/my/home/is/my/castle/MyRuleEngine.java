package my.home.is.my.castle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import my.home.is.my.castle.Radiator;
import my.home.is.my.castle.TV;
import my.home.is.my.castle.Window;
import org.xtextcon.xbase.smarthome.lib.Simulator;
import org.xtextcon.xbase.smarthome.lib.TimeDependent;

@SuppressWarnings("all")
public class MyRuleEngine implements TimeDependent {
  public void trigger(final Calendar time) {
    if (isTime(time, time_5())) {
    	System.out.println("Current time '"+new SimpleDateFormat("HH:mm").format(time.getTime()) + "'.");
    	trigger(then_5());
    }
    if (isTime(time, time_6())) {
    	System.out.println("Current time '"+new SimpleDateFormat("HH:mm").format(time.getTime()) + "'.");
    	trigger(then_6());
    }
  }
  
  private boolean isTime(final Calendar c1, final Calendar c2) {
    return c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY)
      && c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE);
  }
  
  public Calendar time_5() {
    Calendar cal = Calendar.getInstance();
    cal.set(0, 0, 0, 20, 15, 0);
    return cal;
  }
  
  public Calendar time_6() {
    Calendar cal = Calendar.getInstance();
    cal.set(0, 0, 0, 20, 0, 0);
    return cal;
  }
  
  protected void trigger(final Enum<?> event) {
    System.out.println("Received signal '"+event.getClass().getSimpleName()+" "+event+"'.");
    if (event == Window.open) {
    	trigger(then_3());
    }
    if (event == Radiator.on) {
    	trigger(then_4());
    }
  }
  
  public void run() {
    Simulator simulator = new Simulator();
    simulator.submit(this);
    Scanner sc = new Scanner(System.in);
    System.out.println("Simulator started. These commands are available: ");
    System.out.println(" - Set time HH:mm");
    System.out.println(" - Window open");
    System.out.println(" - Window closed");
    System.out.println(" - Radiator on");
    System.out.println(" - Radiator off");
    System.out.println("Waiting for input...");
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
    					System.err.println("The state "+split[1]+" is not defined for device "+split[0]+".");
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
    					System.err.println("The state "+split[1]+" is not defined for device "+split[0]+".");
    			}
    			break;
    		default:
    			System.err.println("Unknown device "+split[0]+ ".");
    	}
    	System.out.println("Waiting for input...");
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
  
  public static void main(final String[] args) {
    new MyRuleEngine().run();
  }
}
