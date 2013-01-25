package cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces;

public interface Attribute<T1 extends Comparable<T1>> {
	
	public String getName();
	public AttributeValue<T1> getValue();
	
	public cz.muni.fi.publishsubscribe.matchingtree.Attribute<T1> getMatchingTreeAttribute();
	public cz.muni.fi.publishsubscribe.countingtree.Attribute<T1> getCountingTreeAttribute();

}
