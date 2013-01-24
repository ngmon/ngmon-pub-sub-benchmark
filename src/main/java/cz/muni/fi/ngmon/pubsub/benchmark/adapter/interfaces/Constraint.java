package cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces;

import cz.muni.fi.publishsubscribe.matchingtree.Predicate;

public interface Constraint<T1 extends Comparable<T1>> {
	
	public Predicate<? extends Comparable<?>, ? extends Comparable<?>> getMatchingTreePredicate();

}
