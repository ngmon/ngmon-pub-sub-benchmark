package cz.muni.fi.ngmon.pubsub.benchmark.adapter.siena;

import cz.muni.fi.ngmon.pubsub.benchmark.adapter.interfaces.AttributeValue;

public class AttributeValueAdapter<T1 extends Comparable<T1>> implements
		AttributeValue<T1> {
	
	private final T1 value;
	private final Class<T1> type;
	
	private siena.AttributeValue sienaAttributeValue;
	
	public AttributeValueAdapter(T1 value, Class<T1> type) {
		this.value = value;
		this.type = type;
		
		if (type.equals(Long.class)) {
			this.sienaAttributeValue = new siena.AttributeValue((Long) value);
		} else if (type.equals(String.class)) {
			this.sienaAttributeValue = new siena.AttributeValue((String) value);
		} else {
			throw new RuntimeException("invalid value type");
		}
	}

	@Override
	public T1 getValue() {
		return value;
	}

	@Override
	public Class<T1> getType() {
		return type;
	}

	@Override
	public cz.muni.fi.publishsubscribe.matchingtree.AttributeValue<T1> getMatchingTreeAttributeValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public cz.muni.fi.publishsubscribe.countingtree.AttributeValue<T1> getCountingTreeAttributeValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public siena.AttributeValue getSienaAttributeValue() {
		return sienaAttributeValue;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AttributeValue<?> that = (AttributeValue<?>) o;

		if (!type.equals(that.getType()))
			return false;
		if (value != null ? !value.equals(that.getValue()) : that.getValue() != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = type.hashCode();
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

}
