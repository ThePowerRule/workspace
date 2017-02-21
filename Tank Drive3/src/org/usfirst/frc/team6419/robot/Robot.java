package org.usfirst.frc.team6419.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    Joystick logitech;
    Talon shooterThing;
    double leftStick;
    double rightStick;
    double armPower;
    public Robot() {
        myRobot = new RobotDrive(2,1,4,3);
        myRobot.setExpiration(0.1);
        shooterThing = new Talon(7);
        logitech = new Joystick(0);
        leftStick = logitech.getRawAxis(1);
        rightStick = logitech.getRawAxis(5);
        //armPower = logitech.get

    }

    
    /**
     * Runs the motors with tank steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	double rawL = logitech.getRawAxis(1);
        	double rawR = logitech.getRawAxis(5);
        	SmartDashboard.putString("Left value", String.valueOf(rawL));
        	SmartDashboard.putString("Right value", String.valueOf(rawR));
            leftStick = rawL>0?-Math.pow(rawL, 2)*36:Math.pow(rawL, 2)*36;
            rightStick = rawR>0?-Math.pow(rawR, 2)*36:Math.pow(rawR, 2)*36;
        	SmartDashboard.putString("Left corrected value", String.valueOf(leftStick));
        	SmartDashboard.putString("Right corrected value", String.valueOf(rightStick));
        	myRobot.tankDrive(leftStick, rightStick);
        	if(logitech.getRawButton(3)) shooterThing.set(1);
        	else if(logitech.getRawButton(1)) shooterThing.set(-.2);
        	else shooterThing.set(0);
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

}
