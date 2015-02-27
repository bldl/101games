package inf101.grid;

import java.util.ArrayList;
import java.util.List;

public class MyGrid2D<T> implements IGrid2D<T> {
	private final List<T> data;
	private final IArea area;

	public MyGrid2D(int width, int height) {
		this(new Rectangle(width, height));
	}
	public MyGrid2D(int width, int height, T initial) {
		this(new Rectangle(width, height), initial);
	}
	
	public MyGrid2D(IArea area) {
		this(area, null);
	}

	public MyGrid2D(IArea area, T initial) {
		this.area = area;
		this.data = new ArrayList<T>(area.getSize());
		for(int i = 0; i < area.getSize(); i++)
			data.add(initial);
	}

	@Override
	public void clear(T val) {
		for(int i = 0; i < area.getSize(); i++)
			data.set(i, val);
	}

	public boolean contains(int x, int y) {
		return area.contains(x, y);
	}

	public boolean contains(IPosition pos) {
		return area.contains(pos);
	}

	@Override
	public IGrid2D<T> copy() {
		MyGrid2D<T> copy = new MyGrid2D<>(area, null);
		for(int i = 0; i < data.size(); i++)
			copy.data.set(i, data.get(i));
		return copy;
	}

	@Override
	public T get(int x, int y) {
		return data.get(area.getIndex(x, y));
	}

	@Override
	public int getHeight() {
		return area.getHeight();
	}

	public int getIndex(int x, int y) {
		return area.getIndex(x, y);
	}
	
	
	
	public int getIndex(IPosition pos) {
		return area.getIndex(pos);
	}

	public int getSize() {
		return area.getSize();
	}

	@Override
	public int getWidth() {
		return area.getWidth();
	}

	@Override
	public T set(int x, int y, T val) {
		return data.set(area.getIndex(x, y), val);
	}

	public boolean wrapsHorizontally() {
		return area.wrapsHorizontally();
	}
	public boolean wrapsVertically() {
		return area.wrapsVertically();
	}

}
