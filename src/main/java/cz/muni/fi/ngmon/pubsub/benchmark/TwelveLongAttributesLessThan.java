package cz.muni.fi.ngmon.pubsub.benchmark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.caliper.Param;
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

	private static final String RANDOM_FILE_NAME = "random";
	private static final String RANDOM_FILE_EXT = "txt";

	private static final int ATTRIBUTE_VALUES_COUNT = 10000;
	private static final String LONG_ATTRIBUTE_NAME_PREFIX = "longAttribute";
	private static final int ATTRIBUTE_COUNT = 12;
	private static final long MIN_VALUE = 1000L;
	private static final long MAX_VALUE = 5000L;
	private static final long ALWAYS_MATCHING_VALUE = 0L;
	private static final long NEVER_MATCHING_VALUE = 100000000L;

	private static final int SUBSCRIBE_CALLS = 1000;

	// must be smaller (or much bigger) than MAX_VALUE - MIN_VALUE, otherwise
	// timeMatch_*_real() benchmarks might not match the required ratio of the
	// Predicates
	@Param
	int PREDICATE_COUNT;
	@Param
	int EVENT_COUNT;

	private PublishSubscribeTree tree;
	private PublishSubscribeTree randomTree;
	private Event matchingEvent;
	/**
	 * This event is matched by almost all (11/12) constraints, but it's not
	 * matched by any filter and thus not matched by any predicate (because
	 * the last event attribute has too large a value to be matched)
	 */
	private Event notMatchingEvent1;
	/**
	 * This event is matched by no constraints/filters/predicates
	 * (the event attributes have too large values)
	 */
	private Event notMatchingEvent2;

	private Event matchingEvent25;
	private Event matchingEvent50;
	private Event matchingEvent75;

	// these events would match all predicates if only
	// one of the attributes would be different
	private Event matchingEvent25_2;
	private Event matchingEvent50_2;
	private Event matchingEvent75_2;

	private Constraint<Long> getConstraint(int attributeIndex, long val) {
		return AdapterFactory.createConstraint(
				LONG_ATTRIBUTE_NAME_PREFIX + attributeIndex,
				AdapterFactory.createAttributeValue(attributeIndex * ATTRIBUTE_VALUES_COUNT
						+ val, Long.class), Operator.LESS_THAN);
	}

	public void setUpPublic() throws Exception {
		setUp();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.tree = AdapterFactory.createPublishSubscribeTree();
		this.randomTree = AdapterFactory.createPublishSubscribeTree();

		List<Long> randomValues = getRandomValuesFromFile();
		Iterator<Long> randomIterator = randomValues.iterator();
		long val = MIN_VALUE;
		long randomVal = randomIterator.next();
		for (int i = 0; i < PREDICATE_COUNT; i++) {

			Filter filter = AdapterFactory.createFilter();
			Filter randomFilter = AdapterFactory.createFilter();
			for (int j = 0; j < ATTRIBUTE_COUNT; j++) {
				filter.addConstraint(getConstraint(j, val));
				randomFilter.addConstraint(getConstraint(j, randomVal));
			}

			Predicate predicate = AdapterFactory.createPredicate();
			predicate.addFilter(filter);
			Predicate randomPredicate = AdapterFactory.createPredicate();
			randomPredicate.addFilter(randomFilter);

			tree.subscribe(predicate);
			randomTree.subscribe(randomPredicate);

			val = val >= MAX_VALUE ? MIN_VALUE : val + 1;
			if (i + 1 < PREDICATE_COUNT)
				randomVal = randomIterator.next();
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

	private String getRandomFileName() {
		return RANDOM_FILE_NAME + "-" + String.valueOf(PREDICATE_COUNT) + "."
				+ RANDOM_FILE_EXT;
	}

	public void createFileWithRandomValues() throws IOException {
		long val = MIN_VALUE;
		List<Long> values = new ArrayList<>(PREDICATE_COUNT);
		for (int i = 0; i < PREDICATE_COUNT; i++) {
			values.add(val);
			val = val >= MAX_VALUE ? MIN_VALUE : val + 1;
		}
		Collections.shuffle(values);

		FileOutputStream fstream = new FileOutputStream(getRandomFileName());
		DataOutputStream out = new DataOutputStream(fstream);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
		for (Long value : values) {
			bw.write(value.toString());
			bw.write("\n");
		}
		bw.close();
	}

	private List<Long> getValuesFromFile() throws NumberFormatException,
			IOException {
		List<Long> values = new ArrayList<>(PREDICATE_COUNT);
		FileInputStream fstream = new FileInputStream(getRandomFileName());
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			values.add(Long.parseLong(strLine));
		}
		br.close();
		return values;
	}

	private List<Long> getRandomValuesFromFile() throws NumberFormatException,
			IOException {
		List<Long> values = new ArrayList<>();
		try {
			values = getValuesFromFile();
		} catch (FileNotFoundException e) {
		}
		if (values.size() != PREDICATE_COUNT) {
			createFileWithRandomValues();
			values = getValuesFromFile();
			if (values.size() != PREDICATE_COUNT) {
				throw new RuntimeException();
			}
		}

		return values;
	}

	// public void timeMatchAverage_25(int reps) {
	// for (int i = 0; i < reps; i++) {
	// for (int j = 0; j < EVENT_COUNT / 4; j++) {
	// tree.match(matchingEvent);
	// tree.match(notMatchingEvent1);
	// tree.match(notMatchingEvent1);
	// tree.match(notMatchingEvent1);
	// }
	// }
	// }
	//
	// public void timeMatchAverage2_25(int reps) {
	// for (int i = 0; i < reps; i++) {
	// for (int j = 0; j < EVENT_COUNT / 4; j++) {
	// tree.match(matchingEvent);
	// tree.match(notMatchingEvent2);
	// tree.match(notMatchingEvent2);
	// tree.match(notMatchingEvent2);
	// }
	// }
	// }
	//
	// public void timeMatchAverage_50(int reps) {
	// for (int i = 0; i < reps; i++) {
	// for (int j = 0; j < EVENT_COUNT / 4; j++) {
	// tree.match(matchingEvent);
	// tree.match(matchingEvent);
	// tree.match(notMatchingEvent1);
	// tree.match(notMatchingEvent1);
	// }
	// }
	// }
	//
	// public void timeMatchAverage2_50(int reps) {
	// for (int i = 0; i < reps; i++) {
	// for (int j = 0; j < EVENT_COUNT / 4; j++) {
	// tree.match(matchingEvent);
	// tree.match(matchingEvent);
	// tree.match(notMatchingEvent2);
	// tree.match(notMatchingEvent2);
	// }
	// }
	// }
	//
	// public void timeMatchAverage_75(int reps) {
	// for (int i = 0; i < reps; i++) {
	// for (int j = 0; j < EVENT_COUNT / 4; j++) {
	// tree.match(matchingEvent);
	// tree.match(matchingEvent);
	// tree.match(matchingEvent);
	// tree.match(notMatchingEvent1);
	// }
	// }
	// }
	//
	// public void timeMatchAverage2_75(int reps) {
	// for (int i = 0; i < reps; i++) {
	// for (int j = 0; j < EVENT_COUNT / 4; j++) {
	// tree.match(matchingEvent);
	// tree.match(matchingEvent);
	// tree.match(matchingEvent);
	// tree.match(notMatchingEvent2);
	// }
	// }
	// }

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

	public void timeMatch_25_random(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				randomTree.match(matchingEvent25);
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

	public void timeMatch_50_random(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				randomTree.match(matchingEvent50);
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

	public void timeMatch_75_random(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				randomTree.match(matchingEvent75);
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

	public void timeMatch2_25_random(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				randomTree.match(matchingEvent25_2);
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

	public void timeMatch2_50_random(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				randomTree.match(matchingEvent50_2);
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

	public void timeMatch2_75_random(int reps) {
		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				randomTree.match(matchingEvent75_2);
			}
		}
	}

	// This method doesn't use Caliper, since I don't know how
	// to customize the reps count to limit the number of subscribe()
	// calls and get the correct results

	/**
	 * Subscribe benchmark
	 * 
	 * @param subscribeToRandomTree
	 *            If the "tree" with randomly ordered predicates should be used
	 * @throws Exception
	 */
	public void subscribeBenchmark(boolean subscribeToRandomTree)
			throws Exception {
		setUp();

		// prepare the predicates first

		List<Predicate> predicates = new ArrayList<>(SUBSCRIBE_CALLS);
		long val1 = MIN_VALUE;
		long val2 = MAX_VALUE;
		for (int i = 0; i < SUBSCRIBE_CALLS / 2; i++) {
			Filter filter1 = AdapterFactory.createFilter();
			Filter filter2 = AdapterFactory.createFilter();
			for (int j = 0; j < ATTRIBUTE_COUNT; j++, val1++, val2++) {
				filter1.addConstraint(getConstraint(j, val1));
				filter2.addConstraint(getConstraint(j, val2));
			}

			Predicate predicate1 = AdapterFactory.createPredicate();
			predicate1.addFilter(filter1);
			Predicate predicate2 = AdapterFactory.createPredicate();
			predicate2.addFilter(filter2);

			predicates.add(predicate1);
			predicates.add(predicate2);
		}

		long timeBeg = System.nanoTime();

		for (Predicate predicate : predicates) {
			if (subscribeToRandomTree)
				randomTree.subscribe(predicate);
			else
				tree.subscribe(predicate);
		}

		long timeEnd = System.nanoTime();
		long timeDiff = timeEnd - timeBeg;

		System.out.println(SUBSCRIBE_CALLS + " subscribe() calls took: ");
		System.out.println(UtilityMethods.getTime(timeDiff));
		System.out.println("One subscribe() call took on average: ");
		System.out.println(UtilityMethods.getTime(timeDiff / SUBSCRIBE_CALLS));
	}

}
