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

	public static String getTime(long ns) {
		StringBuilder sb = new StringBuilder();
		String timeString = String.valueOf(ns);
		sb.append(ns).append(" ns");
		if (timeString.length() >= 4) {
			sb.append(" = ");
			sb.append(timeString.substring(0, timeString.length() - 3)).append(".")
					.append(timeString.substring(timeString.length() - 3));
			sb.append(" us");
			if (timeString.length() >= 7) {
				sb.append(" = ");
				sb.append(timeString.substring(0, timeString.length() - 6)).append(".")
						.append(timeString.substring(timeString.length() - 6));
				sb.append(" ms");
			}
		}

		/*-sb.append(ns).append(" ns =~ ").append(ns / 1000).append(" us =~ ")
				.append(ns / 1000000).append(" ms");*/
		return sb.toString();
	}

}
