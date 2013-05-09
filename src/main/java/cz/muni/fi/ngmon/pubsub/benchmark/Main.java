package cz.muni.fi.ngmon.pubsub.benchmark;

import com.google.caliper.Runner;

public class Main {

	public static void main(String[] args) throws Exception {

		// System.out.println("test1");

		Runner runner = new Runner();

		/*-System.out.println();
		System.out.println("1000");
		System.out.println("1000");

		// com.google.caliper.Runner.main
		runner.run(new String[] {
				"cz.muni.fi.ngmon.pubsub.benchmark.TwelveLongAttributesLessThan",
				"-DPREDICATE_COUNT=1000", "-DEVENT_COUNT=1000" });

		System.out.println();
		System.out.println("1000");
		System.out.println("100");

		runner.run(new String[] {
				"cz.muni.fi.ngmon.pubsub.benchmark.TwelveLongAttributesLessThan",
				"-DPREDICATE_COUNT=1000", "-DEVENT_COUNT=100" });
		
		System.out.println();
		System.out.println("2000");
		System.out.println("100");

		runner.run(new String[] {
				"cz.muni.fi.ngmon.pubsub.benchmark.TwelveLongAttributesLessThan",
				"-DPREDICATE_COUNT=2000", "-DEVENT_COUNT=100" });
		
		System.out.println();
		System.out.println("4000");
		System.out.println("100");*/

		runner.run(new String[] {
				"cz.muni.fi.ngmon.pubsub.benchmark.TwelveLongAttributesLessThan",
				"-DPREDICATE_COUNT=4000", "-DEVENT_COUNT=100" });

		/*-com.google.caliper.Runner
				.main(new String[] {
						"cz.muni.fi.ngmon.pubsub.benchmark.TwelveLongAttributesLessThan",
						"-DPREDICATE_COUNT=4000", "-DEVENT_COUNT=100" });*/

		/*-TwelveLongAttributesLessThan benchmark = new TwelveLongAttributesLessThan();
		benchmark.setUpPublic();
		System.out.println("Before start");
		System.in.read();
		long timeStart = System.nanoTime();
		System.out.println("Start");
		benchmark.timeMatch_25(1);
		long timeEnd = System.nanoTime();
		System.out.println(UtilityMethods.getTime(timeEnd - timeStart));*/
		// benchmark.timeMatch_100(1);
	}

}
