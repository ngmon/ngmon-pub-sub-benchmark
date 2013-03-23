package cz.muni.fi.ngmon.pubsub.benchmark.adapter.countingtree;

import siena.Notification;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Attribute;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event;

public class EventAdapter extends
		cz.muni.fi.publishsubscribe.countingtree.EventImpl implements Event {

	@Override
	public void addAttribute(Attribute<? extends Comparable<?>> attribute) {
		super.addAttribute(attribute.getCountingTreeAttribute());
	}

	@Override
	public cz.muni.fi.publishsubscribe.matchingtree.Event getMatchingTreeEvent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Event getCountingTreeEvent() {
		return this;
	}

	@Override
	public Notification getSienaNotification() {
		throw new UnsupportedOperationException();
	}

}
