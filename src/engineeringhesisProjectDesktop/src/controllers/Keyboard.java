package controllers;

import java.awt.AWTException;
import java.awt.Robot;

public class Keyboard {

	Robot robot;
	
	public Keyboard() throws AWTException{ 
		robot = new Robot();
	}

	public boolean doClick(byte action, int... arg) {
		for(int event : arg){
			robot.keyPress(event);
		}

		for(int event : arg){
			robot.keyRelease(event);
		}
		return false;
	}

}
