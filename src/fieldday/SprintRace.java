package fieldday;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.PilotProps;

/**
* This program runs on a differential robot that will move straight at a
* specified speed until it hits something (wall), detected by its touch sensor.
*
* @author jjumadinova
*
*/

public class SprintRace {

	public static void main(String[] args) {
		// Create a configuration object for the Differential Pilot
		PilotProps p= new PilotProps();
		RegulatedMotor leftMotor = PilotProps.getMotor(p.getProperty(PilotProps.KEY_LEFTMOTOR, "B"));
		RegulatedMotor rightMotor = PilotProps.getMotor(p.getProperty(PilotProps.KEY_RIGHTMOTOR, "C"));

    	float TYRE_DIAMETER = 5.6f;
    	final float AXLE_TRACK = 12.0f;
		DifferentialPilot pilot = new DifferentialPilot(
	        TYRE_DIAMETER, AXLE_TRACK, leftMotor, rightMotor);

		// set up a sensor
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
		SampleProvider touchSamplePr = touchSensor.getTouchMode();
		float[] touchSample = new float[touchSamplePr.sampleSize()];

		//*** CHANGE the SPEED here ***//
		pilot.setAcceleration(5);

		System.out.println("Push a button to start the sprint race!");
		Button.waitForAnyPress();

		while(true) {
			//*** CHANGE the TRAVEL forward DISTANCE here **//
			pilot.travel(10); //cm
			// stop and take a sample of the touch sensor
			touchSamplePr.fetchSample(touchSample, 0);
			System.out.println("Touch: " + touchSample[0]);
			// if touch sensor is being pushed, stop
			if(touchSample[0] == 1) {
				break;
			}
		}
		System.out.println("Finished the race");
		touchSensor.close();
		Sound.beepSequenceUp();

	}

}
