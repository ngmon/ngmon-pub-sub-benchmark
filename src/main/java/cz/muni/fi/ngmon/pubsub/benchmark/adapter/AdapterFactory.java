package cz.muni.fi.ngmon.pubsub.benchmark.adapter;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Attribute;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.PublishSubscribeTree;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree.AttributeAdapter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree.AttributeValueAdapter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree.ConstraintAdapter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree.EventAdapter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree.FilterAdapter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree.MatchingTreeAdapter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree.PredicateAdapter;

public class AdapterFactory {

	private static final DataStructures dataStructure = DataStructures.MATCHING_TREE;

	public static <T1 extends Comparable<T1>> AttributeValue<T1> createAttributeValue(
			T1 value, Class<T1> type) {
		switch (dataStructure) {
		case MATCHING_TREE:
			return new AttributeValueAdapter<T1>(value, type);
		default:
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

	public static <T1 extends Comparable<T1>> Attribute<T1> createAttribute(
			String name, AttributeValue<T1> value) {
		switch (dataStructure) {
		case MATCHING_TREE:
			return new AttributeAdapter<T1>(name, value);
		default:
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

	public static <T1 extends Comparable<T1>> Constraint<T1> createConstraint(
			String attributeName, AttributeValue<T1> attributeValue,
			Operator operator) {
		switch (dataStructure) {
		case MATCHING_TREE:
			return new ConstraintAdapter<T1>(attributeName, attributeValue,
					operator);
		default:
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

	public static Predicate createPredicate() {
		switch (dataStructure) {
		case MATCHING_TREE:
			return new PredicateAdapter();
		default:
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

	public static Event createEvent() {
		switch (dataStructure) {
		case MATCHING_TREE:
			return new EventAdapter();
		default:
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

	public static PublishSubscribeTree createPublishSubscribeTree() {
		switch (dataStructure) {
		case MATCHING_TREE:
			return new MatchingTreeAdapter();
		default:
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

	public static Filter createFilter() {
		switch (dataStructure) {
		case MATCHING_TREE:
			return new FilterAdapter();
		default:
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

}
