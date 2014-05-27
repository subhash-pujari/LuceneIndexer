package com.evoapps.indexPubData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class MainLoop {

	
	private static final String databaseName = "acda_bfs";
	private static final int MAX_VALUE = 1;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseHandler handler = new DatabaseHandler(databaseName);
		IndexFiles indexer  = new IndexFiles();
		
		
		
		ArrayList<Publication> pubList = new ArrayList<Publication>();
	//	pubList = handler.getAllPublicationData();
		ArrayList<Publication> pubListCommunity;
		Iterator<Publication> it;
		
		ArrayList<Document> docList =  new ArrayList<Document>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("/home/subhash/Dropbox/git_repo/dataAnalysis/commFiles/nodeCommFilter.tsv")));
			String line;
			while((line=br.readLine())!=null){
				String[] tokens = line.split("\t");
				
				String id = tokens[0];
				String title = tokens[2];
				//System.out.println("id>>"+ id + "title>>" + title);
				Document doc = new Document(id, title);
				docList.add(doc);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		indexer.indexDocumentList(docList);
		
	}
	
	static ArrayList<Document> getDocumentList(ArrayList<Publication> pubList){
		ArrayList<Document> list = new ArrayList<Document>();
		
		Iterator<Publication> it = pubList.iterator();
		Publication pub;
		Document doc;
		while(it.hasNext()){
			pub = it.next();
			doc = new Document(String.valueOf(pub.getPublicationID()), pub.getTitle());
			System.out.println("docTitle>>"+doc.getContent());
			list.add(doc);
		}
		return list;
	}

	
	static void Log(String str){
		System.out.println(str);
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		str = timeStamp+" : "+str+"\n";
		File logFile = new File("log.txt");
		try {
			if(!logFile.exists()){
				logFile.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
