package inf101.util;

import java.util.ArrayList;
import java.util.List;

public class MyGrid2D<T> implements IGrid2D<T> {
	private List<T> data;
	private int width;
	private int height;

	public MyGrid2D(int width, int height) {
		this(width, height, null);
	}

	public MyGrid2D(int width, int height, T initial) {
		this.width = width;
		this.height = height;
		
		data = new ArrayList<T>(width*height);
		for(int i = 0; i < width*height; i++)
			data.add(initial);
	}
	
	
	
	@Override
	public T get(int x, int y) {
		return data.get(y*getWidth() + x);
	}

	@Override
	public T set(int x, int y, T val) {
		return data.set(y*getWidth() + x, val);
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void clear(T val) {
		for(int i = 0; i < width*height; i++)
			data.set(i, val);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyGrid2D other = (MyGrid2D) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

}
