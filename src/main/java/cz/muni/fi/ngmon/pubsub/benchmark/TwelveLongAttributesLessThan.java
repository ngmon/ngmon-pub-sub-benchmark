package cz.muni.fi.ngmon.pubsub.benchmark;

import com.google.caliper.SimpleBenchmark;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.AdapterFactory;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.Operator;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Filter;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.PublishSubscribeTree;

/**
 * 12 Long attributes, operator < every event has all of the 12 attributes,
 * "notMatchingEvent1" matches all attributes (Constraints) except one,
 * "notMatchingEvent2" matches no attribute (Constraint)
 */
public class TwelveLongAttributesLessThan extends SimpleBenchmark {

	private static final int ATTRIBUTE_VALUES_COUNT = 10000;
	private static final String LONG_ATTRIBUTE_NAME_PREFIX = "longAttribute";
	private static final int ATTRIBUTE_COUNT = 12;
	private static final long MIN_VALUE = 1000L;
	private static final long MAX_VALUE = 5000L;
	private static final long ALWAYS_MATCHING_VALUE = 0L;
	private static final long NEVER_MATCHING_VALUE = 100000000L;

	// must be smaller (or much bigger) than MAX_VALUE - MIN_VALUE, otherwise
	// timeMatch_*_real() benchmarks might not match the required ratio of the
	// Predicates
	private static final int PREDICATE_COUNT = 1000;
	private static final int EVENT_COUNT = 100;

	private PublishSubscribeTree tree;
	private Event matchingEvent;
	private Event notMatchingEvent1;
	private Event notMatchingEvent2;

	private Event matchingEvent25;
	private Event matchingEvent50;
	private Event matchingEvent75;

	// these events would match all predicates if only
	// one of the attributes would be different
	private Event matchingEvent25_2;
	private Event matchingEvent50_2;
	private Event matchingEvent75_2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.tree = AdapterFactory.createPublishSubscribeTree();

		long val = MIN_VALUE;
		for (int i = 0; i < PREDICATE_COUNT; i++) {

			Filter filter = AdapterFactory.createFilter();
			for (int j = 0; j < ATTRIBUTE_COUNT; j++) {
				Constraint<Long> constraint = AdapterFactory.createConstraint(
						LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
								.createAttributeValue(j * 10000 + val,
										Long.class), Operator.LESS_THAN);
				filter.addConstraint(constraint);
			}

			Predicate predicate = AdapterFactory.createPredicate();
			predicate.addFilter(filter);

			tree.subscribe(predicate);

			val = val >= MAX_VALUE ? MIN_VALUE : val + 1;

		}

		matchingEvent = AdapterFactory.createEvent();
		for (int j = 0; j < ATTRIBUTE_COUNT; j++) {
			matchingEvent.addAttribute(AdapterFactory.createAttribute(
					LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
							.createAttributeValue(ALWAYS_MATCHING_VALUE,
									Long.class)));
		}

		notMatchingEvent1 = AdapterFactory.createEvent();
		for (int j = 0; j < ATTRIBUTE_COUNT - 1; j++) {
			notMatchingEvent1.addAttribute(AdapterFactory.createAttribute(
					LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
							.createAttributeValue(ALWAYS_MATCHING_VALUE,
									Long.class)));
		}
		notMatchingEvent1.addAttribute(AdapterFactory.createAttribute(
				LONG_ATTRIBUTE_NAME_PREFIX + (ATTRIBUTE_COUNT - 1),
				AdapterFactory.createAttributeValue(NEVER_MATCHING_VALUE,
						Long.class)));

		notMatchingEvent2 = AdapterFactory.createEvent();
		for (int j = 0; j < ATTRIBUTE_COUNT; j++) {
			notMatchingEvent2.addAttribute(AdapterFactory.createAttribute(
					LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
							.createAttributeValue(NEVER_MATCHING_VALUE,
									Long.class)));
		}

		long valueFor25 = ((long) (PREDICATE_COUNT * 0.75)) + MIN_VALUE;
		matchingEvent25 = AdapterFactory.createEvent();
		for (int j = 0; j < ATTRIBUTE_COUNT; j++) {
			matchingEvent25
					.addAttribute(AdapterFactory.createAttribute(
							LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
									.createAttributeValue(j
											* ATTRIBUTE_VALUES_COUNT
											+ valueFor25, Long.class)));
		}

		long valueFor50 = ((long) (PREDICATE_COUNT * 0.5)) + MIN_VALUE;
		matchingEvent50 = AdapterFactory.createEvent();
		for (int j = 0; j < ATTRIBUTE_COUNT; j++) {
			matchingEvent50
					.addAttribute(AdapterFactory.createAttribute(
							LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
									.createAttributeValue(j
											* ATTRIBUTE_VALUES_COUNT
											+ valueFor50, Long.class)));
		}

		long valueFor75 = ((long) (PREDICATE_COUNT * 0.25)) + MIN_VALUE;
		matchingEvent75 = AdapterFactory.createEvent();
		for (int j = 0; j < ATTRIBUTE_COUNT; j++) {
			matchingEvent75
					.addAttribute(AdapterFactory.createAttribute(
							LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
									.createAttributeValue(j
											* ATTRIBUTE_VALUES_COUNT
											+ valueFor75, Long.class)));
		}

		matchingEvent25_2 = AdapterFactory.createEvent();
		matchingEvent50_2 = AdapterFactory.createEvent();
		matchingEvent75_2 = AdapterFactory.createEvent();
		for (int j = 0; j < ATTRIBUTE_COUNT; j++) {
			boolean half = (j == ATTRIBUTE_COUNT / 2);
			matchingEvent25_2.addAttribute(AdapterFactory.createAttribute(
					LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
							.createAttributeValue(half ? j
									* ATTRIBUTE_VALUES_COUNT + valueFor25
									: ALWAYS_MATCHING_VALUE, Long.class)));
			matchingEvent50_2.addAttribute(AdapterFactory.createAttribute(
					LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
							.createAttributeValue(half ? j
									* ATTRIBUTE_VALUES_COUNT + valueFor50
									: ALWAYS_MATCHING_VALUE, Long.class)));
			matchingEvent75_2.addAttribute(AdapterFactory.createAttribute(
					LONG_ATTRIBUTE_NAME_PREFIX + j, AdapterFactory
							.createAttributeValue(half ? j
									* ATTRIBUTE_VALUES_COUNT + valueFor75
									: ALWAYS_MATCHING_VALUE, Long.class)));
		}
	}

//	public void timeMatchAverage_25(int reps) {
//		for (int i = 0; i < reps; i++) {
//			for (int j = 0; j < EVENT_COUNT / 4; j++) {
//				tree.match(matchingEvent);
//				tree.match(notMatchingEvent1);
//				tree.match(notMatchingEvent1);
//				tree.match(notMatchingEvent1);
//			}
//		}
//	}
//
//	public void timeMatchAverage2_25(int reps) {
//		for (int i = 0; i < reps; i++) {
//			for (int j = 0; j < EVENT_COUNT / 4; j++) {
//				tree.match(matchingEvent);
//				tree.match(notMatchingEvent2);
//				tree.match(notMatchingEvent2);
//				tree.match(notMatchingEvent2);
//			}
//		}
//	}
//
//	public void timeMatchAverage_50(int reps) {
//		for (int i = 0; i < reps; i++) {
//			for (int j = 0; j < EVENT_COUNT / 4; j++) {
//				tree.match(matchingEvent);
//				tree.match(matchingEvent);
//				tree.match(notMatchingEvent1);
//				tree.match(notMatchingEvent1);
//			}
//		}
//	}
//
//	public void timeMatchAverage2_50(int reps) {
//		for (int i = 0; i < reps; i++) {
//			for (int j = 0; j < EVENT_COUNT / 4; j++) {
//				tree.match(matchingEvent);
//				tree.match(matchingEvent);
//				tree.match(notMatchingEvent2);
//				tree.match(notMatchingEvent2);
//			}
//		}
//	}
//
//	public void timeMatchAverage_75(int reps) {
//		for (int i = 0; i < reps; i++) {
//			for (int j = 0; j < EVENT_COUNT / 4; j++) {
//				tree.match(matchingEvent);
//				tree.match(matchingEvent);
//				tree.match(matchingEvent);
//				tree.match(notMatchingEvent1);
//			}
//		}
//	}
//
//	public void timeMatchAverage2_75(int reps) {
//		for (int i = 0; i < reps; i++) {
//			for (int j = 0; j < EVENT_COUNT / 4; j++) {
//				tree.match(matchingEvent);
//				tree.match(matchingEvent);
//				tree.match(matchingEvent);
//				tree.match(notMatchingEvent2);
//			}
//		}
//	}

	public void timeMatch_100(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(matchingEvent);
			}
		}
	}

	public void timeMatch_25(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(matchingEvent25);
			}
		}
	}

	public void timeMatch_50(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(matchingEvent50);
			}
		}
	}

	public void timeMatch_75(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(matchingEvent75);
			}
		}
	}

	public void timeMatch2_25(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(matchingEvent25_2);
			}
		}
	}

	public void timeMatch2_50(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(matchingEvent50_2);
			}
		}
	}

	public void timeMatch2_75(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(matchingEvent75_2);
			}
		}
	}

}
