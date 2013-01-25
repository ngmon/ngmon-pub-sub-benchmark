package cz.muni.fi.ngmon.pubsub.benchmark.adapter.countingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.Operator;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.publishsubscribe.matchingtree.Predicate;

public class ConstraintAdapter<T1 extends Comparable<T1>> implements
		Constraint<T1> {

	private cz.muni.fi.publishsubscribe.countingtree.Constraint<T1> countingTreeConstraint;

	private cz.muni.fi.publishsubscribe.countingtree.Operator operatorToCountingTreeOperator(
			Operator operator) {
		switch (operator) {
		case EQUALS:
			return cz.muni.fi.publishsubscribe.countingtree.Operator.EQUALS;
		case GREATER_THAN:
			return cz.muni.fi.publishsubscribe.countingtree.Operator.GREATER_THAN;
		case GREATER_THAN_OR_EQUAL_TO:
			return cz.muni.fi.publishsubscribe.countingtree.Operator.GREATER_THAN_OR_EQUAL_TO;
		case LESS_THAN:
			return cz.muni.fi.publishsubscribe.countingtree.Operator.LESS_THAN;
		case LESS_THAN_OR_EQUAL_TO:
			return cz.muni.fi.publishsubscribe.countingtree.Operator.LESS_THAN_OR_EQUAL_TO;
		}

		throw new IllegalArgumentException();
	}

	public ConstraintAdapter(String attributeName,
			AttributeValue<T1> attributeValue, Operator operator) {
		this.countingTreeConstraint = new cz.muni.fi.publishsubscribe.countingtree.Constraint<>(
				attributeName, attributeValue.getCountingTreeAttributeValue(),
				operatorToCountingTreeOperator(operator));
	}

	@Override
	public Predicate<? extends Comparable<?>, ? extends Comparable<?>> getMatchingTreePredicate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Constraint<T1> getCountingTreeConstraint() {
		return countingTreeConstraint;
	}

}
