package cz.muni.fi.ngmon.pubsub.benchmark.adapter.siena;

import siena.fwd.MatchMessageHandler;
import siena.fwd.Message;

public class MyMatchMessageHandler implements MatchMessageHandler {

	@Override
	public boolean output(Object id, Message n) {
		return false;
	}

}
