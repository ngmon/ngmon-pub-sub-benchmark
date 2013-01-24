package cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree;

import java.util.List;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;
import cz.muni.fi.publishsubscribe.matchingtree.Subscription;

public class PredicateAdapter implements Predicate {

	private Subscription matchingTreeSubscription;

	@Override
	public void addFilter(Filter filter) {
		List<Constraint<? extends Comparable<?>>> constraints = filter
				.getConstraints();
		for (Constraint<? extends Comparable<?>> constraint : constraints) {
			matchingTreeSubscription.addPredicate(constraint
					.getMatchingTreePredicate());
		}
	}

	public PredicateAdapter() {
		matchingTreeSubscription = new Subscription();
	}

	@Override
	public Subscription getMatchingTreeSubscription() {
		return matchingTreeSubscription;
	}

}
