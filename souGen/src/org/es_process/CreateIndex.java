package org.es_process;

import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import org.Frame.Frame_log;

/**
 * index只需要创建一次，如果index 创建的话不要再次调用
 */
public class CreateIndex {
	private static String INDEX_NAME = "";
	private static String TYPE = "";
	private static String FOLDER_NAME = "";
	private static String IP_ADDRESS = "";
	private static String PORT = "";

	public CreateIndex(String index_name, String type, String folder_name,
			String ip_adress, String port) {
		INDEX_NAME = index_name;
		TYPE = type;
		FOLDER_NAME = folder_name;
		IP_ADDRESS = ip_adress;
		PORT = port;
	}

	public static void create() throws IOException {
		Frame_log log = new Frame_log();
		ElasticSearchHandler esHandler = new ElasticSearchHandler(IP_ADDRESS,
				Integer.valueOf(PORT), FOLDER_NAME);
		log.append("Start create Index\n");
//		System.out.println("Start create Index");
		esHandler.createIndexResponse(INDEX_NAME);
		log.append("create Index over. Start mapping\n");
//		System.out.println("create Index over. Start mapping");
		esHandler.createMapping(INDEX_NAME, TYPE);
		log.append("Mapping over, Start add Documents to index\n");
//		System.out.println("Mapping over, Start add Documents to index");
		esHandler.addDoc(INDEX_NAME, TYPE , log);
		log.append("add Documents over\n");
//		System.out.println("add Documents over");

	}
}
