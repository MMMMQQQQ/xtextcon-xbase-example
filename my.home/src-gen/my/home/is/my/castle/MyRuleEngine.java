package my.home.is.my.castle;

import java.util.Scanner;
import my.home.is.my.castle.Radiator;
import my.home.is.my.castle.Window;

@SuppressWarnings("all")
public class MyRuleEngine {
  public static void main(final String[] args) {
    new MyRuleEngine().run();
  }
  
  public void run() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Simulator started. These commands are available: ");
    System.out.println(" - Window open");
    System.out.println(" - Window closed");
    System.out.println(" - Radiator on");
    System.out.println(" - Radiator off");
    System.out.println("Waiting for input...");
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
  
  protected void trigger(final Enum<?> event) {
    System.out.println("Received signal '"+event.getClass().getSimpleName()+" "+event+"'.");
    if (event == Window.open) {
    	trigger(then_2());
    }
    if (event == Radiator.on) {
    	trigger(then_3());
    }
  }
  
  public Radiator then_2() {
    return Radiator.off;
  }
  
  public Window then_3() {
    return Window.closed;
  }
}
