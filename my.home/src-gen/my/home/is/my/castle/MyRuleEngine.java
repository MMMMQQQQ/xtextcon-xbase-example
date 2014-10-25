package my.home.is.my.castle;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;
import my.home.is.my.castle.Radiator;
import my.home.is.my.castle.Window;

@SuppressWarnings("all")
public class MyRuleEngine {
  private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("my.home.is.my.castle.MyRuleEngine");;
  
  private static String localize(final String key) {
    return RESOURCE_BUNDLE.getString(key);
  }
  
  public void trigger(final Calendar time) {
    
  }
  
  private boolean isTime(final Calendar c1, final Calendar c2) {
    return c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY)
      && c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE);
  }
  
  protected void trigger(final Enum<?> event) {
    System.out.printf(localize("received_signal"), event.getClass().getSimpleName(), event);
    if (event == Window.open) {
    	trigger(then_2());
    }
    if (event == Radiator.on) {
    	trigger(then_3());
    }
  }
  
  public void run() {
    Scanner sc = new Scanner(System.in);
    System.out.println(localize("simulator_started"));
    System.out.println(" - Window open");
    System.out.println(" - Window closed");
    System.out.println(" - Radiator on");
    System.out.println(" - Radiator off");
    System.out.println(localize("waiting"));
    while(sc.hasNextLine()) {
    	String command = sc.nextLine();
    	String[] split = command.split(" ");
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
    		default:
    			System.err.printf(localize("device_unknown"), split[0]);
    	}
    	System.out.println(localize("waiting"));
    }
  }
  
  public Radiator then_2() {
    return Radiator.off;
  }
  
  public Window then_3() {
    return Window.closed;
  }
  
  public static void main(final String... args) {
    new MyRuleEngine().run();
  }
}
