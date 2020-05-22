package wblut.hemesh;

import java.util.Comparator;

/**
 *
 */
public interface HE_FaceSort extends Comparator<HE_Face> {
	/**
	 *
	 */
	public static class HE_FaceSortCenterXYZ implements HE_FaceSort {
		/**
		 *
		 *
		 * @param f0
		 * @param f1
		 * @return
		 */
		@Override
		public int compare(final HE_Face f0, final HE_Face f1) {
			return HE_MeshOp.getFaceCenter(f0).compareTo(HE_MeshOp.getFaceCenter(f1));
		}
	}

	/**
	 *
	 */
	public static class HE_FaceSortCenter1D implements HE_FaceSort {
		/**  */
		int dim = 0;

		/**
		 *
		 *
		 * @param dim
		 */
		public HE_FaceSortCenter1D(final int dim) {
			this.dim = Math.min(Math.max(dim, 0), 2);
		}

		/**
		 *
		 *
		 * @param f0
		 * @param f1
		 * @return
		 */
		@Override
		public int compare(final HE_Face f0, final HE_Face f1) {
			final double r = HE_MeshOp.getFaceCenter(f0).getd(dim) - HE_MeshOp.getFaceCenter(f1).getd(dim);
			if (r > 0) {
				return +1;
			}
			if (r < 0) {
				return -1;
			}
			return 0;
		}
	}

	/**
	 *
	 */
	public static class HE_AABBSortCenter1D implements HE_FaceSort {
		/**  */
		int dim = 0;

		/**
		 *
		 *
		 * @param dim
		 */
		public HE_AABBSortCenter1D(final int dim) {
			this.dim = Math.min(Math.max(dim, 0), 2);
		}

		/**
		 *
		 *
		 * @param f0
		 * @param f1
		 * @return
		 */
		@Override
		public int compare(final HE_Face f0, final HE_Face f1) {
			final double r = f0.getAABB().getCenter(dim) - f1.getAABB().getCenter(dim);
			if (r > 0) {
				return +1;
			}
			if (r < 0) {
				return -1;
			}
			return 0;
		}
	}

	/**
	 *
	 */
	public static class HE_AABBSortMax1D implements HE_FaceSort {
		/**  */
		int dim = 0;

		/**
		 *
		 *
		 * @param dim
		 */
		public HE_AABBSortMax1D(final int dim) {
			this.dim = Math.min(Math.max(dim, 0), 2);
		}

		/**
		 *
		 *
		 * @param f0
		 * @param f1
		 * @return
		 */
		@Override
		public int compare(final HE_Face f0, final HE_Face f1) {
			final double r = f0.getAABB().getMax(dim) - f1.getAABB().getMax(dim);
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
