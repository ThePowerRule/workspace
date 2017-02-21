package org.usfirst.frc6419.RobotFull.commands;

import org.usfirst.frc6419.RobotFull.commands.autonomousCommands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommandGroup extends CommandGroup {
	public AutonomousCommandGroup(){
		addSequential(new TimedDriveStraight(2, 0));
		addSequential(new TimedDriveStraight(.5,.2));
		addSequential(new GyroDriveTurn(-90, .4));
	}
}
