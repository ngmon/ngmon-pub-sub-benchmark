package cz.muni.fi.ngmon.pubsub.benchmark.adapter.countingtree;

import java.util.List;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;

public class FilterAdapter implements Filter {

	private cz.muni.fi.publishsubscribe.countingtree.Filter countingTreeFilter = new cz.muni.fi.publishsubscribe.countingtree.Filter();

	@Override
	public <T1 extends Comparable<T1>, T2 extends Constraint<T1>> boolean addConstraint(
			T2 constraint) {
		return countingTreeFilter.addConstraint(constraint
				.getCountingTreeConstraint());
	}

	@Override
	public List<Constraint<? extends Comparable<?>>> getConstraints() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Filter getCountingTreeFilter() {
		return countingTreeFilter;
	}

}
