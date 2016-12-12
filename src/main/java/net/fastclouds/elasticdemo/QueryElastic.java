package net.fastclouds.elasticdemo;

import java.net.InetAddress;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QueryElastic {

	private static Client getClient(String host, int port, String clusterName) {
		Client client = null;
		try {

			Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName)
					.put("client.transport.sniff", true).build();
			TransportClient transportClient = TransportClient.builder().settings(settings).build();
			transportClient = transportClient
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
			client = transportClient;
		} catch (Exception e) {
			System.out.println("Exception while trying to connect to ElasticSearch : " + e.getMessage());
		}
		return client;
	}

	private static Employee findEmployee(Client client, String name) {

		SearchResponse response = null;
		Employee employee=null;
		
		if (client != null) {
			response = client.prepareSearch().setIndices("elastic-demo")
					.setQuery(QueryBuilders.matchQuery("name", name)).execute().actionGet();
			if(response!=null && response.getHits().hits().length>0) {
				String employeeJson = response.getHits().hits()[0].getSourceAsString();
				ObjectMapper mapper = new ObjectMapper();
				try {
					employee = mapper.readValue(employeeJson, Employee.class);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}

		return employee;
	}

	public static void main(String[] args) {
		Client client = null;
		try {
			client = getClient("10.149.249.228", 9300, "elasticsearch");
			Employee e = findEmployee(client, "John");
			if(e!=null) {
				System.out.println("Found employee " + e.getName());
				System.out.println("Salary is " + e.getSalary());
			}

		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
}
