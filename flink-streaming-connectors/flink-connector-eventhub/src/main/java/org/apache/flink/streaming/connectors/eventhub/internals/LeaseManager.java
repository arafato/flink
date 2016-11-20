package org.apache.flink.streaming.connectors.eventhub.internals;

import org.apache.commons.lang.NotImplementedException;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.connectors.eventhub.models.EventHubPartition;

import static org.apache.flink.util.Preconditions.checkNotNull;

public class LeaseManager {

	private RuntimeContext runtimeContext;
	
	private int indexOfThisConsumerSubtask;
	
	public LeaseManager(RuntimeContext runtimeContext) {
		this.runtimeContext = checkNotNull(runtimeContext);
		this.indexOfThisConsumerSubtask = runtimeContext.getIndexOfThisSubtask();
	}
	
	public boolean ShouldThisSubtaskSubscribeTo(EventHubPartition partition,
												int totalNumberOfConsumerSubtasks,
												int indexOfThisConsumerSubtask) {
		throw new NotImplementedException();
	}
}
