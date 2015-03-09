package inf101.games.listeners;

import inf101.grid.IPosition;

public interface IDragListener {

	/**
	 * Called when the drags-and-drops from one cell to another
	 * 
	 * @param from The position from which the user dragged
	 * @param to The position where the user dropped
	 */
	void dragged(IPosition from, IPosition to);
}
