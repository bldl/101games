package inf101.grid;

public class Torus extends Rectangle {

	public Torus(int width, int height) {
		super(width, height);
	}
	
	/**
	 * 
	 * Override with horizontal wrap-around
	 * 
	 * @see inf101.grid.Rectangle#wrapX(int)
	 */
	@Override
	protected int wrapX(int x) {
		if(x < 0)
			return getWidth() + x % getWidth();
		else
			return x % getWidth();
	}
	
	/**
	 * Override with vertical wrap-around
	 * 
	 * @see inf101.grid.Rectangle#wrapY(int)
	 */
	@Override
	protected int wrapY(int y) {
		if(y < 0)
			return getHeight() + y % getHeight();
		else
			return y % getHeight();
	}
}
