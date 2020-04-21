package wblut.hemesh;

import wblut.geom.WB_Coord;
import wblut.geom.WB_Line;
import wblut.geom.WB_Point;
import wblut.geom.WB_Polygon;
import wblut.geom.WB_Vector;

public class HEC_RevolvePolygon extends HEC_Creator {
	private WB_Polygon polygon;
	private WB_Line axis;
	private int facets;

	public HEC_RevolvePolygon() {
		super();
		setOverride(true);
		facets = 6;
	}

	public HEC_RevolvePolygon setPolygon(final WB_Polygon poly) {
		polygon = poly;
		return this;
	}

	public HEC_RevolvePolygon setAxisOfRevolution(final WB_Coord p, final WB_Coord v) {
		axis = new WB_Line(p, v);
		return this;
	}

	public HEC_RevolvePolygon setAxisOfRevolution(final double ox, final double oy, final double oz, final double vx,
			final double vy, final double vz) {
		axis = new WB_Line(new WB_Point(ox, oy, oz), new WB_Vector(vx, vy, vz));
		return this;
	}

	public HEC_RevolvePolygon setFacets(final int n) {
		facets = n;
		return this;
	}

	@Override
	protected HE_Mesh createBase() {
		if (polygon == null || axis == null) {
			return null;
		}
		final int n = polygon.getNumberOfPoints();
		final WB_Point[] points = new WB_Point[n * facets];
		final double da = 2 * Math.PI / facets;
		int id = 0;
		for (int a = 0; a < facets; a++) {
			for (int i = 0; i < n; i++) {
				points[id] = polygon.getPoint(i).copy();
				points[id].rotateAboutAxisSelf(a * da, axis.getOrigin(), axis.getDirection());
				id++;
			}
		}
		int[][] faces;
		faces = new int[n * facets][];
		id = 0;
		for (int a = 0; a < facets; a++) {
			for (int i = 0; i < n; i++) {
				faces[id] = new int[4];
				faces[id][0] = a * n + (i + 1) % n;
				faces[id][1] = a * n + i;
				faces[id][2] = n * ((a + 1) % facets) + i;
				faces[id][3] = n * ((a + 1) % facets) + (i + 1) % n;
				id++;
			}
		}
		final HEC_FromFacelist fl = new HEC_FromFacelist();
		fl.setVertices(points).setFaces(faces).setCheckDuplicateVertices(false).setCheckNormals(false);
		return fl.createBase();
	}
}