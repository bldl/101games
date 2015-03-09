package inf101.games.listeners;

import inf101.grid.IPosition;

public interface IClickListener {

	/**
	 * Called when the user clicks (mouse) / taps (touch screen) a grid cell.
	 * 
	 * @param pos The position of the cell the user clicked.
	 */
	void clicked(IPosition pos);

	/**
	 * Called when the user double-clicks (mouse) / double-taps (touch screen) a grid cell.
	 * 
	 * @param pos The position of the cell the user clicked.
	 */
	void doubleClicked(IPosition pos);

	/**
	 * Called when the user right clicks (mouse) / taps and holds (touch screen) a grid cell.
	 * 
	 * @param pos The position of the cell the user clicked.
	 */
	void rightClicked(IPosition pos);

}
