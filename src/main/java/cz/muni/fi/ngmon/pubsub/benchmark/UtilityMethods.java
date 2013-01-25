package cz.muni.fi.ngmon.pubsub.benchmark;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.AdapterFactory;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;

public class UtilityMethods {

	public static Predicate createPredicateFromConstraint(
			Constraint<?> constraint) {
		Filter filter = AdapterFactory.createFilter();
		filter.addConstraint(constraint);
		Predicate predicate = AdapterFactory.createPredicate();
		predicate.addFilter(filter);

		return predicate;
	}

}
