package cz.muni.fi.ngmon.pubsub.benchmark.adapter.matchingtree;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Attribute;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;

public class AttributeAdapter<T1 extends Comparable<T1>> implements
		Attribute<T1> {

	private cz.muni.fi.publishsubscribe.matchingtree.Attribute<T1> matchingTreeAttribute;

	public AttributeAdapter(String name, AttributeValue<T1> value) {
		matchingTreeAttribute = new cz.muni.fi.publishsubscribe.matchingtree.Attribute<T1>(
				name, value.getMatchingTreeAttributeValue());
	}

	@Override
	public String getName() {
		return matchingTreeAttribute.getName();
	}

	@Override
	public cz.muni.fi.publishsubscribe.matchingtree.Attribute<T1> getMatchingTreeAttribute() {
		return matchingTreeAttribute;
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Attribute<T1> getCountingTreeAttribute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AttributeValue<T1> getValue() {
		throw new UnsupportedOperationException();
	}

}
