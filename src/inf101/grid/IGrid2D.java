package inf101.grid;

/**
 * Interface for a 2-dimensional grid data structure.
 *
 * @param <T> The element type
 */

public interface IGrid2D<T> extends IArea {
	/**
	 * Get the element at position (x, y)
	 * 
	 * @param x  0 => x < getWidth()
	 * @param y  0 => y < getHeight()
	 * @return Element at x, y
	 */
	T get(int x, int y);
	
	/**
	 * Set the element at position (x, y)
	 * 
	 * @param x 0 => x < getWidth()
	 * @param y 0 => y < getHeight()
	 * @param val New value
	 * @return Old value
	 */
	T set(int x, int y, T val);
	
	/**
	 * @return The height of the grid
	 */
	int getHeight();
	
	/**
	 * @return The width of the grid
	 */
	int getWidth();
	
	/**
	 * Set all cells in the grid to the given default value (may be null).
	 * 
	 * @param val The default value
	 */
	void clear(T val);
	
	
	/**
	 * Make a copy
	 * 
	 * @return A fresh copy of the grid, with the same elements
	 */
	IGrid2D<T> copy();
	
}
