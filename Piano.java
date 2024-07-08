import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.sound.midi.*;

/**
 * Implements a simulated piano with 36 keys.
 */
public class Piano extends JPanel {
	public static int START_PITCH = 48;
	
	public static int WHITE_KEY_WIDTH = 40;
	public static int BLACK_KEY_WIDTH = WHITE_KEY_WIDTH/2;
	
	public static int WHITE_KEY_HEIGHT = 200;
	public static int BLACK_KEY_HEIGHT = WHITE_KEY_HEIGHT/2;
	
	public static int NUM_WHITE_KEYS_PER_OCTAVE = 7;
	public static int NUM_OCTAVES = 3;
	
	public static int NUM_BLACK_KEYS_PER_OCTAVE = 5;
	
	public static int NUM_WHITE_KEYS = NUM_WHITE_KEYS_PER_OCTAVE * NUM_OCTAVES;
	public static int NUM_BLACK_KEYS = NUM_BLACK_KEYS_PER_OCTAVE * NUM_OCTAVES;
	
	public static int WIDTH = NUM_WHITE_KEYS * WHITE_KEY_WIDTH;
	public static int HEIGHT = WHITE_KEY_HEIGHT;
		
	private java.util.List<Key> _keys = new ArrayList<>();
	private Receiver _receiver;
	private PianoMouseListener _mouseListener;


	/**
	 * Returns the list of keys in the piano.
	 * @return the list of keys.
	 */
	public java.util.List<Key> getKeys () {
		return _keys;
	}

	/**
	 * Sets the MIDI receiver of the piano to the specified value.
	 * @param receiver the MIDI receiver 
	 */
	public void setReceiver (Receiver receiver) {
		_receiver = receiver;
	}

	/**
	 * Returns the current MIDI receiver of the piano.
	 * @return the current MIDI receiver 
	 */
	public Receiver getReceiver () {
		return _receiver;
	}

	/**
	 * @param receiver the MIDI receiver to use in the piano.
	 */
	public Piano (Receiver receiver) {
		// Some Swing setup stuff
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setReceiver(receiver);
		_mouseListener = new PianoMouseListener(_keys);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		makeKeys();
	}

	/**
	 * Returns the PianoMouseListener associated with the piano.
	 * @return the PianoMouseListener associated with the piano.
	 */
	public PianoMouseListener getMouseListener () {
		return _mouseListener;
	}

	/**
	 * Instantiate all the Key objects with their correct polygons and pitches, and
	 * add them to the _keys array.
	 */ 
	private void makeKeys () {
		int blackKeyX1 = WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2;   // 30
		
		int pitch = START_PITCH;
		
		int whiteKeyX1 = 0;
		
		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0) {
				drawWhiteKeys(whiteKeyX1, 1, pitch);
				drawBlackKeys(blackKeyX1, 2, pitch+1);
				
				blackKeyX1+=3*WHITE_KEY_WIDTH;
				whiteKeyX1 += 3*WHITE_KEY_WIDTH;
				
				pitch+= NUM_BLACK_KEYS_PER_OCTAVE;
			}
			else {
				drawWhiteKeys(whiteKeyX1, 2, pitch);
				drawBlackKeys(blackKeyX1, 3, pitch+1);
				
				blackKeyX1+=4*WHITE_KEY_WIDTH;
				whiteKeyX1 += 4*WHITE_KEY_WIDTH;
				pitch+= NUM_WHITE_KEYS_PER_OCTAVE;
			}
		}
	}
	
	/*
	 * draws a group of 2 or 3 black keys on the screen
	 * @param startPos is the starting position for the first key
	 * 		  loopNum controls how many keys will be drawn
	 *        pitch to control the pitch for each key
	 */
	private void drawWhiteKeys(int startPos, int loopNum, int pitch) {
		drawBeginningWhiteKey(startPos, pitch);
		
		startPos+=WHITE_KEY_WIDTH;
		
		//draws the middle white keys
		//loopNum == 1 to draw 1 middle white key
		//loopNum == 2 to draw 2 middle white keys
		for (int i = 0; i < loopNum; i++) {
			//each white key pitch increases by 2
			pitch+=2;
							
			int[] xCoords2 = new int[] { 
					startPos, 
					startPos + BLACK_KEY_WIDTH/2, 
					startPos + BLACK_KEY_WIDTH/2,
					startPos + WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2, 
					startPos + WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2, 
					startPos + WHITE_KEY_WIDTH,
					startPos + WHITE_KEY_WIDTH, 
					startPos 
					};
			
			int[] yCoords2 = new int[] { 
					WHITE_KEY_HEIGHT/2, 
					WHITE_KEY_HEIGHT/2,
					0, 
					0,
					WHITE_KEY_HEIGHT/2, 
					WHITE_KEY_HEIGHT/2,
					WHITE_KEY_HEIGHT, 
					WHITE_KEY_HEIGHT};
							
			Polygon polygon2 = new Polygon(xCoords2, yCoords2, xCoords2.length);
			Key key2 = new Key(polygon2, pitch, this, "white");

			_keys.add(key2);
							
			startPos+= WHITE_KEY_WIDTH;
						
		}
		pitch+=2;
		drawEndingWhiteKey(startPos, pitch);
	}
	/*
	 *  draws the outer beginning white key
	 *  @param startPos is the starting position for the key
	 *  	   pitch is the pitch for the key
	 */
	private void drawBeginningWhiteKey(int startPos, int pitch) {
		int[] xCoords1 = new int[] {  
				startPos,  
				startPos + WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2,  
				startPos + WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2,
				startPos + WHITE_KEY_WIDTH, 
				startPos + WHITE_KEY_WIDTH, 
				startPos};
		int[] yCoords1 = new int[] {  
				0,  
				0,  
				WHITE_KEY_HEIGHT/2, 
				WHITE_KEY_HEIGHT/2, 
				WHITE_KEY_HEIGHT, 
				WHITE_KEY_HEIGHT};
				
		Polygon polygon1 = new Polygon(xCoords1, yCoords1, xCoords1.length);
		Key key1 = new Key(polygon1, pitch, this, "white");
		_keys.add(key1);
	}
	
	/*
	 *  draws the outer ending white key
	 *  @param startPos is the starting position for the key
	 *  	   pitch is the pitch for the key
	 */
	private void drawEndingWhiteKey(int startPos, int pitch) {
		int[] xCoords3 = new int[] {  
				startPos + BLACK_KEY_WIDTH/2,  
				startPos + WHITE_KEY_WIDTH,  
				startPos + WHITE_KEY_WIDTH,
				startPos,
				startPos,
				startPos + BLACK_KEY_WIDTH/2};
		int[] yCoords3 = new int[] {  
				0,
				0,  
				WHITE_KEY_HEIGHT, 
				WHITE_KEY_HEIGHT,
				WHITE_KEY_HEIGHT/2,
				WHITE_KEY_HEIGHT/2};
				
		Polygon polygon3 = new Polygon(xCoords3, yCoords3, xCoords3.length);
		Key key3 = new Key(polygon3, pitch, this, "white");
				
		_keys.add(key3); 
	}
	
	/*
	 * draws a group of 2 or 3 black keys on the screen
	 * @param x1 is the starting position for the first key
	 * 		  loopNum controls how many keys will be drawn
	 *        pitch to control the pitch for each key
	 */
	private void drawBlackKeys(int startPos, int loopNum, int pitch) {
		//loopNum == 2 to draw 2 black keys
		//loopNum == 3 to draw 3 black keys
		for (int i = 0; i < loopNum; i++) {
			int[] xCoords = new int[] {  
					startPos,  
					startPos + BLACK_KEY_WIDTH,  
					startPos + BLACK_KEY_WIDTH,  
					startPos};
			int[] yCoords = new int[] {  
					0,  
					0,  
					BLACK_KEY_HEIGHT,  
					BLACK_KEY_HEIGHT};
			
			Polygon polygon = new Polygon(xCoords, yCoords, xCoords.length);
			Key key = new Key(polygon, pitch, this, "black");

			_keys.add(key);
		
			startPos += WHITE_KEY_WIDTH;
			
			// each black key pitch increases by 2
			pitch += 2;
		}
	}

	@Override
	/**
	 * Paints the piano and all its constituent keys.
	 * @param g the Graphics object to use for painting.
	 */
	public void paint (Graphics g) {
		// Delegates to all the individual keys to draw themselves.
		for (Key key: _keys) {
			key.paint(g);
		}
	}
}
