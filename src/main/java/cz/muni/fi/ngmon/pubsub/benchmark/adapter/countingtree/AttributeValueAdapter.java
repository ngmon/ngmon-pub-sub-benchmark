package cz.muni.fi.ngmon.pubsub.benchmark.adapter.countingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;

public class AttributeValueAdapter<T1 extends Comparable<T1>> implements
		AttributeValue<T1> {

	private cz.muni.fi.publishsubscribe.countingtree.AttributeValue<T1> countingTreeAttributeValue;

	public AttributeValueAdapter(T1 value, Class<T1> type) {
		countingTreeAttributeValue = new cz.muni.fi.publishsubscribe.countingtree.AttributeValue<T1>(
				value, type);
	}

	@Override
	public T1 getValue() {
		return countingTreeAttributeValue.getValue();
	}

	@Override
	public Class<T1> getType() {
		return countingTreeAttributeValue.getType();
	}

	@Override
	public cz.muni.fi.publishsubscribe.matchingtree.AttributeValue<T1> getMatchingTreeAttributeValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.AttributeValue<T1> getCountingTreeAttributeValue() {
		return countingTreeAttributeValue;
	}

	@Override
	public siena.AttributeValue getSienaAttributeValue() {
		throw new UnsupportedOperationException();
	}

}
