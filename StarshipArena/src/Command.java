//This class saves all the data needed to later execute a command on a ship.
//This includes saving which meta keys were pressed at the time the command was given.
public class Command {
	boolean isLocationTarget;
	Starship target;
	Point locationTarget;
	boolean shift;
	boolean alt;
	boolean control;
	boolean t;
	public Command(boolean newShift, boolean newAlt, boolean newControl, boolean newT, Starship newTarget, Point newLocation) {
		isLocationTarget = newTarget == null;
		shift = newShift;
		alt = newAlt;
		control = newControl;
		t = newT;
		target = newTarget;
		locationTarget = newLocation;
	}

}