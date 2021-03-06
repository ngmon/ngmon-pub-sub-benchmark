package cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces;

import siena.FilterList;
import cz.muni.fi.publishsubscribe.matchingtree.Subscription;

public interface Predicate {
	
	public void addFilter(Filter filter);
	
	public Subscription getMatchingTreeSubscription();
	public cz.muni.fi.publishsubscribe.countingtree.Predicate getCountingTreePredicate();
	public FilterList getSienaFilterList();

}
