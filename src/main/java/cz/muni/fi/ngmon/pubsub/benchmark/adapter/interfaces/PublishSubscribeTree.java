package cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces;

public interface PublishSubscribeTree {
	
	public void subscribe(Predicate predicate);
	public void match(Event event);

}
