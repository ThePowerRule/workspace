package org.usfirst.frc.team6419.robot.commands;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6419.robot.Robot;

/**
 *
 */
public class ExampleCommand extends Command {
	private static final byte TCS34725_ADDRESS          = 0x29;
	private static final byte TCS34725_COMMAND_BIT      = (byte) 0x80;
	private static final byte TCS34725_ENABLE           = 0x00;
	private static final byte TCS34725_ENABLE_AIEN      = 0x10;    /* RGBC Interrupt Enable */
	private static final byte TCS34725_ENABLE_WEN       = 0x08;    /* Wait enable - Writing 1 activates the wait timer */
	private static final byte TCS34725_ENABLE_AEN       = 0x02;    /* RGBC Enable - Writing 1 actives the ADC, 0 disables it */
	private static final byte TCS34725_ENABLE_PON       = 0x01;    /* Power on - Writing 1 activates the internal oscillator, 0 disables it */
	private static final byte TCS34725_ATIME            = 0x01;    /* Integration time */
	private static final byte TCS34725_WTIME            = 0x03;    /* Wait time (if TCS34725_ENABLE_WEN is asserted; */
	private static final byte TCS34725_WTIME_2_4MS      = (byte) 0xFF;    /* WLONG0 = 2.4ms   WLONG1 = 0.029s */
	private static final byte TCS34725_WTIME_204MS      = (byte) 0xAB;    /* WLONG0 = 204ms   WLONG1 = 2.45s  */
	private static final byte TCS34725_WTIME_614MS      = 0x00;    /* WLONG0 = 614ms   WLONG1 = 7.4s   */
	private static final byte TCS34725_AILTL            = 0x04;    /* Clear channel lower interrupt threshold */
	private static final byte TCS34725_AILTH            = 0x05;
	private static final byte TCS34725_AIHTL            = 0x06;    /* Clear channel upper interrupt threshold */
	private static final byte TCS34725_AIHTH            = 0x07;
	private static final byte TCS34725_PERS             = 0x0C;    /* Persistence register - basic SW filtering mechanism for interrupts */
	private static final byte TCS34725_PERS_NONE        = 0b0000;  /* Every RGBC cycle generates an interrupt                                */
	private static final byte TCS34725_PERS_1_CYCLE     = 0b0001;  /* 1 clean channel value outside threshold range generates an interrupt   */
	private static final byte TCS34725_PERS_2_CYCLE     = 0b0010;  /* 2 clean channel values outside threshold range generates an interrupt  */
	private static final byte TCS34725_PERS_3_CYCLE     = 0b0011;  /* 3 clean channel values outside threshold range generates an interrupt  */
	private static final byte TCS34725_PERS_5_CYCLE     = 0b0100;  /* 5 clean channel values outside threshold range generates an interrupt  */
	private static final byte TCS34725_PERS_10_CYCLE    = 0b0101;  /* 10 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_15_CYCLE    = 0b0110;  /* 15 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_20_CYCLE    = 0b0111;  /* 20 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_25_CYCLE    = 0b1000;  /* 25 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_30_CYCLE    = 0b1001;  /* 30 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_35_CYCLE    = 0b1010;  /* 35 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_40_CYCLE    = 0b1011;  /* 40 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_45_CYCLE    = 0b1100;  /* 45 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_50_CYCLE    = 0b1101;  /* 50 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_55_CYCLE    = 0b1110;  /* 55 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_PERS_60_CYCLE    = 0b1111;  /* 60 clean channel values outside threshold range generates an interrupt */
	private static final byte TCS34725_CONFIG           = 0x0D;
	private static final byte TCS34725_CONFIG_WLONG     = 0x02;    /* Choose between short and long (12x; wait times via TCS34725_WTIME */
	private static final byte TCS34725_CONTROL          = 0x0F;    /* Set the gain level for the sensor */
	private static final byte TCS34725_ID               = 0x12;    /* 0x44 = TCS34721/TCS34725, 0x4D = TCS34723/TCS34727 */
	private static final byte TCS34725_STATUS           = 0x13;
	private static final byte TCS34725_STATUS_AINT      = 0x10;    /* RGBC Clean channel interrupt */
	private static final byte TCS34725_STATUS_AVALID    = 0x01;    /* Indicates that the RGBC channels have completed an integration cycle */
	private static final byte TCS34725_CDATAL           = 0x14;    /* Clear channel data */
	private static final byte TCS34725_CDATAH           = 0x15;
	private static final byte TCS34725_RDATAL           = 0x16;    /* Red channel data */
	private static final byte TCS34725_RDATAH           = 0x17;
	private static final byte TCS34725_GDATAL           = 0x18;    /* Green channel data */
	private static final byte TCS34725_GDATAH           = 0x19;
	private static final byte TCS34725_BDATAL           = 0x1A;    /* Blue channel data */
	private static final byte TCS34725_BDATAH           = 0x1B;
	private static final byte TCS34725_INTEGRATIONTIME_2_4MS  = (byte) 0xFF;   /**<  2.4ms - 1 cycle    - Max Count: 1024  */
	private static final byte TCS34725_INTEGRATIONTIME_24MS   = (byte) 0xF6;   /**<  24ms  - 10 cycles  - Max Count: 10240 */
	private static final byte TCS34725_INTEGRATIONTIME_50MS   = (byte) 0xEB;   /**<  50ms  - 20 cycles  - Max Count: 20480 */
	private static final byte TCS34725_INTEGRATIONTIME_101MS  = (byte) 0xD5;   /**<  101ms - 42 cycles  - Max Count: 43008 */
	private static final byte TCS34725_INTEGRATIONTIME_154MS  = (byte) 0xC0;   /**<  154ms - 64 cycles  - Max Count: 65535 */
	private static final byte TCS34725_INTEGRATIONTIME_700MS  = 0x00;    /**<  700ms - 256 cycles - Max Count: 65535 */
	private static final byte TCS34725_GAIN_1X                = 0x00;   /**<  No gain  */
	private static final byte TCS34725_GAIN_4X                = 0x01;   /**<  4x gain  */
	private static final byte TCS34725_GAIN_16X               = 0x02;   /**<  16x gain */
	private static final byte TCS34725_GAIN_60X               = 0x03;   /**<  60x gain */
	I2C sensor = new I2C(Port.kOnboard, 0x29);
	public int red = 0, blue = 0, green = 0, color = 0;
	private int count = 0;
	void  begin(){
		SmartDashboard.putBoolean("Address", sensor.addressOnly());
		setIntegrationTime(TCS34725_INTEGRATIONTIME_2_4MS);
		setGain(TCS34725_GAIN_1X);
		enable();
	}
	void setIntegrationTime(byte it){
		writeByte(TCS34725_ATIME, it);
	}
	void setGain(byte gain){
		writeByte(TCS34725_CONTROL, gain);
	}
	void getRawData(){
		color = readInt(TCS34725_CDATAL);
		red = readInt(TCS34725_RDATAL);
		green = readInt(TCS34725_GDATAL);
		blue = readInt(TCS34725_BDATAL);

	}
	int calculateColorTemperature(int r, int g, int b){
		float X, Y, Z;      /* RGB to XYZ correlation      */
		float xc, yc;       /* Chromaticity co-ordinates   */
		float n;            /* McCamy's formula            */
		float cct;

		/* 1. Map RGB values to their XYZ counterparts.    */
		/* Based on 6500K fluorescent, 3000K fluorescent   */
		/* and 60W incandescent values for a wide range.   */
		/* Note: Y = Illuminance or lux                    */
		X = (-0.14282F * r) + (1.54924F * g) + (-0.95641F * b);
		Y = (-0.32466F * r) + (1.57837F * g) + (-0.73191F * b);
		Z = (-0.68202F * r) + (0.77073F * g) + ( 0.56332F * b);

		/* 2. Calculate the chromaticity co-ordinates      */
		xc = (X) / (X + Y + Z);
		yc = (Y) / (X + Y + Z);

		/* 3. Use McCamy's formula to determine the CCT    */
		n = (xc - 0.3320F) / (0.1858F - yc);

		/* Calculate the final CCT */
		cct = (float) ((449.0 * Math.pow(n, 3)) + (3525.0 * Math.pow(n, 2)) + (6823.3 * n) + 5520.33);

		/* Return the results in degrees Kelvin */
		return (int)cct;
	}
	int calculateLux(int r, int g, int b){
		float illuminance;

		/* This only uses RGB ... how can we integrate clear or calculate lux */
		/* based exclusively on clear since this might be more reliable?      */
		illuminance = (-0.32466F * r) + (1.57837F * g) + (-0.73191F * b);

		return (int)illuminance;
	}
	void writeByte (byte register, int value){
		sensor.write(TCS34725_ENABLE, TCS34725_COMMAND_BIT|register);
		sensor.write(register, (byte)(value&0xFF));
	}
	byte readByte (byte register){
		sensor.write(TCS34725_ENABLE, TCS34725_COMMAND_BIT|register);
		byte[] buffer = {1};
		//byte[] send = {(byte) (TCS34725_COMMAND_BIT|register)};
		//sensor.transaction(send, 1, buffer, 1);
		SmartDashboard.putBoolean("IsValid", sensor.read(register, 1, buffer));
		byte b = buffer[0];
		SmartDashboard.putNumber("Value", b);
		return b;
	}
	int readInt (byte register){
		sensor.write(TCS34725_ENABLE, TCS34725_COMMAND_BIT|register);
		byte[] buffer = {1, 2};
		//byte[] send = {(byte) (TCS34725_COMMAND_BIT|register)};
		//sensor.transaction(send, 1, buffer, 1);
		SmartDashboard.putBoolean("IsValid", sensor.read(register, 2, buffer));
		byte a = buffer[0], b = buffer[1];
		SmartDashboard.putNumber("Value", b);
		int x = a;
		x *= 256;
		x += b;
		return x;
	}
	void enable(){
		writeByte(TCS34725_ENABLE, TCS34725_ENABLE_PON);
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		writeByte(TCS34725_ENABLE, TCS34725_ENABLE_PON|TCS34725_ENABLE_AEN);
	}
	void disable(){
		  /* Turn the device off to save power */
		  int reg = 0;
		  reg = readByte(TCS34725_ENABLE);
		  writeByte(TCS34725_ENABLE, reg & ~(TCS34725_ENABLE_PON | TCS34725_ENABLE_AEN));
	}



	public ExampleCommand() {
		// Use requires() here to declare subsystem dependencies
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		begin();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		getRawData();
		SmartDashboard.putNumber("Color", color);
		SmartDashboard.putNumber("Red", red);
		SmartDashboard.putNumber("Green", blue);
		SmartDashboard.putNumber("Blue", green);
		count++;
		SmartDashboard.putNumber("Count", count);
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		disable();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		disable();
	}
}
