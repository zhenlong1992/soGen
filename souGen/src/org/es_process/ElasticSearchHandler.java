package org.es_process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.Frame.Frame_log;
import org.entity.Document;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.util.FileIO;

public class ElasticSearchHandler {

	private static Client client;
	private static String FOLDER_NAME = "";

	/**
	 * 构造函数。连接ES引擎
	 * 
	 * @param ipAddress
	 */
	public ElasticSearchHandler(String ipAddress, int port, String folder_name) {
		// 集群连接超时设置
		/*
		 * Settings settings =
		 * ImmutableSettings.settingsBuilder().put("client.transport.ping_timeout"
		 * , "10s").build(); client = new TransportClient(settings);
		 */
		// InetSocketTransportAddress() Parameter: ipAddress,Port
		client = new TransportClient()
				.addTransportAddress(new InetSocketTransportAddress(ipAddress,
						port));
		FOLDER_NAME = folder_name;
	}

	/**
	 * 
	 * 创建索引
	 */
	public void createIndexResponse(String indexname) {
		client.admin().indices().prepareCreate(indexname).execute().actionGet();

	}

	/**
	 * 
	 * @param indexname
	 * @param type
	 * @throws IOException
	 *             创建映射 有两个field 一个是url另一个是content
	 */
	public void createMapping(String indexname, String type) throws IOException {
		/**
		 * "mappings": { { "_all": { "analyzer": "ik", "auto_boost": true },
		 * "properties": { "content": { "include_in_all": true, "analyzer":
		 * "ik", "term_vector": "with_positions_offsets", "boost": 8, "type":
		 * "string" }, "url": { "include_in_all": true, "analyzer": "ik",
		 * "term_vector": "with_positions_offsets", "boost": 8, "type": "string"
		 * } } }
		 */
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject()
				.startObject(type).startObject("_all")
				.field("indexAnalyzer", "ik").field("searchAnalyzer", "ik")
				.field("term_vector", "no").field("store", "false").endObject()
				.startObject("properties").startObject("url")
				.field("type", "string").field("store", "no")
				.field("term_vector", "with_positions_offsets")
				.field("indexAnalyzer", "ik").field("searchAnalyzer", "ik")
				.field("include_in_all", "true").field("boost", 8).endObject()
				.startObject("content").field("type", "string")
				.field("store", "no")
				.field("term_vector", "with_positions_offsets")
				.field("indexAnalyzer", "ik").field("searchAnalyzer", "ik")
				.field("include_in_all", "true").field("boost", 8).endObject()
				.endObject().endObject().endObject();
		PutMappingRequest mappingRequest = Requests
				.putMappingRequest(indexname).type(type).source(mapping);
//		System.out.println(mappingRequest.toString());
		client.admin().indices().putMapping(mappingRequest).actionGet();
	}

	/**
	 * @param indexname
	 * @param type
	 * @throws IOException
	 *             添加文件到索引
	 */
	public void addDoc(String indexname, String type,Frame_log log) throws IOException {
		FileIO file = new FileIO();
		StringBuilder content = null;
		String url = null;
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		int docAmount = file.countDoc(FOLDER_NAME);
		for (int i = 1; i < docAmount; i++) {
			url = file.readURL(FOLDER_NAME + "doc" + i + ".txt");
			content = file.readContent(FOLDER_NAME + "doc" + i + ".txt");
			XContentBuilder builder = XContentFactory.jsonBuilder()
					.startObject().field("url", url).field("content", content)
					.endObject();
			bulkRequest = client.prepareBulk();
			bulkRequest
					.add(client.prepareIndex(indexname, type, i + "")
							.setSource(builder)).execute().actionGet();
			log.append(i+"/"+docAmount+"\n");
//			System.out.print(i+"/"+docAmount+"\n");
		}
		// BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		// if (bulkResponse.hasFailures()) {
		// System.out.println("bulk state : " + bulkResponse.hashCode());
		// }

	}

	/**
	 * 执行搜索
	 * 
	 * @param queryBuilder
	 * @param indexname
	 * @param type
	 * @return
	 */
	/*public List<Document> searcher(String query, String indexname, String type) {
		List<Document> list = new ArrayList<Document>();

		SearchResponse searchResponse = client.prepareSearch(indexname)
				.setTypes(type).setSearchType(SearchType.DEFAULT)
				.setQuery(QueryBuilders.queryString(query))
				.addHighlightedField("content").setSize(50).execute()
				.actionGet();

		SearchHits hits = searchResponse.getHits();
		System.out.println("查询到记录数=" + hits.getTotalHits());
		if (hits.getTotalHits() > 0) {
			for (SearchHit hit : hits) {
				Map<String, HighlightField> result = hit.highlightFields();
				// logger.info("A map of highlighted fields:{}", result);

				Integer id = (Integer) hit.getSource().get("id");
				String url = (String) hit.getSource().get("url");
				String content = (String) hit.getSource().get("content");
				// String content = result.get("content").toString();
				list.add(new Document(id, url, content));
			}
		}
		return list;
	}*/

}