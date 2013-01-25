package cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces;

import java.util.List;

public interface Filter {
	
	public <T1 extends Comparable<T1>, T2 extends Constraint<T1>> boolean addConstraint(
			T2 constraint);

	public List<Constraint<? extends Comparable<?>>> getConstraints();
	public cz.muni.fi.publishsubscribe.countingtree.Filter getCountingTreeFilter();

}
