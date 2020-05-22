package wblut.hemesh;

import java.util.Comparator;

/**
 *
 */
public interface HE_VertexSort extends Comparator<HE_Vertex> {
	/**
	 *
	 */
	public static class HE_VertexSortXYZ implements HE_VertexSort {
		/**
		 *
		 *
		 * @param v0
		 * @param v1
		 * @return
		 */
		@Override
		public int compare(final HE_Vertex v0, final HE_Vertex v1) {
			return v0.compareTo(v1);
		}
	}

	/**
	 *
	 */
	public static class HE_VertexSortYXZ implements HE_VertexSort {
		/**
		 *
		 *
		 * @param v0
		 * @param v1
		 * @return
		 */
		@Override
		public int compare(final HE_Vertex v0, final HE_Vertex v1) {
			return v0.compareToY1st(v1);
		}
	}

	/**
	 *
	 */
	public static class HE_VertexSortCenter1D implements HE_VertexSort {
		/**  */
		int dim = 0;

		/**
		 *
		 *
		 * @param dim
		 */
		HE_VertexSortCenter1D(final int dim) {
			this.dim = Math.min(Math.max(dim, 0), 2);
		}

		/**
		 *
		 *
		 * @param v0
		 * @param v1
		 * @return
		 */
		@Override
		public int compare(final HE_Vertex v0, final HE_Vertex v1) {
			final double r = v0.getd(dim) - v1.getd(dim);
			if (r > 0) {
				return +1;
			}
			if (r < 0) {
				return -1;
			}
			return 0;
		}
	}
}
