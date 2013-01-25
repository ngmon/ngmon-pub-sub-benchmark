package cz.muni.fi.ngmon.pubsub.benchmark.adapter.countingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.PublishSubscribeTree;
import cz.muni.fi.publishsubscribe.countingtree.CountingTree;

public class CountingTreeAdapter implements PublishSubscribeTree {

	private CountingTree countingTree = new CountingTree();

	@SuppressWarnings("deprecation")
	@Override
	public void subscribe(Predicate predicate) {
		countingTree.subscribe(predicate.getCountingTreePredicate());
	}

	@Override
	public void match(Event event) {
		countingTree.match(event.getCountingTreeEvent());
	}

}
