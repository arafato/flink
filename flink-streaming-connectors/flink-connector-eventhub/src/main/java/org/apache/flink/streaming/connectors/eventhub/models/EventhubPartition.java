package org.apache.flink.streaming.connectors.eventhub.models;

import java.io.Serializable;

import static org.apache.flink.util.Preconditions.checkNotNull;

public class EventhubPartition implements Serializable {
	private static final long serialVersionUID = 9142527677273214949L;
	private String eventhub;
	private int partitionId;
	private final int cachedHash;
	
	public EventhubPartition(String eventhub, int partitionId) {
		this.eventhub = checkNotNull(eventhub);
		this.partitionId = partitionId;
		
		int hash = 17;
		hash = 37 * hash + eventhub.hashCode();
		hash = 37 * hash + this.partitionId;
		this.cachedHash = hash;
	}
	
	public String getEventhubName() {
		return this.eventhub;
	}
	
	public int getPartitionId() {
		return this.partitionId;
	}
	
	@Override
	public String toString() {
		return "EventhubPartition{" +
				"eventhubName='" + this.eventhub + "'" +
				", partitionId='" + this.partitionId + "'}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventhubPartition)) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		EventhubPartition other = (EventhubPartition) obj;

		return this.eventhub.equals(other.getEventhubName()) && this.partitionId == other.getPartitionId();
	}
	
	@Override
	public int hashCode() {
		return this.cachedHash;
	}
}
