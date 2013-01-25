package cz.muni.fi.ngmon.pubsub.benchmark.adapter.siena;

import siena.AttributeConstraint;
import siena.Op;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.Operator;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.publishsubscribe.matchingtree.Predicate;

public class ConstraintAdapter<T1 extends Comparable<T1>> implements
		Constraint<T1> {

	private String attributeName;
	private AttributeValue<T1> attributeValue;
	private short sienaOp;

	private siena.AttributeConstraint sienaAttributeConstraint;

	public ConstraintAdapter(String attributeName,
			AttributeValue<T1> attributeValue, Operator operator) {
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;

		switch (operator) {
		case EQUALS:
			sienaOp = Op.EQ;
			break;
		case GREATER_THAN:
			sienaOp = Op.GT;
			break;
		case GREATER_THAN_OR_EQUAL_TO:
			sienaOp = Op.GE;
			break;
		case LESS_THAN:
			sienaOp = Op.LT;
			break;
		case LESS_THAN_OR_EQUAL_TO:
			sienaOp = Op.LE;
			break;
		}

		this.sienaAttributeConstraint = new AttributeConstraint(sienaOp,
				attributeValue.getSienaAttributeValue());
	}

	@Override
	public Predicate<? extends Comparable<?>, ? extends Comparable<?>> getMatchingTreePredicate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Constraint<T1> getCountingTreeConstraint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AttributeConstraint getSienaAttributeConstraint() {
		return sienaAttributeConstraint;
	}

	@Override
	public String getAttributeName() {
		return attributeName;
	}

}
