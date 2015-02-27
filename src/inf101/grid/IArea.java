package inf101.grid;

/**
 * 
 * Representation of a two-dimensional area.
 * 
 * @author anya
 *
 */
public interface IArea {
	/**
	 * @param pos A position
	 * @return True if the position lies within the area
	 */
	boolean contains(IPosition pos);
	
	/**
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @return True if the (x,y) position lies within the area
	 */
	boolean contains(int x, int y);
	
	/**
	 * Returns the number of legal positions in the area
	 * @return Same as getWidth()*getHeight()
	 */
	int getSize();
	
	/**
	 * @return Width of the area
	 */
	int getWidth();
	
	/**
	 * @return Height of the area
	 */
	int getHeight();
	
	/**
	 * Translate a 2D coordinate to a 1D array index.
	 * 
	 * @param x X-coordinate, 0 <= x < getWidth()
	 * @param y Y-coordinate, 0 <= y < getHeight()
	 * @return an index, 0 <= i < getSize()
	 * @throws IndexOutOfBoundsException if !contains(x, y)
	 */
	int getIndex(int x, int y);
	
	/**
	 * Translate a 2D coordinate to a 1D array index.
	 * @param pos A legal position
	 * @return an index, 0 <= i < getSize()
	 * @throws IndexOutOfBoundsException if !contains(pos)
	 */int getIndex(IPosition pos);
	
	/**
	 * If the area wraps horizontally, then x will be the same as x+(k*getWidth()) for any k – i.e., it will be as if the 2D area is projected on a cylinder or torus in 3D-space.
	 * 
	 * With no wrapping, accessing positions outside (0,0)–(getWidth(),getHeight()) is illegal.
	 * 
	 * @return True if the area wraps around horizontally
	 */
	boolean wrapsHorizontally();
	
	/**
	 * If the area wraps vertically, then y will be the same as y+(k*getHeight()) for any k – i.e., it will be as if the 2D area is projected on a cylinder or torus in 3D-space.
	 * 
	 * With no wrapping, accessing positions outside (0,0)–(getWidth(),getHeight()) is illegal.
	 * 
	 * @return True if the area wraps around vertically
	 */
	boolean wrapsVertically();
}
