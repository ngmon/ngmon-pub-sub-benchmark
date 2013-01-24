package cz.muni.fi.ngmon.pubsub.benchmark;

import java.util.ArrayList;
import java.util.List;

import com.google.caliper.SimpleBenchmark;

/**
 * Tests the speed of the subscribe() method.
 * The *DifferentPredicate() methods are slow, since each test run
 * adds a lot more subscriptions (the longer the test runs, the more
 * subscriptions there are in the tree).
 * In the *DifferentAttributes() methods the new subscription
 * (predicate) uses different attribute names that those which already
 * are in the tree
 * Every Predicate has one Filter, every Filter has 12 Constraints:
 * 3 Long, less than, 3 Long, greater than or equal to, 3 Long equals
 * and 3 String equals
 */
public class Subscribe extends SimpleBenchmark {

	private static final int SMALL_TREE_PREDICATE_COUNT = 100;
	private static final int LARGE_TREE_PREDICATE_COUNT = SMALL_TREE_PREDICATE_COUNT * 100;

	private Predicate benchmarkPredicate;
	private Predicate benchmarkPredicateAlt;

	private CountingTree emptyTree;
	private CountingTree smallTree;
	private CountingTree largeTree;

	private Predicate createPredicate(int offset) {
		return createPredicate(offset, null);
	}

	private Predicate createPredicate(int offset, String attributePrefix) {
		List<String> attributeNames = new ArrayList<String>();
		attributeNames.add("longAttr1");
		attributeNames.add("longAttr2");
		attributeNames.add("longAttr3");
		attributeNames.add("stringAttr1");
		if (attributePrefix != null) {
			for (int i = 0; i < attributeNames.size(); i++) {
				attributeNames.set(i, attributeNames.get(i) + attributePrefix);
			}
		}

		Filter filter = new Filter();
		for (int i = 0; i < 3; i++) {
			Constraint<Long> constraint = new Constraint<>(
					attributeNames.get(0) + i, new AttributeValue<>((long) (i + 1)
							* 1000000 + offset, Long.class), Operator.LESS_THAN);
			filter.addConstraint(constraint);
		}

		for (int i = 0; i < 3; i++) {
			Constraint<Long> constraint = new Constraint<>(
					attributeNames.get(1) + i, new AttributeValue<>((long) (i + 1)
							* 10000000 + offset, Long.class),
					Operator.GREATER_THAN_OR_EQUAL_TO);
			filter.addConstraint(constraint);
		}

		for (int i = 0; i < 3; i++) {
			Constraint<Long> constraint = new Constraint<>(
					attributeNames.get(2) + i, new AttributeValue<>((long) (i + 1)
							* 100000000 + offset, Long.class), Operator.EQUALS);
			filter.addConstraint(constraint);
		}

		for (int i = 0; i < 3; i++) {
			Constraint<String> constraint = new Constraint<>(
					attributeNames.get(3) + i, new AttributeValue<>(
							String.valueOf((i + 1) * 1000000000 + offset),
							String.class), Operator.EQUALS);
			filter.addConstraint(constraint);
		}

		Predicate predicate = new Predicate();
		predicate.addFilter(filter);

		return predicate;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.benchmarkPredicate = createPredicate(0);
		this.benchmarkPredicateAlt = createPredicate(0, "alt");

		emptyTree = new CountingTree();

		smallTree = new CountingTree();
		for (int i = 0; i < SMALL_TREE_PREDICATE_COUNT; i++) {
			smallTree.subscribe(createPredicate(i));
		}

		largeTree = new CountingTree();
		for (int i = 0; i < LARGE_TREE_PREDICATE_COUNT; i++) {
			largeTree.subscribe(createPredicate(i));
		}
	}

	public void timeSubscribeToEmptyTree(int reps) {
		for (int i = 0; i < reps; i++) {
			emptyTree.subscribe(benchmarkPredicate);
		}
	}

	public void timeSubscribeToEmptyTreeDifferentPredicate(int reps) {
		for (int i = 0; i < reps; i++) {
			emptyTree.subscribe(createPredicate(i, "time"));
		}
	}

	public void timeSubscribeToSmallTree(int reps) {
		for (int i = 0; i < reps; i++) {
			smallTree.subscribe(benchmarkPredicate);
		}
	}
	
	public void timeSubscribeToSmallTreeDifferentPredicate(int reps) {
		for (int i = 0; i < reps; i++) {
			smallTree.subscribe(createPredicate(i, "time"));
		}
	}

	public void timeSubscribeToSmallTreeDifferentAttributes(int reps) {
		for (int i = 0; i < reps; i++) {
			smallTree.subscribe(benchmarkPredicateAlt);
		}
	}

	public void timeSubscribeToLargeTree(int reps) {
		for (int i = 0; i < reps; i++) {
			largeTree.subscribe(benchmarkPredicate);
		}
	}

	public void timeSubscribeToLargeTreeDifferentAttributes(int reps) {
		for (int i = 0; i < reps; i++) {
			largeTree.subscribe(benchmarkPredicateAlt);
		}
	}
	
	public void timeSubscribeToLargeTreeDifferentPredicate(int reps) {
		for (int i = 0; i < reps; i++) {
			largeTree.subscribe(createPredicate(i, "time"));
		}
	}

}
