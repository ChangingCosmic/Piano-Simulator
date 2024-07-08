import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.awt.event.*;
import org.junit.jupiter.api.*;

/**
 * Contains a set of unit tests for the Piano class.
 */
class PianoTester {
	private TestReceiver _receiver;
	private Piano _piano;
	private PianoMouseListener _mouseListener;

	private MouseEvent makeMouseEvent (int x, int y) {
		return new MouseEvent(_piano, 0, 0, 0, x, y, 0, false);
	}

	@BeforeEach
	void setup () {
		// A new TestReceiver will be created before running *each*
		// test. Hence, the "turn on" and "turn off" counts will be
		// reset to 0 before *each* test.
		_receiver = new TestReceiver();
		_piano = new Piano(_receiver);
		_mouseListener = _piano.getMouseListener();
	}

	@Test
	void testClickUpperLeftMostPixel () {
		// Pressing the mouse should cause the key to turn on.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
	}

	@Test
	void testDragWithinKey () {
		// Test that pressing and dragging the mouse *within* the same key
		// should cause the key to be turned on only once, not multiple times.
		// Use makeMouseEvent and TestReceiver.getKeyOnCount.
		// TODO complete me
		
		_mouseListener.mousePressed(makeMouseEvent(10, 20));
		_mouseListener.mouseDragged(makeMouseEvent(10, 60));
		
		assertTrue(1 == _receiver.getKeyOnCount(Piano.START_PITCH));
		assertTrue(0 == _receiver.getKeyOffCount(Piano.START_PITCH));
		
	}

	@Test
	/*
	 * tests that every key has the correct pitch
	 * also tests that if a mouse is pressed on the boundary 
	 * (like at (30, 0)) between 2 keys, 
	 *  the key to the right is played
	 */
	void testForCorrectPitchAcrossAllKeys () {

		int startPos = 10;
		for (int i = Piano.START_PITCH; i < (Piano.NUM_WHITE_KEYS + Piano.NUM_BLACK_KEYS); i++) {
			_mouseListener.mousePressed(makeMouseEvent(startPos, 0));

			assertTrue(_receiver.isKeyOn(i));
			
			//20 to land on the boundary for all keys
			startPos+=20;
		}
	}
	
	@Test
	/*
	 * tests a drag over multiple keys
	 */
	void testDragOverMultipleKeys () {
		
		_mouseListener.mousePressed(makeMouseEvent (0, 10));
		_mouseListener.mouseDragged(makeMouseEvent (40, 10));
		_mouseListener.mouseDragged(makeMouseEvent (60, 10));
		
		assertTrue(1 == _receiver.getKeyOnCount(Piano.START_PITCH));
		assertTrue(1 == _receiver.getKeyOffCount(Piano.START_PITCH));
		
		assertTrue(1 == _receiver.getKeyOnCount(Piano.START_PITCH + 1));
		assertTrue(1 == _receiver.getKeyOffCount(Piano.START_PITCH + 1));
		
		assertTrue(1 == _receiver.getKeyOnCount(Piano.START_PITCH + 2));
	}
	
	@Test
	/*
	 * tests the key boundary
	 * the key that should be played is the one on the right
	 */
	void testFirstTwoKeyBoundary() {
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH - Piano.BLACK_KEY_WIDTH/2, 0));
		assertTrue(1 == _receiver.getKeyOnCount(Piano.START_PITCH+1));
	}
	
	@Test
	/*
	 * tests the right boundary for the piano to see if it plays
	 */
	void testUpperRightKey () {
		_mouseListener.mousePressed(makeMouseEvent(839, 0));
		assertTrue(_receiver.isKeyOn(83));
	}
}
