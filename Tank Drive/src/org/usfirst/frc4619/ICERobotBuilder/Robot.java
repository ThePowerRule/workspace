package org.usfirst.frc4619.ICERobotBuilder;



import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    double leftStick;  // set to ID 1 in DriverStation
    double rightStick; // set to ID 2 in DriverStation
	Joystick logitech;
    public Robot() {
        myRobot = new RobotDrive(0, 1);
        myRobot.setExpiration(0.1);
        leftStick = logitech.getRawAxis(1);
        rightStick = logitech.getRawAxis(5);
    }

    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	double rawL = logitech.getRawAxis(1);
        	double rawR = logitech.getRawAxis(5);
        	SmartDashboard.putString("Left value", String.valueOf(rawL));
        	SmartDashboard.putString("Right value", String.valueOf(rawR));
            leftStick = -rawL;
            rightStick = -rawR;
        	SmartDashboard.putString("Left corrected value", String.valueOf(leftStick));
        	SmartDashboard.putString("Right corrected value", String.valueOf(rightStick));
        	myRobot.tankDrive(leftStick, rightStick);
            Timer.delay(0.005);		// wait for a motor update time
           
        }
    }

}
