package fieldday;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.PilotProps;

/**
* This program runs on a differential robot that will move along a straight path
* pushing the ball in front of it.
* We assume the ball is placed in the front center of the robot at the start,
* and the program is manually stopped after the ball is placed into a box.
*
* @author jjumadinova
*
*/

public class PushRace {
	public static void main(String[] args) {
		// Create a configuration object for the Differential Pilot
		PilotProps p = new PilotProps();
		RegulatedMotor leftMotor = PilotProps.getMotor(p.getProperty(PilotProps.KEY_LEFTMOTOR, "B"));
		RegulatedMotor rightMotor = PilotProps.getMotor(p.getProperty(PilotProps.KEY_RIGHTMOTOR, "C"));

		float TYRE_DIAMETER = 5.6f;
		final float AXLE_TRACK = 12.0f;
		DifferentialPilot pilot = new DifferentialPilot(
		TYRE_DIAMETER, AXLE_TRACK, leftMotor, rightMotor);

		//*** CHANGE the SPEED here ***//
		pilot.setAcceleration(5);

		System.out.println("Push a button to start the pushing race!");
		Button.waitForAnyPress();

		// until the escape button is pressed
		while(Button.ESCAPE.isUp()) {
			//*** CHANGE the TRAVEL forward DISTANCE here **//
			// Could use forward method //
			pilot.travel(10); //cm

		}
		System.out.println("Finished the race");
		Sound.beepSequenceUp();

	}

}
