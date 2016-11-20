package org.apache.flink.streaming.connectors.eventhub.internals;

import java.util.List;

import org.apache.flink.streaming.connectors.eventhub.models.EventHub;

public class EventHubManager {
	private final List<EventHub> eventHubs;

	public EventHubManager(List<EventHub> eventHubs) {
		this.eventHubs = eventHubs;
	}
}
