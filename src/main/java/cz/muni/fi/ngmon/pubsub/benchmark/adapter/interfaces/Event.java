package cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces;

public interface Event {

	public void addAttribute(Attribute<? extends Comparable<?>> attribute);
	
	public cz.muni.fi.publishsubscribe.matchingtree.Event getMatchingTreeEvent();

}
