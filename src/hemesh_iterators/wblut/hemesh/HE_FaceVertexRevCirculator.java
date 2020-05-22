package wblut.hemesh;

import java.util.Iterator;

/**
 *
 */
public class HE_FaceVertexRevCirculator implements Iterator<HE_Vertex> {
	/**  */
	private final HE_Halfedge _start;
	/**  */
	private HE_Halfedge _current;

	/**
	 *
	 *
	 * @param f
	 */
	HE_FaceVertexRevCirculator(final HE_Face f) {
		_start = f.getHalfedge();
		_current = null;
	}

	/**
	 *
	 *
	 * @return
	 */
	@Override
	public boolean hasNext() {
		if (_start == null) {
			return false;
		}
		return _current == null || _current.getPrevInFace() != _start;
	}

	/**
	 *
	 *
	 * @return
	 */
	@Override
	public HE_Vertex next() {
		if (_current == null) {
			_current = _start;
		} else {
			_current = _current.getPrevInFace();
		}
		return _current.getVertex();
	}

	/**
	 *
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}