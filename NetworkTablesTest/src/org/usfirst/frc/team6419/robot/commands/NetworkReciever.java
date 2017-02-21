package org.usfirst.frc.team6419.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.usfirst.frc.team6419.robot.Robot;

/**
 *
 */
public class NetworkReciever extends Command {
	private Socket socket;
	private boolean isLive = false;
	public static final String HOST_ADDRESS = "127.0.0.10";
	public static final int PORT_NUMBER = 2048;
	public static final String ENDSTRING = "nl";
	public static final int ERROR = 404, END_CODE = 10;
	public BufferedReader in;
	public PrintWriter out; 
	private double number = 0;
	public NetworkReciever() {
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		try {
			socket = new Socket(HOST_ADDRESS, PORT_NUMBER);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (Exception e) {
			e.printStackTrace();
			isLive = false;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		update();
		SmartDashboard.putNumber("Value", number);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return !isLive;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}

	protected void update(){
		try {
			if(in.ready()){
				String s = in.readLine();
				if(s.equals(ENDSTRING)){
					number = END_CODE;
				}
				number = Double.valueOf(s);
			}
		} catch (IOException e) {
			number = ERROR;
			isLive = false;
		}

	}

	protected double getNumber(){
		return number;
	}
}
