package controllers;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import network.protocol.MouseProtocol;
import network.protocol.Protocol;

public class Mouse {

	Robot robot;

	public Mouse() throws AWTException {
		robot = new Robot();
	}

	public boolean doClick(byte action) throws IllegalArgumentException {

		switch (action) {
		case MouseProtocol.LEFT_PRESSED:
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			break;
		case MouseProtocol.LEFT_RELEASED:
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			break;
		case MouseProtocol.RIGHT_PRESSED:
			robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			break;
		case MouseProtocol.RIGHT_RELEASED:
			robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			break;
		default:
			throw new IllegalArgumentException(
					"Program didn't recognize mouse event\nProbably wrong data were recived " + action);
		}
		return false;
	}

	private void moveMouse(int x, int y) {
		// TODO nie obsluguje dobrze dual screena
		// ekran glowny musi byc po lewej stronie
		// rozwiazanie jest tu
		// http://stackoverflow.com/questions/2941324/how-do-i-set-the-position-of-the-mouse-in-java
		Point currentPosition = MouseInfo.getPointerInfo().getLocation();
		int newX = currentPosition.x + x;
		int newY = currentPosition.y + y;
		robot.mouseMove(newX, newY);
	}

	private void setPointer(int x, int y) {
		robot.mouseMove(x, y);
	}

	public boolean makeMovement(byte action, int... arg) {
		if (arg.length != 2) {
			throw new IllegalArgumentException(
					"Should be array of two integers x and y\n byt instead is array size="
							+ arg.length);
		}
		switch (Protocol.getMode(action)) {
		case MouseProtocol.POINTER_SETUP:
			setPointer(arg[0], arg[1]);
			break;
		case MouseProtocol.POINTER_TRANSLATION:
			moveMouse(arg[0], arg[1]);
			break;
		}
		return false;
	}

	public void moveWheel(int value){
		robot.mouseWheel(value);
	}
}
