package cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces;

public interface AttributeValue<T1 extends Comparable<T1>> {

	public T1 getValue();

	public Class<T1> getType();

	public cz.muni.fi.publishsubscribe.matchingtree.AttributeValue<T1> getMatchingTreeAttributeValue();

	public cz.muni.fi.publishsubscribe.countingtree.AttributeValue<T1> getCountingTreeAttributeValue();

	public siena.AttributeValue getSienaAttributeValue();

}
