package cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;

public class AttributeValueAdapter<T1 extends Comparable<T1>> implements
		AttributeValue<T1> {

	private cz.muni.fi.publishsubscribe.matchingtree.AttributeValue<T1> matchingTreeAttributeValue;

	public AttributeValueAdapter(T1 value, Class<T1> type) {
		matchingTreeAttributeValue = new cz.muni.fi.publishsubscribe.matchingtree.AttributeValue<T1>(
				value, type);
	}

	@Override
	public T1 getValue() {
		return matchingTreeAttributeValue.getValue();
	}

	@Override
	public Class<T1> getType() {
		return matchingTreeAttributeValue.getType();
	}

	@Override
	public cz.muni.fi.publishsubscribe.matchingtree.AttributeValue<T1> getMatchingTreeAttributeValue() {
		return matchingTreeAttributeValue;
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.AttributeValue<T1> getCountingTreeAttributeValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public siena.AttributeValue getSienaAttributeValue() {
		throw new UnsupportedOperationException();
	}

}
