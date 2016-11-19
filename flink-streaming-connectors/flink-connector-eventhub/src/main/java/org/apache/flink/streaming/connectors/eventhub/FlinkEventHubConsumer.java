/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.connectors.eventhub;

import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.connectors.eventhub.internals.LeaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.azure.eventprocessorhost.*;

/**
 * The Flink EventHub Consumer is an exactly-once parallel streaming data source that subscribes to multiple Azure Event Hubs
 * within the same Azure service region. Each subtask of the consumer is responsible for fetching data records from one Event Hub
 * by using the Event Host Processor provided by the Azure SDK. 
 *
 * @param <T> the type of data emitted
 */
public class FlinkEventHubConsumer<T> extends RichParallelSourceFunction<T> {
	
	private static final long serialVersionUID = 3592457170890288412L;
	
	private static final Logger LOG = LoggerFactory.getLogger(FlinkEventHubConsumer.class);

	@Override
	public void run(SourceContext<T> ctx) throws Exception {
		LeaseManager leaseManager = new LeaseManager();
		// We are using the built-in thread pool of EventProcessorHost that is also managed by it.
		ExecutorService executorService = (ExecutorService) FieldUtils.readDeclaredStaticField(EventProcessorHost.class, "executorService");
		leaseManager.initialize(executorService);
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void close() throws Exception {
		cancel();
		super.close();
	}
}
