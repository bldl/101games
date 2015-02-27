package inf101.util.generators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import inf101.grid.IGrid2D;
import inf101.grid.MyGrid2D;
import inf101.util.IGenerator;

public class Grid2DGenerator<T> implements IGenerator<IGrid2D<T>> {
	/**
	 * Generator for the width of a random grid
	 */
	private final IGenerator<Integer> widthGenerator;
	/**
	 * Generator for the height of a random grid
	 */
	private final IGenerator<Integer> heightGenerator;
	/**
	 * Generator for one element of a random grid
	 */
	private final IGenerator<T> elementGenerator;

	public Grid2DGenerator(IGenerator<T> elementGenerator) {
		this.elementGenerator = elementGenerator;
		this.widthGenerator = new IntGenerator(1,100);
		this.heightGenerator = new IntGenerator(1, 100);
	}	

	public Grid2DGenerator(IGenerator<T> elementGenerator, int maxWidth, int maxHeight) {
		if(maxWidth < 1 || maxHeight < 1)
			throw new IllegalArgumentException("Width and height must be 1 or greater");
		
		this.elementGenerator = elementGenerator;
		this.widthGenerator = new IntGenerator(1, maxWidth);
		this.heightGenerator = new IntGenerator(1, maxHeight);
	}	

	public Grid2DGenerator(IGenerator<T> elementGenerator, IGenerator<Integer> widthGenerator, IGenerator<Integer> heightGenerator) {
		this.elementGenerator = elementGenerator;
		this.widthGenerator = widthGenerator;
		this.heightGenerator = heightGenerator;
	}	

	@Override
	public IGrid2D<T> generate(Random r) {
		int w = widthGenerator.generate(r);
		int h = heightGenerator.generate(r);
		
		IGrid2D<T> grid = new MyGrid2D<>(w, h);
		
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				grid.set(x, y, elementGenerator.generate(r));
			}
		}
		
		return grid;
	}

	@Override
	public List<IGrid2D<T>> generateEquals(Random r, int n) {
		long seed = r.nextLong();

		List<IGrid2D<T>> list = new ArrayList<>();
		
		for(int i = 0; i < n; i++)
			list.add(generate(new Random(seed)));
		
		return list;
	}

	@Override
	public List<IGrid2D<T>> generateEquals(int n) {
		return generateEquals(IntGenerator.getRandom(), n); 
	}

	@Override
	public IGrid2D<T> generate() {
		return generate(IntGenerator.getRandom());
	}

}
