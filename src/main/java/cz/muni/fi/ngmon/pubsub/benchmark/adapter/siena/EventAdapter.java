package cz.muni.fi.ngmon.pubsub.benchmark.adapter.siena;

import siena.Notification;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Attribute;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event;

public class EventAdapter extends siena.Notification implements Event {

	private static final long serialVersionUID = 1L;

	@Override
	public void addAttribute(Attribute<? extends Comparable<?>> attribute) {
		super.putAttribute(attribute.getName(), attribute.getValue()
				.getSienaAttributeValue());
	}

	@Override
	public cz.muni.fi.publishsubscribe.matchingtree.Event getMatchingTreeEvent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Event getCountingTreeEvent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Notification getSienaNotification() {
		return this;
	}

}
