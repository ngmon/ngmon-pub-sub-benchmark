package cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.PublishSubscribeTree;
import cz.muni.fi.publishsubscribe.matchingtree.MatchingTree;

public class MatchingTreeAdapter implements PublishSubscribeTree {

	private MatchingTree matchingTree = new MatchingTree();

	@Override
	public void subscribe(Predicate predicate) {
		matchingTree.preprocess(predicate.getMatchingTreeSubscription());
	}

	@Override
	public void match(Event event) {
		matchingTree.match(event.getMatchingTreeEvent());
	}

}
