package wblut.hemesh;

/**
 *
 */
public class HEM_CenterSplit extends HEM_Modifier {
	/**  */
	private double d;
	/**  */
	private double c;
	/**  */
	boolean relative;

	/**
	 *
	 */
	public HEM_CenterSplit() {
		super();
		d = 0;
		c = 0.5;
		relative = true;
	}

	/**
	 *
	 *
	 * @param d
	 * @return
	 */
	public HEM_CenterSplit setOffset(final double d) {
		this.d = d;
		return this;
	}

	/**
	 *
	 *
	 * @param c
	 * @return
	 */
	public HEM_CenterSplit setChamfer(final double c) {
		this.c = c;
		return this;
	}

	/**
	 *
	 *
	 * @param b
	 * @return
	 */
	public HEM_CenterSplit setRelative(final boolean b) {
		this.relative = b;
		return this;
	}

	/**
	 *
	 *
	 * @param mesh
	 * @return
	 */
	@Override
	protected HE_Mesh applySelf(final HE_Mesh mesh) {
		tracker.setStartStatus(this, "Starting HEC_CenterSplit.");
		final HEM_Extrude ext = new HEM_Extrude().setChamfer(c).setDistance(d).setRelative(relative);
		mesh.modify(ext);
		tracker.setStopStatus(this, "Exiting HEC_CenterSplit.");
		mesh.renameSelection("extruded", "center");
		return mesh;
	}

	/**
	 *
	 *
	 * @param selection
	 * @return
	 */
	@Override
	protected HE_Mesh applySelf(final HE_Selection selection) {
		tracker.setStartStatus(this, "Starting HEC_CenterSplit.");
		final HEM_Extrude ext = new HEM_Extrude().setChamfer(c).setDistance(d).setRelative(relative);
		selection.modify(ext);
		tracker.setStopStatus(this, "Exiting HEC_CenterSplit.");
		selection.getParent().renameSelection("extruded", "center");
		return selection.getParent();
	}
}
