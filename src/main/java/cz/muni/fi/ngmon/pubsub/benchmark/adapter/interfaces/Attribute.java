package cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces;

public interface Attribute<T1 extends Comparable<T1>> {
	
	public String getName();
	
	public cz.muni.fi.publishsubscribe.matchingtree.Attribute<T1> getMatchingTreeAttribute();

}
