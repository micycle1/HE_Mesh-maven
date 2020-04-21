package wblut.geom;

public class WB_RandomInCylinder extends WB_RandomFactory {
	private double radius, height;

	public WB_RandomInCylinder() {
		super();
		radius = 1.0;
		height = 1.0;
	}

	public WB_RandomInCylinder(final long seed) {
		super(seed);
		radius = 1.0;
		height = 1.0;
	}

	public WB_RandomInCylinder setRadius(final double r) {
		radius = r;
		return this;
	}

	public WB_RandomInCylinder setHeight(final double h) {
		height = h;
		return this;
	}

	@Override
	public WB_Point nextPointImp() {
		final double r = radius * Math.sqrt(randomGen.nextDouble());
		final double t = 2 * Math.PI * randomGen.nextDouble();
		return new WB_Point(r * Math.cos(t), r * Math.sin(t), height * randomGen.nextCenteredDouble());
	}

	@Override
	public WB_Vector nextVectorImp() {
		final double r = radius * Math.sqrt(randomGen.nextDouble());
		final double t = 2 * Math.PI * randomGen.nextDouble();
		return new WB_Vector(r * Math.cos(t), r * Math.sin(t), height * randomGen.nextCenteredDouble());
	}
}