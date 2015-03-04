package inf101.grid;

/**
 * En hendig liten klasse for å lagre koordinater.
 * 
 * @author Anya Helene Bagge
 *
 */
public class Position implements IPosition {
	public final int x;
	public final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
}