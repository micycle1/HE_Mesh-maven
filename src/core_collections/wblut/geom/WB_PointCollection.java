package wblut.geom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.GZIPInputStream;

import wblut.geom.WB_KDTreeInteger3D.WB_KDEntryInteger;
import wblut.hemesh.HE_Mesh;
import wblut.math.WB_Epsilon;

/**
 *
 */
public abstract class WB_PointCollection extends WB_CoordCollection {
	public static WB_PointCollection getCollection(final WB_CoordCollection coords) {
		return WB_PointCollection.getCollection(coords.toList());
	}

	/**
	 *
	 *
	 * @param coords
	 * @return
	 */
	public static WB_PointCollection getCollection(final WB_Coord... coords) {
		return new WB_PointCollectionArray(coords);
	}

	/**
	 *
	 *
	 * @param path
	 * @param scale
	 * @return
	 */
	public static WB_PointCollection getCollection(final String path, final double scale) {
		return new WB_PointCollectionObj(path, scale);
	}

	/**
	 *
	 *
	 * @param polygon
	 * @return
	 */
	public static WB_PointCollection getCollection(final WB_Polygon polygon) {
		return new WB_PointCollectionPolygon(polygon);
	}

	/**
	 *
	 *
	 * @param coords
	 * @return
	 */
	public static WB_PointCollection getCollection(final Collection<? extends WB_Coord> coords) {
		return new WB_PointCollectionList(coords);
	}

	/**
	 *
	 *
	 * @param factory
	 * @param numberOfPoints
	 * @return
	 */
	public static WB_PointCollection getCollection(final WB_PointFactory factory, final int numberOfPoints) {
		return new WB_PointCollectionFactory(factory, numberOfPoints);
	}

	/**
	 *
	 *
	 * @param point
	 * @param factory
	 * @param numberOfPoints
	 * @return
	 */
	public static WB_PointCollection getCollection(final WB_Coord point, final WB_PointFactory factory,
			final int numberOfPoints) {
		return new WB_PointCollectionFactory(point, factory, numberOfPoints);
	}

	/**
	 *
	 *
	 * @param coords
	 * @return
	 */
	public static WB_PointCollection getCollection(final double[][] coords) {
		return new WB_PointCollectionRaw(coords);
	}

	/**
	 *
	 *
	 * @param coords
	 * @return
	 */
	public static WB_PointCollection getCollection(final float[][] coords) {
		return new WB_PointCollectionRaw(coords);
	}

	/**
	 *
	 *
	 * @param coords
	 * @return
	 */
	public static WB_PointCollection getCollection(final int[][] coords) {
		return new WB_PointCollectionRaw(coords);
	}

	/**
	 *
	 *
	 * @param coords
	 * @return
	 */
	public static WB_PointCollection getCollection(final double[] coords) {
		return new WB_PointCollectionRaw(coords);
	}

	/**
	 *
	 *
	 * @param coords
	 * @return
	 */
	public static WB_PointCollection getCollection(final float[] coords) {
		return new WB_PointCollectionRaw(coords);
	}

	/**
	 *
	 *
	 * @param coords
	 * @return
	 */
	public static WB_PointCollection getCollection(final int[] coords) {
		return new WB_PointCollectionRaw(coords);
	}

	/**
	 *
	 *
	 * @param mesh
	 * @return
	 */
	public static WB_PointCollection getCollection(final HE_Mesh mesh) {
		return getCollection(mesh.getVerticesAsCoord());
	}

	/**
	 *
	 *
	 * @param mesh
	 * @return
	 */
	public static WB_PointCollection getCollection(final WB_SimpleMesh mesh) {
		return getCollection(mesh.vertices);
	}

	/**
	 *
	 *
	 * @param factory
	 * @return
	 */
	@Override
	public WB_PointCollection noise(final WB_VectorFactory factory) {
		return new WB_PointCollectionNoise(this, factory);
	}

	/**
	 *
	 *
	 * @param d
	 * @return
	 */
	@Override
	public WB_PointCollection jitter(final double d) {
		return new WB_PointCollectionJitter(this, d);
	}

	/**
	 *
	 *
	 * @param map
	 * @return
	 */
	public WB_PointCollection map(final WB_Map3D map) {
		return new WB_PointCollectionMap(this, map);
	}

	/**
	 *
	 *
	 * @return
	 */
	@Override
	public WB_PointCollection unique() {
		return new WB_PointCollectionUnique(this);
	}

	/**
	 *
	 *
	 * @param i
	 * @return
	 */
	@Override
	abstract public WB_Point get(final int i);

	/**
	 *
	 *
	 * @param fromInc
	 * @param toExcl
	 * @return
	 */
	@Override
	public List<WB_Coord> subList(final int fromInc, final int toExcl) {
		return toList().subList(fromInc, toExcl);
	}

	/**
	 *
	 *
	 * @return
	 */
	@Override
	abstract public int size();

	/**
	 *
	 *
	 * @return
	 */
	@Override
	abstract public WB_Point[] toArray();

	/**
	 *
	 *
	 * @return
	 */
	@Override
	abstract public List<WB_Coord> toList();

	/**
	 *
	 */
	static class WB_PointCollectionArray extends WB_PointCollection {
		/**  */
		WB_Point[] array;

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionArray(final WB_Coord[] coords) {
			this.array = new WB_Point[coords.length];
			for (int i = 0; i < coords.length; i++) {
				array[i] = new WB_Point(coords[i]);
			}
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return array[i];
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return array[i].xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return array[i].yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return array[i].zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return array.length;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (final WB_Point c : array) {
				list.add(c);
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			array = Arrays.copyOf(array, array.length + coord.length);
			for (int i = 0; i < coord.length; i++) {
				array[array.length - coord.length + i] = new WB_Point(coord[i]);
			}
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coords) {
			array = Arrays.copyOf(array, array.length + coords.size());
			int i = 0;
			for (final WB_Coord coord : coords) {
				array[array.length - coords.size() + (i++)] = new WB_Point(coord);
			}
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			array = Arrays.copyOf(array, array.length + coords.size());
			for (int i = 0; i < coords.size(); i++) {
				array[array.length - coords.size() + i] = new WB_Point(coords.get(i));
			}
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionList extends WB_PointCollection {
		/**  */
		List<WB_Point> list;

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionList(final Collection<? extends WB_Coord> coords) {
			this.list = new WB_PointList();
			for (final WB_Coord c : coords) {
				list.add(new WB_Point(c));
			}
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return list.get(i);
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return list.get(i).xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return list.get(i).yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return list.get(i).zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return list.size();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[list.size()];
			int i = 0;
			for (final WB_Point c : list) {
				array[i++] = c;
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> clist = new WB_CoordList();
			for (final WB_Point c : list) {
				clist.add(c);
			}
			return clist;
		}

		@Override
		public void add(final WB_Coord... coord) {
			for (final WB_Coord element : coord) {
				list.add(new WB_Point(element));
			}
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coords) {
			for (final WB_Coord coord : coords) {
				list.add(new WB_Point(coord));
			}
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			for (int i = 0; i < coords.size(); i++) {
				list.add(new WB_Point(coords.get(i)));
			}
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionRaw extends WB_PointCollection {
		/**  */
		WB_Point[] array;

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionRaw(final double[][] coords) {
			this.array = new WB_Point[coords.length];
			int id = 0;
			for (final double[] coord : coords) {
				array[id++] = new WB_Point(coord[0], coord[1], coord[2]);
			}
		}

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionRaw(final float[][] coords) {
			this.array = new WB_Point[coords.length];
			int id = 0;
			for (final float[] coord : coords) {
				array[id++] = new WB_Point(coord[0], coord[1], coord[2]);
			}
		}

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionRaw(final int[][] coords) {
			this.array = new WB_Point[coords.length];
			int id = 0;
			for (final int[] coord : coords) {
				array[id++] = new WB_Point(coord[0], coord[1], coord[2]);
			}
		}

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionRaw(final double[] coords) {
			this.array = new WB_Point[coords.length / 3];
			for (int id = 0; id < coords.length; id += 3) {
				array[id / 3] = new WB_Point(coords[id], coords[id + 1], coords[id + 2]);
			}
		}

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionRaw(final float[] coords) {
			this.array = new WB_Point[coords.length / 3];
			for (int id = 0; id < coords.length; id += 3) {
				array[id / 3] = new WB_Point(coords[id], coords[id + 1], coords[id + 2]);
			}
		}

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionRaw(final int[] coords) {
			this.array = new WB_Point[coords.length / 3];
			for (int id = 0; id < coords.length; id += 3) {
				array[id / 3] = new WB_Point(coords[id], coords[id + 1], coords[id + 2]);
			}
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return array[i];
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return array[i].xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return array[i].yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return array[i].zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return array.length;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (final WB_Point c : array) {
				list.add(c);
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			array = Arrays.copyOf(array, array.length + coord.length);
			for (int i = 0; i < coord.length; i++) {
				array[array.length - coord.length + i] = new WB_Point(coord[i]);
			}
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coords) {
			array = Arrays.copyOf(array, array.length + coords.size());
			int i = 0;
			for (final WB_Coord coord : coords) {
				array[array.length - coords.size() + (i++)] = new WB_Point(coord);
			}
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			array = Arrays.copyOf(array, array.length + coords.size());
			for (int i = 0; i < coords.size(); i++) {
				array[array.length - coords.size() + i] = new WB_Point(coords.get(i));
			}
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionFlat extends WB_PointCollection {
		/**  */
		double[] array;

		/**
		 *
		 *
		 * @param coords
		 */
		WB_PointCollectionFlat(final double[] coords) {
			this.array = coords;
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return new WB_Point(array[3 * i], array[3 * i + 1], array[3 * i + 2]);
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return array[3 * i];
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return array[3 * i + 1];
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return array[3 * i + 2];
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return array.length / 3;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] out = new WB_Point[array.length / 3];
			for (int i = 0; i < array.length; i += 3) {
				out[i / 3] = new WB_Point(array[i], array[i + 1], array[i + 2]);
			}
			return out;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (int i = 0; i < array.length; i += 3) {
				list.add(new WB_Point(array[i], array[i + 1], array[i + 2]));
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			array = Arrays.copyOf(array, array.length + 3 * coord.length);
			for (int i = 0; i < 3 * coord.length; i += 3) {
				array[array.length - 3 * coord.length + i] = coord[i].xd();
				array[array.length - 3 * coord.length + i + 1] = coord[i].yd();
				array[array.length - 3 * coord.length + i + 2] = coord[i].zd();
			}
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coords) {
			array = Arrays.copyOf(array, array.length + 3 * coords.size());
			int i = 0;
			for (final WB_Coord coord : coords) {
				array[array.length - 3 * coords.size() + (i++)] = coord.xd();
				array[array.length - 3 * coords.size() + (i++)] = coord.yd();
				array[array.length - 3 * coords.size() + (i++)] = coord.zd();
			}
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			array = Arrays.copyOf(array, array.length + 3 * coords.size());
			for (int i = 0; i < 3 * coords.size(); i += 3) {
				array[array.length - 3 * coords.size() + i] = coords.getX(i);
				array[array.length - 3 * coords.size() + i + 1] = coords.getY(i);
				array[array.length - 3 * coords.size() + i + 2] = coords.getZ(i);
			}
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionFactory extends WB_PointCollection {
		/**  */
		WB_Point[] array;

		/**
		 *
		 *
		 * @param factory
		 * @param numberOfPoints
		 */
		WB_PointCollectionFactory(final WB_PointFactory factory, final int numberOfPoints) {
			this.array = new WB_Point[numberOfPoints];
			for (int i = 0; i < numberOfPoints; i++) {
				array[i] = factory.nextPoint();
			}
		}

		/**
		 *
		 *
		 * @param point
		 * @param factory
		 * @param numberOfPoints
		 */
		WB_PointCollectionFactory(final WB_Coord point, final WB_PointFactory factory, final int numberOfPoints) {
			this.array = new WB_Point[numberOfPoints + 1];
			array[0] = new WB_Point(point);
			for (int i = 1; i <= numberOfPoints; i++) {
				array[i] = factory.nextPoint();
			}
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return array[i];
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return array[i].xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return array[i].yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return array[i].zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return array.length;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (final WB_Point c : array) {
				list.add(c);
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			array = Arrays.copyOf(array, array.length + coord.length);
			for (int i = 0; i < coord.length; i++) {
				array[array.length - coord.length + i] = new WB_Point(coord[i]);
			}
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coords) {
			array = Arrays.copyOf(array, array.length + coords.size());
			int i = 0;
			for (final WB_Coord coord : coords) {
				array[array.length - coords.size() + (i++)] = new WB_Point(coord);
			}
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			array = Arrays.copyOf(array, array.length + coords.size());
			for (int i = 0; i < coords.size(); i++) {
				array[array.length - coords.size() + i] = new WB_Point(coords.get(i));
			}
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionPolygon extends WB_PointCollection {
		/**  */
		WB_Polygon polygon;

		/**
		 *
		 *
		 * @param polygon
		 */
		WB_PointCollectionPolygon(final WB_Polygon polygon) {
			this.polygon = polygon;
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return polygon.getPoint(i);
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return polygon.getPoint(i).xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return polygon.getPoint(i).yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return polygon.getPoint(i).zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return polygon.getNumberOfShellPoints();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[polygon.getNumberOfShellPoints()];
			for (int i = 0; i < polygon.getNumberOfShellPoints(); i++) {
				array[i] = polygon.getPoint(i);
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (int i = 0; i < polygon.getNumberOfShellPoints(); i++) {
				list.add(polygon.getPoint(i));
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			throw new java.lang.UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coord) {
			throw new java.lang.UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			throw new java.lang.UnsupportedOperationException("Not supported yet.");
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionNoise extends WB_PointCollection {
		/**  */
		WB_PointCollection source;
		/**  */
		WB_Vector[] noise;

		/**
		 *
		 *
		 * @param source
		 * @param factory
		 */
		WB_PointCollectionNoise(final WB_PointCollection source, final WB_VectorFactory factory) {
			this.source = source;
			noise = new WB_Vector[source.size()];
			for (int i = 0; i < source.size(); i++) {
				noise[i] = factory.nextVector();
			}
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return WB_Point.add(source.get(i), noise[i]);
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return source.getX(i) + noise[i].xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return source.getY(i) + noise[i].yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return source.getZ(i) + noise[i].xd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return source.size();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[source.size()];
			for (int i = 0; i < source.size(); i++) {
				array[i] = WB_Point.add(source.get(i), noise[i]);
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (int i = 0; i < source.size(); i++) {
				list.add(WB_Point.add(source.get(i), noise[i]));
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			source.add(coord);
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coord) {
			source.add(coord);
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			source.add(coords);
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionMap extends WB_PointCollection {
		/**  */
		WB_PointCollection source;
		/**  */
		WB_Map3D map;

		/**
		 *
		 *
		 * @param source
		 * @param map
		 */
		WB_PointCollectionMap(final WB_PointCollection source, final WB_Map3D map) {
			this.source = source;
			this.map = map;
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return map.mapPoint3D(source.get(i));
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return map.mapPoint3D(source.get(i)).xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return map.mapPoint3D(source.get(i)).yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return map.mapPoint3D(source.get(i)).zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return source.size();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[source.size()];
			for (int i = 0; i < source.size(); i++) {
				array[i] = map.mapPoint3D(source.get(i));
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (int i = 0; i < source.size(); i++) {
				list.add(map.mapPoint3D(source.get(i)));
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			source.add(coord);
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coord) {
			source.add(coord);
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			source.add(coords);
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionUnmap3D extends WB_PointCollection {
		/**  */
		WB_PointCollection source;
		/**  */
		WB_Map3D map;

		/**
		 *
		 *
		 * @param source
		 * @param map
		 */
		WB_PointCollectionUnmap3D(final WB_PointCollection source, final WB_Map3D map) {
			this.source = source;
			this.map = map;
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return map.unmapPoint3D(source.get(i));
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return map.unmapPoint3D(source.get(i)).xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return map.unmapPoint3D(source.get(i)).yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return map.unmapPoint3D(source.get(i)).zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return source.size();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[source.size()];
			for (int i = 0; i < source.size(); i++) {
				array[i] = map.unmapPoint3D(source.get(i));
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (int i = 0; i < source.size(); i++) {
				list.add(map.unmapPoint3D(source.get(i)));
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			source.add(coord);
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coord) {
			source.add(coord);
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			source.add(coords);
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionUnmap2D extends WB_PointCollection {
		/**  */
		WB_PointCollection source;
		/**  */
		WB_Map2D map;

		/**
		 *
		 *
		 * @param source
		 * @param map
		 */
		WB_PointCollectionUnmap2D(final WB_PointCollection source, final WB_Map2D map) {
			this.source = source;
			this.map = map;
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return map.unmapPoint2D(source.get(i));
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return map.unmapPoint2D(source.get(i)).xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return map.unmapPoint2D(source.get(i)).yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return map.unmapPoint2D(source.get(i)).zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return source.size();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[source.size()];
			for (int i = 0; i < source.size(); i++) {
				array[i] = map.unmapPoint2D(source.get(i));
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (int i = 0; i < source.size(); i++) {
				list.add(map.unmapPoint2D(source.get(i)));
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			source.add(coord);
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coord) {
			source.add(coord);
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			source.add(coords);
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionJitter extends WB_PointCollection {
		/**  */
		WB_PointCollection source;
		/**  */
		WB_Vector[] noise;

		/**
		 *
		 *
		 * @param source
		 * @param d
		 */
		WB_PointCollectionJitter(final WB_PointCollection source, final double d) {
			this.source = source;
			noise = new WB_Point[source.size()];
			final WB_VectorFactory factory = new WB_RandomOnSphere();
			for (int i = 0; i < source.size(); i++) {
				noise[i] = factory.nextVector().mulSelf(d);
			}
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return WB_Point.add(source.get(i), noise[i]);
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return source.getX(i) + noise[i].xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return source.getY(i) + noise[i].yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return source.getZ(i) + noise[i].xd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return source.size();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[source.size()];
			for (int i = 0; i < source.size(); i++) {
				array[i] = WB_Point.add(source.get(i), noise[i]);
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> list = new WB_CoordList();
			for (int i = 0; i < source.size(); i++) {
				list.add(WB_Point.add(source.get(i), noise[i]));
			}
			return list;
		}

		@Override
		public void add(final WB_Coord... coord) {
			source.add(coord);
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coord) {
			source.add(coord);
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			source.add(coords);
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionUnique extends WB_PointCollection {
		/**  */
		List<WB_Point> list;

		/**
		 *
		 *
		 * @param source
		 */
		WB_PointCollectionUnique(final WB_PointCollection source) {
			this.list = new WB_PointList();
			final WB_KDTreeInteger3D<WB_Point> tree = new WB_KDTreeInteger3D<>();
			WB_Point c;
			WB_KDEntryInteger<WB_Point>[] neighbors;
			for (int i = 0; i < source.size(); i++) {
				c = source.get(i);
				neighbors = tree.getNearestNeighbors(c, 1);
				if (neighbors[0].d2 > WB_Epsilon.SQEPSILON) {
					tree.add(c, i);
					list.add(c);
				}
			}
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return list.get(i);
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return list.get(i).xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return list.get(i).yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return list.get(i).zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return list.size();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[list.size()];
			int i = 0;
			for (final WB_Point c : list) {
				array[i++] = c;
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> clist = new WB_CoordList();
			for (final WB_Point c : list) {
				clist.add(c);
			}
			return clist;
		}

		@Override
		public void add(final WB_Coord... coord) {
			for (final WB_Coord element : coord) {
				list.add(new WB_Point(element));
			}
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coords) {
			for (final WB_Coord coord : coords) {
				list.add(new WB_Point(coord));
			}
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			for (int i = 0; i < coords.size(); i++) {
				list.add(new WB_Point(coords.get(i)));
			}
		}
	}

	/**
	 *
	 */
	static class WB_PointCollectionObj extends WB_PointCollection {
		/**  */
		List<WB_Point> list = new WB_PointList();

		/**
		 *
		 *
		 * @param path
		 * @param scale
		 */
		WB_PointCollectionObj(final String path, final double scale) {
			this.list = new WB_PointList();
			final File file = new File(path);
			try (InputStream is = createInputStream(file);
					final BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));) {
				if (is == null) {
					return;
				}
				String line;
				while ((line = reader.readLine()) != null) {
					final String[] parts = line.split("\\s+");
					if (parts[0].equals("v")) {
						final double x1 = scale * Double.parseDouble(parts[1]);
						final double y1 = scale * Double.parseDouble(parts[2]);
						final double z1 = parts.length > 3 ? scale * Double.parseDouble(parts[3]) : 0;
						final WB_Point pointLoc = new WB_Point(x1, y1, z1);
						list.add(pointLoc);
					}
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 *
		 *
		 * @param file
		 * @return
		 */
		private InputStream createInputStream(final File file) {
			if (file == null) {
				throw new IllegalArgumentException("file can't be null");
			}
			try {
				InputStream stream = new FileInputStream(file);
				if (file.getName().toLowerCase().endsWith(".gz")) {
					stream = new GZIPInputStream(stream);
				}
				return stream;
			} catch (final IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public WB_Point get(final int i) {
			return list.get(i);
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getX(final int i) {
			return list.get(i).xd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getY(final int i) {
			return list.get(i).yd();
		}

		/**
		 *
		 *
		 * @param i
		 * @return
		 */
		@Override
		public double getZ(final int i) {
			return list.get(i).zd();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public int size() {
			return list.size();
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public WB_Point[] toArray() {
			final WB_Point[] array = new WB_Point[list.size()];
			int i = 0;
			for (final WB_Point c : list) {
				array[i++] = c;
			}
			return array;
		}

		/**
		 *
		 *
		 * @return
		 */
		@Override
		public List<WB_Coord> toList() {
			final List<WB_Coord> clist = new WB_CoordList();
			for (final WB_Point c : list) {
				clist.add(c);
			}
			return clist;
		}

		@Override
		public void add(final WB_Coord... coord) {
			for (final WB_Coord element : coord) {
				list.add(new WB_Point(element));
			}
		}

		@Override
		public void add(final Collection<? extends WB_Coord> coords) {
			for (final WB_Coord coord : coords) {
				list.add(new WB_Point(coord));
			}
		}

		@Override
		public void add(final WB_CoordCollection coords) {
			for (int i = 0; i < coords.size(); i++) {
				list.add(new WB_Point(coords.get(i)));
			}
		}
	}
}
