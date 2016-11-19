package org.apache.flink.streaming.connectors.eventhub.internals;

import java.util.HashMap;
import java.util.List;

import org.apache.flink.streaming.connectors.eventhub.models.EventhubPartition;

public class EventHubManager {
	private final List<String> eventhubs;
	
	private HashMap<String, List<EventhubPartition>> eventhubPartitions;
	
	public EventHubManager(List<String> eventhubs) {
		this.eventhubs = eventhubs;
	}
}
