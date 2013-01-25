package cz.muni.fi.ngmon.pubsub.benchmark.adapter.siena;

import siena.FilterList;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;
import cz.muni.fi.publishsubscribe.matchingtree.Subscription;

public class PredicateAdapter extends FilterList implements Predicate {

	@Override
	public void addFilter(Filter filter) {
		super.add(filter.getSienaFilter());
	}

	@Override
	public Subscription getMatchingTreeSubscription() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Predicate getCountingTreePredicate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public FilterList getSienaFilterList() {
		return this;
	}

}
