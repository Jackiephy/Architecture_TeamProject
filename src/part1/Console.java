package part1;

import java.awt.EventQueue;

/**
*
* @author Alina
*/
public class Console {
	public static void main(String[] args) {
        //GUI show    
	GUI ui = new GUI();
		
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                new Console();
            }
        });
        //to do: GUI and ROM Loader program
    }
}
