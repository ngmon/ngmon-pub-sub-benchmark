package cz.muni.fi.ngmon.pubsub.benchmark.adapter.countingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Attribute;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;

public class AttributeAdapter<T1 extends Comparable<T1>> implements
		Attribute<T1> {

	private cz.muni.fi.publishsubscribe.countingtree.Attribute<T1> countingTreeAttribute;

	public AttributeAdapter(String name, AttributeValue<T1> value) {
		countingTreeAttribute = new cz.muni.fi.publishsubscribe.countingtree.Attribute<T1>(
				name, value.getCountingTreeAttributeValue());
	}

	@Override
	public String getName() {
		return countingTreeAttribute.getName();
	}

	@Override
	public cz.muni.fi.publishsubscribe.matchingtree.Attribute<T1> getMatchingTreeAttribute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Attribute<T1> getCountingTreeAttribute() {
		return countingTreeAttribute;
	}

}
