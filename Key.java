import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.Polygon;

/**
 * Implements a key on a simulated piano keyboard.
 */
public class Key {
	private Polygon _polygon;
	private int _pitch;
	private boolean _isOn;
	private Piano _piano;
	private String typeKey;

	/**
	 * Returns the polygon associated with this key.
	 * @return the polygon associated with this key.
	 */
	public Polygon getPolygon () {
		return _polygon;
	}

	/**
	 * @param polygon the Polygon that describes the shape and position of this key.
	 * @param pitch the pitch value of the key.
	 * @param piano the Piano associated with this key.
	 */
	public Key (Polygon polygon, int pitch, Piano piano, String type) {
		_polygon = polygon;
		_pitch = pitch;
		_piano = piano;
		typeKey = type;
	}

	/**
	 * Turns the note either on or off.
	 * @param isOn whether the note should be turned on.
	 */
	public void play (boolean isOn) {
		try {
			// Some MIDI technicalities
			ShortMessage myMsg = new ShortMessage();
			final int VELOCITY = 93;
			myMsg.setMessage(isOn ? ShortMessage.NOTE_ON : ShortMessage.NOTE_OFF, 0, _pitch, VELOCITY);
			final int IMMEDIATELY = -1;
			// Send the message to the receiver (either local or remote).
			_piano.getReceiver().send(myMsg, IMMEDIATELY);
			// Set the key to "on".
			_isOn = isOn;
			// Ask the piano to redraw itself (since one of its keys has changed).
			_piano.repaint();
		} catch (InvalidMidiDataException imde) {
			System.out.println("Could not play key!");
		}
	}

	/**
	 * Paints the key using the specified Swing Graphics object.
	 * @param g the Graphics object to be used for painting.
	 */
	public void paint (Graphics g) {		
		if (getIsOn() == true) {
			g.setColor(Color.GRAY);
		}
		else {
			if (getType() == "white") {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.BLACK);
			}
		}
		
		g.fillPolygon(_polygon);
		g.setColor(Color.BLACK);
		g.drawPolygon(_polygon);
	}
	
	public String getType() {
		return typeKey;
	}
	
	public boolean getIsOn() {
		return _isOn;
	}
	
	public void setIsOn(boolean status) {
		_isOn = status;
	}

	/**
	 * Returns a String representation describing the key.
	 * @return a String representation describing the key.
	 */
	public String toString () {
		return "Key: " + _pitch;
	}
}
