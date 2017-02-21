package org.usfirst.frc6419.RobotFull.commands.autonomousCommands;

import org.usfirst.frc6419.RobotFull.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TimedDriveStraight extends Command {
	private long killTime;
	private long initTime;
	private double speed;
	public TimedDriveStraight(double duration, double power){
		super();
		requires(Robot.chassis);
		speed = power;
		killTime = (long)(duration*1000);
	}
	@Override
	protected boolean isFinished() {
		return System.currentTimeMillis()-initTime>=killTime;
	}
	
	@Override 
	protected void initialize() {
		initTime = System.currentTimeMillis();
    }
	
	@Override 
	protected void execute() {
		Robot.chassis.driveForward(speed);
		SmartDashboard.putNumber("Time: ", System.currentTimeMillis()-initTime);
		SmartDashboard.putNumber("Kill time: ", killTime);
	}
	
	@Override 
	protected void end(){
		Robot.chassis.driveForward(0);
	}

}
