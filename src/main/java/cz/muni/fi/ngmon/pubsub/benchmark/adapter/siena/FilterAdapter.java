package cz.muni.fi.ngmon.pubsub.benchmark.adapter.siena;

import java.util.List;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;

public class FilterAdapter extends siena.Filter implements Filter {

	private static final long serialVersionUID = 1L;

	@Override
	public <T1 extends Comparable<T1>, T2 extends Constraint<T1>> boolean addConstraint(
			T2 constraint) {
		super.addConstraint(constraint.getAttributeName(),
				constraint.getSienaAttributeConstraint());
		return true;
	}

	@Override
	public List<Constraint<? extends Comparable<?>>> getConstraints() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Filter getCountingTreeFilter() {
		throw new UnsupportedOperationException();
	}

	@Override
	public siena.Filter getSienaFilter() {
		return this;
	}

}
