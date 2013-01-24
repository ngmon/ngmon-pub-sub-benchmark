package cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;

public class FilterAdapter implements Filter {
	
	private List<Constraint<? extends Comparable<?>>> constraints = new ArrayList<>();

	@Override
	public <T1 extends Comparable<T1>, T2 extends Constraint<T1>> boolean addConstraint(
			T2 constraint) {
		return this.constraints.add(constraint);
	}
	
	@Override
	public List<Constraint<? extends Comparable<?>>> getConstraints() {
		return this.constraints;
	}

}
