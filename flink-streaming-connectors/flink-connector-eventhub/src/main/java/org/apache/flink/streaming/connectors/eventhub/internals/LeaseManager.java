package org.apache.flink.streaming.connectors.eventhub.internals;

import org.apache.commons.lang.NotImplementedException;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.connectors.eventhub.models.EventHubPartition;

import static org.apache.flink.util.Preconditions.checkNotNull;

public class LeaseManager {

	private RuntimeContext runtimeContext;
	
	private int indexOfThisConsumerSubtask;
	private int numberOfParallelSubTasks;
	
	public LeaseManager(RuntimeContext runtimeContext) {
		this.runtimeContext = checkNotNull(runtimeContext);
		this.indexOfThisConsumerSubtask = runtimeContext.getIndexOfThisSubtask();
		this.numberOfParallelSubTasks = runtimeContext.getNumberOfParallelSubtasks();
	}
	
	public boolean ShouldThisSubtaskSubscribeTo(EventHubPartition partition) {
		throw new NotImplementedException();
	}
}
