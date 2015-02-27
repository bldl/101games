package inf101.grid;

public class Rectangle implements IArea {
	private final int width;
	private final int height;
	
	public Rectangle(int width, int height) {
		this.width = width;
		this.height = height;
	}
	@Override
	public boolean contains(IPosition pos) {
		int x = wrapX(pos.getX());
		int y = wrapY(pos.getY());
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	protected int wrapY(int y) {
		return y;
	}

	protected int wrapX(int x) {
		return x;
	}

	@Override
	public boolean contains(int x, int y) {
		x = wrapX(x);
		y = wrapY(y);
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	@Override
	public int getSize() {
		return width * height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getIndex(int x, int y) {
		x = checkX(x);
		y = checkY(y);
		return y*width + x;
	}

	/**
	 * @param y Y-coordinate
	 * @return The same y, wrapped to wrapY(y)
	 * @throws IndexOutOfBoundsException if coordinate is out of range, and wrapping is not enabled
	 */
	protected int checkY(int y) {
		y = wrapY(y);
		if(y < 0 || y >= height)
			throw new IndexOutOfBoundsException(""+y);
		return y;
	}

	/**
	 * @param x X-coordinate
	 * @return The same x, wrapped to wrapX(x)
	 * @throws IndexOutOfBoundsException if coordinate is out of range, and wrapping is not enabled
	 */
	protected int checkX(int x) {
		x = wrapX(x);
		if(x < 0 || x >= width)
			throw new IndexOutOfBoundsException(""+x);

		return x;
	}

	@Override
	public int getIndex(IPosition pos) {
		int x = checkX(pos.getX());
		int y = checkY(pos.getY());
		return y*width + x;
	}

	@Override
	public boolean wrapsHorizontally() {
		return false;
	}

	@Override
	public boolean wrapsVertically() {
		return false;
	}

}
