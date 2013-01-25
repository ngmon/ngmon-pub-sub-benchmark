package cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Attribute;
import cz.muni.fi.publishsubscribe.matchingtree.Event;

public class EventAdapter extends Event implements
		cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event {

	@Override
	public void addAttribute(Attribute<? extends Comparable<?>> attribute) {
		super.putAttribute(attribute.getMatchingTreeAttribute());
	}

	@Override
	public Event getMatchingTreeEvent() {
		return this;
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Event getCountingTreeEvent() {
		throw new UnsupportedOperationException();
	}

}
