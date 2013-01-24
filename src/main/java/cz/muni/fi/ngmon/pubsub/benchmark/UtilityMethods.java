package cz.muni.fi.ngmon.pubsub.benchmark;

public class UtilityMethods {

	public static Predicate createPredicateFromConstraint(
			Constraint<?> constraint) {
		Filter filter = new Filter();
		filter.addConstraint(constraint);
		Predicate predicate = new Predicate();
		predicate.addFilter(filter);

		return predicate;
	}

}
