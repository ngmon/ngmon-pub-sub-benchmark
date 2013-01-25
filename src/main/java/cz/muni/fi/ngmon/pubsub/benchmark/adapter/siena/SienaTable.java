package cz.muni.fi.ngmon.pubsub.benchmark.adapter.siena;

import siena.fwd.BadConstraintException;
import siena.fwd.SFFTable;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Event;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Predicate;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.PublishSubscribeTree;

public class SienaTable implements PublishSubscribeTree {

	private SFFTable sffTable = new SFFTable();
	private MyMatchMessageHandler msgHandler = new MyMatchMessageHandler();

	@Override
	public void subscribe(Predicate predicate) {
		try {
			sffTable.ifconfig("foo", predicate.getSienaFilterList());
		} catch (BadConstraintException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void match(Event event) {
		sffTable.match(event.getSienaNotification(), msgHandler);
	}

}
