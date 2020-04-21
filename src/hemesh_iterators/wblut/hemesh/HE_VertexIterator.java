package wblut.hemesh;

import java.util.Collection;
import java.util.Iterator;

public class HE_VertexIterator implements Iterator<HE_Vertex> {
	Iterator<HE_Vertex> _itr;

	HE_VertexIterator(final Collection<HE_Vertex> vertices) {
		_itr = vertices.iterator();
	}

	HE_VertexIterator(final HE_HalfedgeStructure mesh) {
		_itr = mesh.vItr();
	}

	@Override
	public boolean hasNext() {
		return _itr.hasNext();
	}

	@Override
	public HE_Vertex next() {
		return _itr.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}