package org.apache.flink.streaming.connectors.eventhub.internals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.flink.streaming.connectors.eventhub.models.EventHub;
import org.apache.flink.streaming.connectors.eventhub.models.EventHubPartition;

import static org.apache.flink.util.Preconditions.checkNotNull;

public class EventHubManager {
	private HashMap<String, EventHub> eventHubs;

	public EventHubManager() {}

	public EventHubManager(HashMap<String, EventHub> eventHubs) {
		this.eventHubs = eventHubs;
	}

	public void addEventHub(EventHub eventHub) {
		checkNotNull(eventHub);
		this.eventHubs.put(eventHub.getName(), eventHub);
	}

	public EventHub getEventHub(String eventHubName) {
		if(!this.eventHubs.containsKey(eventHubName)) {
			return null;
		}
		return this.eventHubs.get(eventHubName);
	}

	public List<EventHubPartition> getAllPartitions() {
		ArrayList<EventHubPartition> allPartitions = new ArrayList<>();
		for (EventHub eventHub: this.eventHubs.values()) {
			allPartitions.addAll(eventHub.getPartitions());
		}
		return allPartitions;
	}
}
