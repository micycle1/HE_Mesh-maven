package wblut.geom;

import org.eclipse.collections.impl.map.mutable.primitive.DoubleIntHashMap;

public class WB_DoubleIntMap extends DoubleIntHashMap {
	public WB_DoubleIntMap() {
		super();
	}

	@Override
	public void put(final double i, final int value) {
		put(i, value);
	}

	@Override
	public int getIfAbsent(final double i, final int defaultValue) {
		return getIfAbsent(i, defaultValue);
	}

	@Override
	public int get(final double i) {
		return get(i);
	}
}