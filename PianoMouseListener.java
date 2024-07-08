import javax.swing.*;
import java.awt.event.*;
import javax.sound.midi.*;
import java.util.*;

/**
 * Handles mouse press, release, and drag events on the Piano.
 */
public class PianoMouseListener extends MouseAdapter {
    private List<Key> _keys;
    
	/**
	 * @param keys the list of keys in the piano.
	 */
	public PianoMouseListener (List<Key> keys) {
		_keys = keys;
	}

	@Override 
	/**
	 * This method is called by Swing whenever the user drags the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseDragged (MouseEvent e) {
		for (Key key : _keys) {
			if (key.getPolygon().contains(e.getX(), e.getY())) {
				//checks if the key is being played already or not
		    	if (false == key.getIsOn()) {
		    		key.play(true);
		            key.setIsOn(true);
					System.out.println(key.toString());

		        }
		    }
		    else {
		        if (key.getIsOn()) { //if getIsOn() is true
		            key.play(false);
		            key.setIsOn(false);
		        }
		    }
		}
	}

	@Override
	/**
	 * This method is called by Swing whenever the user presses the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mousePressed (MouseEvent e) {
		for (Key key: _keys) {
			if (key.getPolygon().contains(e.getX(), e.getY())) {
				key.play(true);
				// System.out.println(key.toString());
			}
		}
	}
		
	@Override
	/**
	 * This method is called by Swing whenever the user releases the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseReleased (MouseEvent e) {
		for (Key key: _keys) {
			if (key.getPolygon().contains(e.getX(), e.getY())) {
				key.play(false);
			}
		}
	}
}
