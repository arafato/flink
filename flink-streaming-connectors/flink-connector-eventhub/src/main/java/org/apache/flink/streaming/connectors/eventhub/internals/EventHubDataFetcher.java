package org.apache.flink.streaming.connectors.eventhub.internals;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.eventhub.models.EventHubPartition;

import java.util.ArrayList;
import java.util.List;

public class EventHubDataFetcher<T> {
	private List<EventHubPartition> subscribedPartitions = new ArrayList<>();

	private final SourceFunction.SourceContext<T> sourceContext;

	/** Checkpoint lock, also used to synchronize operations on subscribedShardsState */
	private final Object checkpointLock;

	private EventHubManager eventHubManager;
	private LeaseManager leaseManager;

	public EventHubDataFetcher(EventHubManager eventHubManager,
							   LeaseManager leaseManager,
							   SourceFunction.SourceContext sourceContext) {
		this(eventHubManager,
			 leaseManager,
			 sourceContext,
			 sourceContext.getCheckpointLock());
	}

	public EventHubDataFetcher(EventHubManager eventHubManager,
							   LeaseManager leaseManager,
							   SourceFunction.SourceContext sourceContext,
							   Object checkpointLock) {
		this.eventHubManager = eventHubManager;
		this.leaseManager = leaseManager;
		this.sourceContext = sourceContext;
		this.checkpointLock = checkpointLock;
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
