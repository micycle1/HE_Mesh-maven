package wblut.math;

/**
 *
 */
public class WB_Epsilon {
	/**  */
	static public double EPSILON = 1e-6;
	/**  */
	static public double SCALE = 1e9;
	/**  */
	static public double SQEPSILON = 1e-12;
	/**  */
	static public double EPSILONANGLE = 1e-3 * Math.PI / 180.0;

	/**
	 *
	 *
	 * @param x
	 * @param min
	 * @param max
	 * @return
	 */
	public static double clampEpsilon(final double x, final double min, final double max) {
		if (x < min + EPSILON) {
			return min;
		}
		if (x > max - EPSILON) {
			return max;
		}
		return x;
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isEqualHybrid(final double x, final double y) {
		return WB_Math.fastAbs(x - y) < WB_Epsilon.EPSILON * WB_Math.max(WB_Math.fastAbs(x), WB_Math.fastAbs(y), 1.0);
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isEqual(final double x, final double y) {
		return WB_Math.fastAbs(x - y) < WB_Epsilon.EPSILON;
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isEqualAbs(final double x, final double y) {
		return WB_Math.fastAbs(x - y) < WB_Epsilon.EPSILON;
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @param threshold
	 * @return
	 */
	public static boolean isEqualAbs(final double x, final double y, final double threshold) {
		return WB_Math.fastAbs(x - y) < threshold + WB_Epsilon.EPSILON;
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isEqualRel(final double x, final double y) {
		return WB_Math.fastAbs(x - y) < WB_Epsilon.EPSILON * WB_Math.max(WB_Math.fastAbs(x), WB_Math.fastAbs(y));
	}

	/**
	 *
	 *
	 * @param x
	 * @return
	 */
	public static boolean isZero(final double x) {
		return WB_Math.fastAbs(x) < WB_Epsilon.EPSILON;
	}

	/**
	 *
	 *
	 * @param x
	 * @return
	 */
	public static boolean isZeroSq(final double x) {
		return WB_Math.fastAbs(x) < WB_Epsilon.SQEPSILON;
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int compareHybrid(final double x, final double y) {
		if (isEqualHybrid(x, y)) {// x and y in range -epsilon, epsilon
			return 0;
		}
		if (x > y) {
			return 1;
		}
		return -1;
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int compareAbs(final double x, final double y) {
		if (isEqualAbs(x, y)) {// x and y in range -epsilon, epsilon
			return 0;
		}
		if (x > y) {
			return 1;
		}
		return -1;
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int compare(final double x, final double y) {
		if (isEqual(x, y)) {// x and y in range -epsilon, epsilon
			return 0;
		}
		if (x > y) {
			return 1;
		}
		return -1;
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int compareRel(final double x, final double y) {
		if (isEqualRel(x, y)) {// x and y in range -epsilon, epsilon
			return 0;
		}
		if (x > y) {
			return 1;
		}
		return -1;
	}
}
