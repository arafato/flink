package org.apache.flink.streaming.connectors.eventhub.internals;

import org.apache.flink.streaming.connectors.eventhub.models.EventHubPartition;

import java.util.ArrayList;
import java.util.List;

public class EventHubDataFetcher {
	private List<EventHubPartition> subscribedPartitions = new ArrayList<>();

	private EventHubManager eventHubManager;
	private LeaseManager leaseManager;

	public EventHubDataFetcher(EventHubManager eventHubManager,
							   LeaseManager leaseManager) {
		this.eventHubManager = eventHubManager;
		this.leaseManager = leaseManager;
		this.populateSubscribedPartitions();
	}
	
	private void populateSubscribedPartitions() {
		for (EventHubPartition partition: this.eventHubManager.getAllPartitions()) {
			if (this.leaseManager.shouldThisSubtaskSubscribeTo(partition)) {
				this.subscribedPartitions.add(partition);
			}
		}
	}
}
