package fieldday;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.PilotProps;

/**
* This program runs on a differential robot that will move straight until
* it gets close to an object, in which case it turns backwards and rotates and moves forward
* again. The robot stops when it detects a red color representing the finish line.
*
* @author jjumadinova
*
*/

public class ObstacleRace {

	static final float MAX_DISTANCE = 5f; // max distance that is returned for the sensor
	static final int DETECTOR_DELAY = 200; // min delay between notifications from sensor readings

	public static void main(String[] args) {

		// Create a configuration object for the Differential Pilot
		PilotProps p = new PilotProps();
		RegulatedMotor leftMotor = PilotProps.getMotor(p.getProperty(PilotProps.KEY_LEFTMOTOR, "B"));
		RegulatedMotor rightMotor = PilotProps.getMotor(p.getProperty(PilotProps.KEY_RIGHTMOTOR, "C"));

		float TYRE_DIAMETER = 5.6f;
		final float AXLE_TRACK = 12.0f;
		final DifferentialPilot pilot = new DifferentialPilot(
		TYRE_DIAMETER, AXLE_TRACK, leftMotor, rightMotor);

		// *** CHANGE the SPEED here ***//
		pilot.setAcceleration(5);

		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		SampleProvider colorSamplePr = colorSensor.getColorIDMode();
		float[] colorSample = new float[colorSamplePr.sampleSize()];

		// set up a sonic sensor
		EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider sonicSamplePr = sonicSensor.getDistanceMode();
		float[] sonicSample = new float[sonicSamplePr.sampleSize()];
		sonicSamplePr.fetchSample(sonicSample, 0);
		System.out.println("Distance is "+sonicSample[0]);

		System.out.println("Push a button to start the obstacle avoidance race!");
		Button.waitForAnyPress();

		while(true) {
			//*** CHANGE the FORWARD TRAVEL DISTANCE here ***//
			pilot.travel(5);

			// check for objects
			sonicSamplePr.fetchSample(sonicSample, 0);
			if(sonicSample[0] < 0.1) {
				// Perform a movement to avoid the obstacle.
				//*** CHANGE the BACKUP TRAVEL DISTANCE here ***//
				double num = -1;
				pilot.travel(num);
				System.out.println("moving back d is: " + num);
				//*** CHANGE the TURN ANGLE here ***//
				pilot.rotate(Math.random()*90-45);
				//*** CHANGE the TRAVEL forward DISTANCE **//
				pilot.travel(5);
			}

			// check the color of the floor
			colorSamplePr.fetchSample(colorSample, 0);
			// if reached the end (red finish line) - stop
			if(colorSample[0] == Color.RED) {
				break;
			}
		}

		System.out.println("Finished the race");
		Sound.beepSequenceUp();

	}

}
