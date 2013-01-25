package cz.muni.fi.ngmon.pubsub.benchmark.adapter.siena;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.Attribute;
import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;

public class AttributeAdapter<T1 extends Comparable<T1>> implements
		Attribute<T1> {

	private String name;
	private AttributeValue<T1> value;

	public AttributeAdapter(String name, AttributeValue<T1> value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	public AttributeValue<T1> getValue() {
		return value;
	}

	@Override
	public cz.muni.fi.publishsubscribe.matchingtree.Attribute<T1> getMatchingTreeAttribute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.Attribute<T1> getCountingTreeAttribute() {
		throw new UnsupportedOperationException();
	}

}
