package org.usfirst.frc6419.RobotFull.commands.autonomousCommands;

import org.usfirst.frc6419.RobotFull.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LauncherSwitch extends Command {
	private boolean isMech;
	private int keyCode;
	public static final int HEXAPUS_ON = 1,
			HEXAPUS_OFF = 2,
			LAUNCHER_ON = 3,
			LAUNCHER_OFF = 4;
	public LauncherSwitch(int code){
		super();
		requires(Robot.ballLauncher);
		keyCode = code;
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override 
	protected void initialize() {
    }
	
	@Override 
	protected void execute() {
		switch(keyCode){
		case HEXAPUS_ON:
			Robot.ballLauncher.startHexapus();
			break;
		case HEXAPUS_OFF:
			Robot.ballLauncher.stopHexapus();
			break;
		case LAUNCHER_ON:
			Robot.ballLauncher.startLauncher();
			break;
		case LAUNCHER_OFF:
			Robot.ballLauncher.stopLauncher();
			break;
		}
	}
	
	@Override 
	protected void end(){
	}
}
