package cz.muni.fi.ngmon.pubsub.benchmark.adapter.countingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;
import cz.muni.fi.publishsubscribe.matchingtree.Subscription;

public class PredicateAdapter implements Predicate {

	private cz.muni.fi.publishsubscribe.countingtree.Predicate countingTreePredicate = new cz.muni.fi.publishsubscribe.countingtree.Predicate();

	@Override
	public void addFilter(Filter filter) {
		countingTreePredicate.addFilter(filter.getCountingTreeFilter());
	}

	@Override
	public Subscription getMatchingTreeSubscription() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Predicate getCountingTreePredicate() {
		return countingTreePredicate;
	}

}
