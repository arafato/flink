package org.apache.flink.streaming.connectors.eventhub.internals;

import java.util.HashMap;

import org.apache.flink.streaming.connectors.eventhub.models.EventHub;

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
}
