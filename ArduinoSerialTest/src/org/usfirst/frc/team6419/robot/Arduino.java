package org.usfirst.frc.team6419.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
/**
 * A serial connection to an Arduino driving a color sensor
 * <p>The connection is initialized as soon as "new Arduino()"
 * is called.
 * <p>This class also contains an inner ColorSensor class
 * that allows other processes to get the raw red, green, and
 * blue values of the sensor, along with the color in 
 * hexadecimal form.
 * 
 * @author Team 6419
 *
 */
public class Arduino {
	SerialPort arduino;
	private ColorSensor colorSensor;
	private String buffer = "";
	private boolean isLive = true;
	private static final byte[] GET_COLOR_SENSOR_RED = {1},
			GET_COLOR_SENSOR_GREEN = {2},
			GET_COLOR_SENSOR_BLUE = {3},
			GET_COLOR_SENSOR_HEXADECIMAL = {4};
	private static final byte[] GET_TIME_OF_FLIGHT_DISTANCE = {5};
	public static final int ERROR_PARSING = -1;
	public static final int ERROR_NO_RESPONSE = -2;
	/**
	 * Constructs and initializes a connection to the Arduino
	 * on port "kUSB"
	 */
	public Arduino(){
		colorSensor = new ColorSensor();
		try{
			arduino = new SerialPort(9600, SerialPort.Port.kUSB);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	private void retrieveData(byte[] command){
		try{
			arduino.write(command, command.length);
			Timer.delay(.1);
			buffer = arduino.readString();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public ColorSensor colorSensor(){
		return colorSensor;
	}
	public class ColorSensor{
		private ColorSensor(){}
		public int getRed(){
			retrieveData(GET_COLOR_SENSOR_RED);
			if(buffer.length()>0){
				try{return Integer.parseInt(buffer);}
				catch(Exception e){
					System.out.println("Error parsing response from Arduino");
					return ERROR_PARSING;
				}
			}
			else{
				System.out.println("No response from Arduino");
				return ERROR_NO_RESPONSE;
			}
		}
		public int getGreen(){
			retrieveData(GET_COLOR_SENSOR_GREEN);
			if(buffer.length()>0){
				try{return Integer.parseInt(buffer);}
				catch(Exception e){
					System.out.println("Error parsing response from Arduino");
					return ERROR_PARSING;
				}
			}
			else{
				System.out.println("No response from Arduino");
				return ERROR_NO_RESPONSE;
			}
		}
		public int getBlue(){
			retrieveData(GET_COLOR_SENSOR_BLUE);
			if(buffer.length()>0){
				try{return Integer.parseInt(buffer);}
				catch(Exception e){
					System.out.println("Error parsing response from Arduino");
					return ERROR_PARSING;
				}
			}
			else{
				System.out.println("No response from Arduino");
				return ERROR_NO_RESPONSE;
			}
		}
		public String getColorString(){
			retrieveData(GET_COLOR_SENSOR_HEXADECIMAL);
			return buffer;
		}
	}
}
