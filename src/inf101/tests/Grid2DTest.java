package inf101.tests;

import static org.junit.Assert.*;
import inf101.grid.IGrid2D;
import inf101.grid.MyGrid2D;
import inf101.util.IGenerator;
import inf101.util.generators.Grid2DGenerator;
import inf101.util.generators.IntGenerator;
import inf101.util.generators.StringGenerator;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Grid2DTest {
	private static final int N = 100000;

	private IGenerator<String> strGen = new StringGenerator();
	private IGenerator<IGrid2D<String>> gridGen = new Grid2DGenerator<String>(
			strGen);
	private IGenerator<Integer> intGen = new IntGenerator(1, 100);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test that get gives back the same value after set.
	 */
	@Test
	public void setGetTest() {
		IGrid2D<String> grid = gridGen.generate();

		IGenerator<Integer> wGen = new IntGenerator(0, grid.getWidth());
		IGenerator<Integer> hGen = new IntGenerator(0, grid.getHeight());

		for (int i = 0; i < N; i++) {
			int x = wGen.generate();
			int y = hGen.generate();
			String s = strGen.generate();

			setGetProperty(grid, x, y, s);
		}
	}

	@Test
	public void dimensionTest() {
		for (int i = 0; i < N/100; i++) {
			int w = intGen.generate();
			int h = intGen.generate();
			IGrid2D<String> grid = new MyGrid2D<>(w, h);
			assertEquals(w, grid.getWidth());
			assertEquals(h, grid.getHeight());
		}
	}

	@Test
	public void clearTest() {
		for (int i = 0; i < N/1000; i++) {
			IGrid2D<String> grid = gridGen.generate();

			clearProperty(grid, strGen.generate());
		}
	}

	@Test
	public void setGetIndepTest() {
		IGrid2D<String> grid = gridGen.generate();

		IGenerator<Integer> wGen = new IntGenerator(0, grid.getWidth());
		IGenerator<Integer> hGen = new IntGenerator(0, grid.getHeight());

		for (int i = 0; i < N; i++) {
			int x1 = wGen.generate();
			int y1 = hGen.generate();
			int x2 = wGen.generate();
			int y2 = hGen.generate();
			String s = strGen.generate();

			setGetIndependentProperty(grid, x1, y1, x2, y2, s);
		}
	}

	/**
	 * get(x,y) is val after set(x, y, val)
	 */
	private <T> void setGetProperty(IGrid2D<T> grid, int x, int y, T val) {
		grid.set(x, y, val);
		assertEquals(val, grid.get(x, y));
	}

	/**
	 * A set on (x1,y1) doesn't affect a get on a different (x2,y2)
	 */
	private <T> void setGetIndependentProperty(IGrid2D<T> grid, int x1, int y1,
			int x2, int y2, T val) {
		if (x1 != x2 && y1 != y2) {
			T s = grid.get(x2, y2);
			grid.set(x1, y1, val);
			assertEquals(s, grid.get(x2, y2));
		}
	}

	/**
	 * all cells are equals to val, after grid.clear(val)
	 */
	private <T> void clearProperty(IGrid2D<T> grid, T val) {
		grid.clear(val);
		for (int x = 0; x < grid.getWidth(); x++)
			for (int y = 0; y < grid.getHeight(); y++)
				assertEquals(val, grid.get(x, y));
	}

	
	public <T> void transitiveProperty(T s1, T s2, T s3) {
		if(s1.equals(s2) && s2.equals(s3))
			assertEquals(s1, s3);
	}
	
	public <T> void reflexiveProperty(T s) {
			assertEquals(s, s);
	}
	public <T> void symmetricProperty(T s1, T s2) {
		if(s1.equals(s2) )
			assertEquals(s2, s1);
	}
	public <T> void hashCodeProperty(T s1, T s2) {
		if(s1.equals(s2) )
			assertEquals(s1.hashCode(), s2.hashCode());
	}

	@Test
	public void transitiveTest1() {
		for(int i = 0; i < N/100; i++) {
			transitiveProperty(gridGen.generate(), gridGen.generate(), gridGen.generate());
		}
	}
	
	@Test
	public void transitiveTest2() {
		for(int i = 0; i < N/100; i++) {
			List<IGrid2D<String>> ss = gridGen.generateEquals(3);
			
			transitiveProperty(ss.get(0), ss.get(1), ss.get(2));
		}
	}
	
	@Test
	public void reflexiveTest() {
		for(int i = 0; i < N/100; i++) {
			reflexiveProperty(gridGen.generate());
		}
	}
	
	
	@Test
	public void symmetricTest1() {
		for(int i = 0; i < N/100; i++) {
			symmetricProperty(gridGen.generate(), gridGen.generate());
		}
	}
	
	@Test
	public void symmetricTest2() {
		for(int i = 0; i < N/100; i++) {
			List<IGrid2D<String>> ss = gridGen.generateEquals(2);
			
			symmetricProperty(ss.get(0), ss.get(1));
		}
	}

	@Test
	public void hashCodeTest1() {
		for(int i = 0; i < N/100; i++) {
			hashCodeProperty(gridGen.generate(), gridGen.generate());
		}
	}
	
	@Test
	public void hashCodeTest2() {
		for(int i = 0; i < N/100; i++) {
			List<IGrid2D<String>> ss = gridGen.generateEquals(2);
			
			hashCodeProperty(ss.get(0), ss.get(1));
		}
	}

}
