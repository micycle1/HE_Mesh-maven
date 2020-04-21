package wblut.hemesh;

import java.util.Iterator;

public class HE_VertexFaceCirculator implements Iterator<HE_Face> {
	private final HE_Halfedge _start;
	private HE_Halfedge _current;

	HE_VertexFaceCirculator(final HE_Vertex v) {
		_start = v.getHalfedge();
		_current = null;
	}

	@Override
	public boolean hasNext() {
		if (_start == null) {
			return false;
		}
		return (_current == null || _current.getNextInVertex() != _start) && _start != null;
	}

	@Override
	public HE_Face next() {
		if (_current == null) {
			_current = _start;
		} else {
			_current = _current.getNextInVertex();
		}
		return _current.getFace();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}