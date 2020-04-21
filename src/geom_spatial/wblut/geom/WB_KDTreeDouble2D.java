package wblut.geom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wblut.math.WB_Epsilon;

public class WB_KDTreeDouble2D<T extends WB_Coord> {
	private final int _dim;
	private final int _maximumBinSize;
	private final WB_KDNodeDouble<T> root;

	public WB_KDTreeDouble2D() {
		this._dim = 2;
		this._maximumBinSize = 32;
		this.root = new WB_KDNodeDouble<>();
	}

	public WB_KDTreeDouble2D(final int binsize) {
		this._dim = 2;
		this._maximumBinSize = binsize;
		this.root = new WB_KDNodeDouble<>();
	}

	public List<WB_AABB2D> getLeafBounds() {
		final List<WB_AABB2D> leafs = new ArrayList<>();
		root.addLeafBounds(leafs);
		return leafs;
	}

	public List<WB_AABB2D> getAllBounds() {
		final List<WB_AABB2D> all = new ArrayList<>();
		root.addBox(all, 0);
		return all;
	}

	public List<WB_AABB2D> getLeafRegions() {
		final List<WB_AABB2D> leafs = new ArrayList<>();
		root.addLeafRegion(leafs);
		return leafs;
	}

	public List<WB_AABB2D> getAllRegions() {
		final List<WB_AABB2D> all = new ArrayList<>();
		root.addRegion(all, 0);
		return all;
	}

	public double add(final T coord, final double val) {
		return root.add(new WB_KDEntryDouble<>(coord, val, -1));
	}

	public WB_KDEntryDouble<T>[] getRange(final WB_AABB2D aabb) {
		return root.range(aabb);
	}

	public WB_KDEntryDouble<T>[] getRange(final WB_Coord center, final double radius) {
		final double r2 = radius * radius;
		return root.range(center, r2);
	}

	public WB_KDEntryDouble<T>[] getRange(final WB_Coord center, final double lower, final double upper) {
		final double lower2 = lower * lower;
		final double upper2 = upper * upper;
		return root.range(center, lower2, upper2);
	}

	public WB_KDEntryDouble<T>[] getNearestNeighbors(final WB_Coord coord, final int num) {
		final QueryResultDouble<T> heap = new QueryResultDouble<>(num);
		root.findNearest(heap, coord);
		return heap.entries;
	}

	public WB_KDEntryDouble<T> getNearestNeighbor(final WB_Coord coord) {
		final QueryResultDouble<T> heap = new QueryResultDouble<>(1);
		root.findNearest(heap, coord);
		return heap.entries[0];
	}

	public WB_KDEntryDouble<T> getNearestNeighbor(final double x, final double y) {
		final QueryResultDouble<T> heap = new QueryResultDouble<>(1);
		root.findNearest(heap, new WB_Point(x, y));
		return heap.entries[0];
	}

	public static class WB_KDEntryDouble<T extends WB_Coord> {
		public T coord;
		public double value;
		public double d2;

		public WB_KDEntryDouble(final T coord, final double value, final double d2) {
			this.coord = coord;
			this.value = value;
			this.d2 = d2;
		}
	}

	@SuppressWarnings("hiding")
	private class WB_KDNodeDouble<T extends WB_Coord> {
		private WB_AABB2D _limits;
		private WB_KDNodeDouble<T> _negative, _positive;
		private final WB_AABB2D _region;
		private WB_KDEntryDouble<T>[] _bin;
		private boolean _isLeaf;
		private int _binSize;
		private int _discriminator;
		private double _sliceValue;
		private int _id;

		@SuppressWarnings("unchecked")
		private WB_KDNodeDouble() {
			_bin = new WB_KDEntryDouble[_maximumBinSize];
			_negative = _positive = null;
			_limits = null;
			_isLeaf = true;
			_binSize = 0;
			final double[] min = new double[_dim];
			Arrays.fill(min, Double.NEGATIVE_INFINITY);
			final double[] max = new double[_dim];
			Arrays.fill(max, Double.POSITIVE_INFINITY);
			_region = new WB_AABB2D(min, max);
			_id = 0;
		}

		private void addLeafBounds(final List<WB_AABB2D> leafs) {
			if (_isLeaf) {
				final WB_AABB2D box = _limits.get();
				box.setId(_id);
				leafs.add(box);
			} else {
				_positive.addLeafBounds(leafs);
				_negative.addLeafBounds(leafs);
			}
		}

		private void addLeafRegion(final List<WB_AABB2D> leafs) {
			if (_isLeaf) {
				final WB_AABB2D box = _region.get();
				if (box.getMinX() == Double.NEGATIVE_INFINITY) {
					box._min[0] = _limits._min[0];
				}
				if (box.getMinY() == Double.NEGATIVE_INFINITY) {
					box._min[1] = _limits._min[1];
				}
				if (box.getMaxX() == Double.POSITIVE_INFINITY) {
					box._max[0] = _limits._max[0];
				}
				if (box.getMaxY() == Double.POSITIVE_INFINITY) {
					box._max[1] = _limits._max[1];
				}
				box.setId(_id);
				leafs.add(box);
			} else {
				_positive.addLeafRegion(leafs);
				_negative.addLeafRegion(leafs);
			}
		}

		private void addBox(final List<WB_AABB2D> leafs, final int level) {
			final WB_AABB2D box = _limits.get();
			box.setId(_id);
			leafs.add(box);
			if (!_isLeaf) {
				_positive.addBox(leafs, level + 1);
				_negative.addBox(leafs, level + 1);
			}
		}

		private void addRegion(final List<WB_AABB2D> leafs, final int level) {
			final WB_AABB2D box = _region.get();
			if (box.getMinX() == Double.NEGATIVE_INFINITY) {
				box._min[0] = _limits._min[0];
			}
			if (box.getMinY() == Double.NEGATIVE_INFINITY) {
				box._min[1] = _limits._min[1];
			}
			if (box.getMaxX() == Double.POSITIVE_INFINITY) {
				box._max[0] = _limits._max[0];
			}
			if (box.getMaxY() == Double.POSITIVE_INFINITY) {
				box._max[1] = _limits._max[1];
			}
			box.setId(_id);
			leafs.add(box);
			if (!_isLeaf) {
				_positive.addRegion(leafs, level + 1);
				_negative.addRegion(leafs, level + 1);
			}
		}

		private double add(final WB_KDEntryDouble<T> entry) {
			if (_isLeaf) {
				return addInLeaf(entry);
			} else {
				extendBounds(entry.coord);
				if (entry.coord.getd(_discriminator) > _sliceValue) {
					return _positive.add(entry);
				} else {
					return _negative.add(entry);
				}
			}
		}

		private double addInLeaf(final WB_KDEntryDouble<T> entry) {
			final double lookup = lookup(entry.coord);
			if (Double.isNaN(lookup)) {
				extendBounds(entry.coord);
				if (_binSize + 1 > _maximumBinSize) {
					addLevel();
					add(entry);
					return Double.NaN;
				}
				_bin[_binSize] = entry;
				_binSize++;
				return Double.NaN;
			}
			return lookup;
		}

		private double lookup(final WB_Coord point) {
			for (int i = 0; i < _binSize; i++) {
				if (WB_Epsilon.isZeroSq(WB_GeometryOp2D.getSqDistance2D(point, _bin[i].coord))) {
					return _bin[i].value;
				}
			}
			return Double.NaN;
		}

		private void findNearest(final QueryResultDouble<T> heap, final WB_Coord data) {
			if (_binSize == 0) {
				return;
			}
			if (_isLeaf) {
				for (int i = 0; i < _binSize; i++) {
					final double dist = WB_GeometryOp2D.getSqDistance2D(_bin[i].coord, data);
					heap.tryToAdd(dist, _bin[i]);
				}
			} else {
				if (data.getd(_discriminator) > _sliceValue) {
					_positive.findNearest(heap, data);
					if (_negative._binSize == 0) {
						return;
					}
					if (heap.size < heap.capacity
							|| _negative._limits.getDistanceSquare(data) < heap.getDistanceSquare(heap.size - 1)) {
						_negative.findNearest(heap, data);
					}
				} else {
					_negative.findNearest(heap, data);
					if (_positive._binSize == 0) {
						return;
					}
					if (heap.size < heap.capacity
							|| _positive._limits.getDistanceSquare(data) < heap.getDistanceSquare(heap.size - 1)) {
						_positive.findNearest(heap, data);
					}
				}
			}
		}

		private WB_KDEntryDouble<T>[] range(final WB_AABB2D range) {
			if (_bin == null) {
				@SuppressWarnings("unchecked")
				WB_KDEntryDouble<T>[] tmp = new WB_KDEntryDouble[0];
				if (_negative._limits.intersects(range)) {
					final WB_KDEntryDouble<T>[] tmpl = _negative.range(range);
					if (0 == tmp.length) {
						tmp = tmpl;
					}
				}
				if (_positive._limits.intersects(range)) {
					final WB_KDEntryDouble<T>[] tmpr = _positive.range(range);
					if (0 == tmp.length) {
						tmp = tmpr;
					} else if (0 < tmpr.length) {
						@SuppressWarnings("unchecked")
						final WB_KDEntryDouble<T>[] tmp2 = new WB_KDEntryDouble[tmp.length + tmpr.length];
						System.arraycopy(tmp, 0, tmp2, 0, tmp.length);
						System.arraycopy(tmpr, 0, tmp2, tmp.length, tmpr.length);
						tmp = tmp2;
					}
				}
				return tmp;
			}
			@SuppressWarnings("unchecked")
			final WB_KDEntryDouble<T>[] tmp = new WB_KDEntryDouble[_binSize];
			int n = 0;
			for (int i = 0; i < _binSize; i++) {
				if (range.contains(_bin[i].coord)) {
					tmp[n++] = _bin[i];
				}
			}
			@SuppressWarnings("unchecked")
			final WB_KDEntryDouble<T>[] tmp2 = new WB_KDEntryDouble[n];
			System.arraycopy(tmp, 0, tmp2, 0, n);
			return tmp2;
		}

		private WB_KDEntryDouble<T>[] range(final WB_Coord center, final double r2) {
			if (_bin == null) {
				@SuppressWarnings("unchecked")
				WB_KDEntryDouble<T>[] tmp = new WB_KDEntryDouble[0];
				if (_negative._limits.getDistanceSquare(center) <= r2) {
					final WB_KDEntryDouble<T>[] tmpl = _negative.range(center, r2);
					if (tmp.length == 0) {
						tmp = tmpl;
					}
				}
				if (_positive._limits.getDistanceSquare(center) <= r2) {
					final WB_KDEntryDouble<T>[] tmpr = _positive.range(center, r2);
					if (tmp.length == 0) {
						tmp = tmpr;
					} else if (0 < tmpr.length) {
						@SuppressWarnings("unchecked")
						final WB_KDEntryDouble<T>[] tmp2 = new WB_KDEntryDouble[tmp.length + tmpr.length];
						System.arraycopy(tmp, 0, tmp2, 0, tmp.length);
						System.arraycopy(tmpr, 0, tmp2, tmp.length, tmpr.length);
						tmp = tmp2;
					}
				}
				return tmp;
			}
			@SuppressWarnings("unchecked")
			final WB_KDEntryDouble<T>[] tmp = new WB_KDEntryDouble[_binSize];
			int n = 0;
			for (int i = 0; i < _binSize; i++) {
				final double d2 = WB_GeometryOp2D.getSqDistance2D(center, _bin[i].coord);
				if (d2 <= r2) {
					_bin[i].d2 = d2;
					tmp[n++] = _bin[i];
				}
			}
			@SuppressWarnings("unchecked")
			final WB_KDEntryDouble<T>[] tmp2 = new WB_KDEntryDouble[n];
			System.arraycopy(tmp, 0, tmp2, 0, n);
			return tmp2;
		}

		private WB_KDEntryDouble<T>[] range(final WB_Coord center, final double lower2, final double upper2) {
			if (_bin == null) {
				@SuppressWarnings("unchecked")
				WB_KDEntryDouble<T>[] tmp = new WB_KDEntryDouble[0];
				if (_negative._limits.getDistanceSquare(center) <= upper2) {
					final WB_KDEntryDouble<T>[] tmpl = _negative.range(center, lower2, upper2);
					if (tmp.length == 0) {
						tmp = tmpl;
					}
				}
				if (_positive._limits.getDistanceSquare(center) <= upper2) {
					final WB_KDEntryDouble<T>[] tmpr = _positive.range(center, lower2, upper2);
					if (tmp.length == 0) {
						tmp = tmpr;
					} else if (0 < tmpr.length) {
						@SuppressWarnings("unchecked")
						final WB_KDEntryDouble<T>[] tmp2 = new WB_KDEntryDouble[tmp.length + tmpr.length];
						System.arraycopy(tmp, 0, tmp2, 0, tmp.length);
						System.arraycopy(tmpr, 0, tmp2, tmp.length, tmpr.length);
						tmp = tmp2;
					}
				}
				return tmp;
			}
			@SuppressWarnings("unchecked")
			final WB_KDEntryDouble<T>[] tmp = new WB_KDEntryDouble[_binSize];
			int n = 0;
			for (int i = 0; i < _binSize; i++) {
				final double d2 = WB_GeometryOp2D.getSqDistance2D(center, _bin[i].coord);
				if (d2 <= upper2 && d2 >= lower2) {
					_bin[i].d2 = d2;
					tmp[n++] = _bin[i];
				}
			}
			@SuppressWarnings("unchecked")
			final WB_KDEntryDouble<T>[] tmp2 = new WB_KDEntryDouble[n];
			System.arraycopy(tmp, 0, tmp2, 0, n);
			return tmp2;
		}

		private void addLevel() {
			_discriminator = _limits.maxOrdinate();
			_negative = new WB_KDNodeDouble<>();
			_positive = new WB_KDNodeDouble<>();
			_negative._id = 2 * _id;
			_positive._id = 2 * _id + 1;
			_sliceValue = (_limits.getMax(_discriminator) + _limits.getMin(_discriminator)) * 0.5;
			for (int i = 0; i < _dim; i++) {
				_negative._region._min[i] = _region._min[i];
				_positive._region._max[i] = _region._max[i];
				if (i == _discriminator) {
					_negative._region._max[i] = _sliceValue;
					_positive._region._min[i] = _sliceValue;
				} else {
					_negative._region._max[i] = _region._max[i];
					_positive._region._min[i] = _region._min[i];
				}
			}
			for (int i = 0; i < _binSize; ++i) {
				if (_bin[i].coord.getd(_discriminator) > _sliceValue) {
					_positive.addInLeaf(_bin[i]);
				} else {
					_negative.addInLeaf(_bin[i]);
				}
			}
			_bin = null;
			_isLeaf = false;
		}

		private void extendBounds(final WB_Coord coord) {
			if (_limits == null) {
				_limits = new WB_AABB2D(coord);
			} else {
				_limits.expandToInclude(coord);
			}
		}
	}

	@SuppressWarnings("hiding")
	public class QueryResultDouble<T extends WB_Coord> {
		private final WB_KDEntryDouble<T>[] entries;
		private final double[] distSqs;
		private final int capacity;
		private int size;

		@SuppressWarnings("unchecked")
		protected QueryResultDouble(final int capacity) {
			this.entries = new WB_KDEntryDouble[capacity];
			this.distSqs = new double[capacity];
			this.capacity = capacity;
			this.size = 0;
		}

		protected void tryToAdd(final double dist, final WB_KDEntryDouble<T> entry) {
			int i = size;
			for (; i > 0 && distSqs[i - 1] > dist; --i) {
			}
			if (i >= capacity) {
				return;
			}
			if (size < capacity) {
				size++;
			}
			final int j = i + 1;
			System.arraycopy(distSqs, i, distSqs, j, size - j);
			distSqs[i] = dist;
			System.arraycopy(entries, i, entries, j, size - j);
			entry.d2 = dist;
			entries[i] = entry;
		}

		public WB_KDEntryDouble<T> getEntry(final int i) {
			if (size == 0) {
				return null;
			}
			return entries[i];
		}

		public double getDistanceSquare(final int i) {
			if (size == 0) {
				return Double.NaN;
			}
			return distSqs[i];
		}

		public int size() {
			return size;
		}
	}
}