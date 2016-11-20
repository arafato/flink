package org.apache.flink.streaming.connectors.eventhub.models;

import com.microsoft.azure.servicebus.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.SharedAccessSignatureTokenProvider;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventHub {
	private final ConnectionStringBuilder connectionString;
	private final String consumerGroup;
	private final String eventHubName;

	private List<EventHubPartition> partitions = new ArrayList<>();

	public EventHub(ConnectionStringBuilder connectionString,
					String consumerGroup) throws Exception {
		this.connectionString = connectionString;
		this.eventHubName = this.connectionString.getEntityPath();
		this.consumerGroup = consumerGroup;
		this.initializePartitionIds();
	}

	private void initializePartitionIds() throws Exception {
		try {
			String contentEncoding = StandardCharsets.UTF_8.name();
			URI namespaceUri = new URI("https", connectionString.getEndpoint().getHost(), null, null);
			String resourcePath = String.join("/",
				namespaceUri.toString(),
				this.connectionString.getEntityPath(),
				"consumergroups",
				this.consumerGroup,
				"partitions");

			final String authorizationToken = SharedAccessSignatureTokenProvider.generateSharedAccessSignature(
				this.connectionString.getSasKeyName(), this.connectionString.getSasKey(),
				resourcePath, Duration.ofMinutes(20));

			URLConnection connection = new URL(resourcePath).openConnection();
			connection.addRequestProperty("Authorization", authorizationToken);
			connection.setRequestProperty("Content-Type", "application/atom+xml;type=entry");
			connection.setRequestProperty("charset", contentEncoding);
			InputStream responseStream = connection.getInputStream();

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(responseStream);

			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList partitionIdsNodes = (NodeList) xpath.evaluate("//feed/entry/title", doc.getDocumentElement(), XPathConstants.NODESET);
			if (partitionIdsNodes.getLength() == 0) {
				throw new Exception("EventHub does not exist");
			}

			for (int partitionIndex = 0; partitionIndex < partitionIdsNodes.getLength(); partitionIndex++) {
				this.partitions.add(new EventHubPartition(this.eventHubName, Integer.parseInt(partitionIdsNodes.item(partitionIndex).getTextContent())));
			}
		} catch (Exception exception) {
			String errorMessage = String.format(Locale.US, "Encountered error while fetching the list of EventHub PartitionIds: %s", exception.getMessage());
			if (exception instanceof FileNotFoundException) {
				errorMessage = "Consumer group does not exist";
			}
			throw new Exception(errorMessage, exception);
		}
	}
}
