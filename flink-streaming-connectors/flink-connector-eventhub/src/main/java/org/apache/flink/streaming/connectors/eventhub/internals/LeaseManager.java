package org.apache.flink.streaming.connectors.eventhub.internals;

import org.apache.commons.lang.NotImplementedException;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.connectors.eventhub.models.EventHubPartition;

import static org.apache.flink.util.Preconditions.checkNotNull;

public class LeaseManager {

	private RuntimeContext runtimeContext;
	
	private int indexOfThisConsumerSubtask;
	private int totalNumberOfParallelSubTasks;
	
	public LeaseManager(RuntimeContext runtimeContext) {
		this.runtimeContext = checkNotNull(runtimeContext);
		this.indexOfThisConsumerSubtask = runtimeContext.getIndexOfThisSubtask();
		this.totalNumberOfParallelSubTasks = runtimeContext.getNumberOfParallelSubtasks();
	}
	
	public boolean shouldThisSubtaskSubscribeTo(EventHubPartition partition) {
		return (Math.abs(partition.hashCode() % this.totalNumberOfParallelSubTasks)) == this.indexOfThisConsumerSubtask;
	}
}
