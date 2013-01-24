package cz.muni.fi.ngmon.pubsub.benchmark;

public class Main {

	public static void main(String[] args) {
		com.google.caliper.Runner
				.main(new String[] { "cz.muni.fi.ngmon.pubsub.benchmark.TwelveLongAttributesLessThan" });
	}

}
