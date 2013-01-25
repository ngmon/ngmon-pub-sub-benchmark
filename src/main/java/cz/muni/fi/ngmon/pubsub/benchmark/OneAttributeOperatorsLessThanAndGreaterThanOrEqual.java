package cz.muni.fi.ngmon.pubsub.benchmark;

import java.util.ArrayList;
import java.util.List;

import com.google.caliper.SimpleBenchmark;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.AdapterFactory;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.Operator;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Constraint;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.PublishSubscribeTree;

/**
 * One attribute (of type Long), 25 % predicates matched, constraint operators
 * less than and greater than or equal to
 */
public class OneAttributeOperatorsLessThanAndGreaterThanOrEqual extends
		SimpleBenchmark {

	private static final String LONG_ATTRIBUTE_NAME = "longAttribute";

	private static final int PREDICATE_COUNT = 500;
	private static final int EVENT_COUNT = 10000;

	private static final long GREATER_THAN_OR_EQUAL_MIN_VALUE_25 = 100L;
	private static final long GREATER_THAN_OR_EQUAL_MAX_VALUE_25 = 200L;

	private static final long LESS_THAN_MIN_VALUE_25_50_75 = 0L;
	private static final long LESS_THAN_MAX_VALUE_25_50_75 = 99L;

	private static final long LESS_THAN_ONE_EVENT_MATCH_25_VALUE = 50L;
	private static final long GREATER_THAN_ONE_EVENT_MATCH_25_VALUE = 150L;

	private static final long GREATER_THAN_OR_EQUAL_MIN_VALUE_50 = 50L;
	private static final long GREATER_THAN_OR_EQUAL_MAX_VALUE_50 = 150L;

	private static final long GREATER_THAN_OR_EQUAL_MAX_VALUE_EVENTS_50 = 99L;

	private static final long GREATER_THAN_OR_EQUAL_MAX_VALUE_75 = 50L;

	private static final long MIN_VALUE_EVENT_100 = -100L;

	// should match 25 % of the less than predicates (0 - 99)
	// and 75 % of the greater than or equal to predicates (50 - 150)
	// => 50 % total
	private static final long MATCH_50_VALUE = 75;

	// should match 75 % of the less than predicates (0 - 99)
	// and 75 % of the greater than or equal to predicates (-50 - 50)
	private static final long MATCH_75_VALUE = 25;

	private static final long MATCH_100_VALUE = -50;

	/** Tree for 25 % match ratio */
	private PublishSubscribeTree tree25;

	private PublishSubscribeTree tree50;
	private PublishSubscribeTree tree75;
	private PublishSubscribeTree tree100;

	private List<Event> eventsFromLessThanMinValueToLessThanMaxValue25;
	private List<Event> eventsFromGreaterThanOrEqualMinValueToMaxValue25;

	private List<Event> eventsFor50;
	private List<Event> eventsFor75;
	private List<Event> eventsFor100;

	/**
	 * Half of the constraints/predicates have values between
	 * LESS_THAN_MIN_VALUE and LESS_THAN_MAX_VALUE and operator less than and
	 * the second half have values between GREATER_THAN_OR_EQUAL_MIN_VALUE and
	 * GREATER_THAN_OR_EQUAL_MAX_VALUE and operator greater than or equal
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.tree25 = setUpCustomTree(LESS_THAN_MIN_VALUE_25_50_75,
				LESS_THAN_MAX_VALUE_25_50_75,
				GREATER_THAN_OR_EQUAL_MIN_VALUE_25,
				GREATER_THAN_OR_EQUAL_MAX_VALUE_25);

		// events with values ranging from LESS_THAN_MIN_VALUE
		// to GREATER_THAN_OR_EQUAL_MAX_VALUE
		eventsFromLessThanMinValueToLessThanMaxValue25 = prepareEvents(
				LESS_THAN_MIN_VALUE_25_50_75, LESS_THAN_MAX_VALUE_25_50_75);
		eventsFromGreaterThanOrEqualMinValueToMaxValue25 = prepareEvents(
				GREATER_THAN_OR_EQUAL_MIN_VALUE_25,
				GREATER_THAN_OR_EQUAL_MAX_VALUE_25);

		this.tree50 = setUpCustomTree(LESS_THAN_MIN_VALUE_25_50_75,
				LESS_THAN_MAX_VALUE_25_50_75,
				GREATER_THAN_OR_EQUAL_MIN_VALUE_50,
				GREATER_THAN_OR_EQUAL_MAX_VALUE_50);

		eventsFor50 = prepareEvents(GREATER_THAN_OR_EQUAL_MIN_VALUE_50,
				GREATER_THAN_OR_EQUAL_MAX_VALUE_EVENTS_50);

		this.tree75 = AdapterFactory.createPublishSubscribeTree();
		int i = 0;
		while (i < PREDICATE_COUNT / 2) {
			long constraintValue = i % (LESS_THAN_MAX_VALUE_25_50_75 + 1);
			Constraint<Long> constraint = AdapterFactory.createConstraint(

			LONG_ATTRIBUTE_NAME, AdapterFactory.createAttributeValue(
					constraintValue - 50, Long.class),
					Operator.GREATER_THAN_OR_EQUAL_TO);
			tree75.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));
			constraint = AdapterFactory.createConstraint(LONG_ATTRIBUTE_NAME,
					AdapterFactory.createAttributeValue(constraintValue,
							Long.class), Operator.LESS_THAN);
			tree75.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));

			i++;
		}

		eventsFor75 = prepareEvents(LESS_THAN_MIN_VALUE_25_50_75,
				GREATER_THAN_OR_EQUAL_MAX_VALUE_75);

		this.tree100 = AdapterFactory.createPublishSubscribeTree();
		i = 0;
		while (i < PREDICATE_COUNT / 2) {
			long constraintValue = i % (LESS_THAN_MAX_VALUE_25_50_75 + 1);
			Constraint<Long> constraint = AdapterFactory.createConstraint(

			LONG_ATTRIBUTE_NAME, AdapterFactory.createAttributeValue(

			constraintValue - 200, Long.class),
					Operator.GREATER_THAN_OR_EQUAL_TO);
			tree100.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));
			constraint = AdapterFactory.createConstraint(LONG_ATTRIBUTE_NAME,
					AdapterFactory.createAttributeValue(constraintValue,
							Long.class), Operator.LESS_THAN);
			tree100.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));

			i++;
		}

		eventsFor100 = prepareEvents(MIN_VALUE_EVENT_100,
				LESS_THAN_MIN_VALUE_25_50_75);
	}

	private PublishSubscribeTree setUpCustomTree(long lessThanMinValue,
			long lessThanMaxValue, long greaterThanOrEqualMinValue,
			long greaterThanOrEqualMaxValue) {
		PublishSubscribeTree tree = AdapterFactory.createPublishSubscribeTree();
		int i = 0;
		while (i < PREDICATE_COUNT) {
			long constraintValue = i % (greaterThanOrEqualMaxValue + 1);
			Constraint<Long> constraint = AdapterFactory
					.createConstraint(

							LONG_ATTRIBUTE_NAME,
							AdapterFactory.createAttributeValue(
									constraintValue, Long.class),
							constraintValue > lessThanMaxValue ? Operator.GREATER_THAN_OR_EQUAL_TO
									: Operator.LESS_THAN);
			tree.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));

			i++;
		}

		return tree;
	}

	private List<Event> prepareEvents(long minValue, long maxValue) {
		List<Event> events = new ArrayList<>(EVENT_COUNT);
		for (long i = 0, value = minValue; i < EVENT_COUNT; i++) {
			Event event = AdapterFactory.createEvent();
			event.addAttribute(AdapterFactory.createAttribute(
					LONG_ATTRIBUTE_NAME,
					AdapterFactory.createAttributeValue(value, Long.class)));
			events.add(event);

			value = value >= maxValue ? minValue : value + 1;
		}

		return events;
	}

	/**
	 * Matches less than predicates only, one event (value in the middle between
	 * LESS_THAN_MIN_VALUE and LESS_THAN_MAX_VALUE
	 */
	public void timeMatchLessThanOneEvent_25(int reps) {
		Event event = AdapterFactory.createEvent();
		event.addAttribute(AdapterFactory.createAttribute(LONG_ATTRIBUTE_NAME,
				AdapterFactory.createAttributeValue(
						LESS_THAN_ONE_EVENT_MATCH_25_VALUE, Long.class)));

		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree25.match(event);
			}
		}
	}

	/**
	 * Matches greater than or equal to predicates only, one event (value in the
	 * middle between GREATER_THAN_OR_EQUAL_MIN_VALUE and
	 * GREATER_THAN_OR_EQUAL_MAX_VALUE)
	 */
	public void timeMatchGreaterThanOrEqualOneEvent_25(int reps) {
		Event event = AdapterFactory.createEvent();
		event.addAttribute(AdapterFactory.createAttribute(LONG_ATTRIBUTE_NAME,
				AdapterFactory.createAttributeValue(
						GREATER_THAN_ONE_EVENT_MATCH_25_VALUE, Long.class)));

		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree25.match(event);
			}
		}
	}

	/**
	 * Matches less than predicates only, events with values ranging from
	 * LESS_THAN_MIN_VALUE to LESS_THAN_MAX_VALUE
	 */
	public void timeMatchLessThanDifferentEventsLessThan_25(int reps) {
		for (int i = 0; i < reps; i++) {
			for (Event event : eventsFromLessThanMinValueToLessThanMaxValue25) {
				tree25.match(event);
			}
		}
	}

	/**
	 * Matches greater than or equal to predicates only, events with values
	 * ranging from GREATER_THAN_OR_EQUAL_MIN_VALUE to
	 * GREATER_THAN_OR_EQUAL_MAX_VALUE
	 */
	public void timeMatchGreaterThanOrEqualDifferentEventsGreaterThan_25(
			int reps) {
		for (int i = 0; i < reps; i++) {
			for (Event event : eventsFromGreaterThanOrEqualMinValueToMaxValue25) {
				tree25.match(event);
			}
		}
	}

	public void timeMatchOneEvent_50(int reps) {
		Event event = AdapterFactory.createEvent();
		event.addAttribute(AdapterFactory
				.createAttribute(LONG_ATTRIBUTE_NAME, AdapterFactory
						.createAttributeValue(MATCH_50_VALUE, Long.class)));

		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree50.match(event);
			}
		}
	}

	public void timeMatchDifferentEvents_50(int reps) {
		for (int i = 0; i < reps; i++) {
			for (Event event : eventsFor50) {
				tree50.match(event);
			}
		}
	}

	public void timeMatchOneEvent_75(int reps) {
		Event event = AdapterFactory.createEvent();
		event.addAttribute(AdapterFactory
				.createAttribute(LONG_ATTRIBUTE_NAME, AdapterFactory
						.createAttributeValue(MATCH_75_VALUE, Long.class)));

		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree75.match(event);
			}
		}
	}

	public void timeMatchDifferentEvents_75(int reps) {
		for (int i = 0; i < reps; i++) {
			for (Event event : eventsFor75) {
				tree75.match(event);
			}
		}
	}

	public void timeMatchOneEvent_100(int reps) {
		Event event = AdapterFactory.createEvent();
		event.addAttribute(AdapterFactory.createAttribute(LONG_ATTRIBUTE_NAME,
				AdapterFactory
						.createAttributeValue(MATCH_100_VALUE, Long.class)));

		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree100.match(event);
			}
		}
	}

	public void timeMatchDifferentEvents_100(int reps) {
		for (int i = 0; i < reps; i++) {
			for (Event event : eventsFor100) {
				tree100.match(event);
			}
		}
	}

}
