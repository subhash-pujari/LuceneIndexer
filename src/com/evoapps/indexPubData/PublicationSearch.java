package com.evoapps.indexPubData;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class PublicationSearch {

	private static final String INDEX_PATH = "/home/subhash/LuceneFolder/Indexer";
	private static IndexReader reader;
	private static IndexSearcher search;
	private static Analyzer analyzer;
	private static QueryParser parser;
	private static String querytext  = "gangs";
	private static final String QUERY_FIELD = "contents";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			reader = DirectoryReader.open(FSDirectory.open(new File(INDEX_PATH)));
			search = new IndexSearcher(reader);
			parser = new QueryParser(Version.LUCENE_40, QUERY_FIELD, analyzer);
			Query query = parser.parse(querytext);
			TopDocs docs= search.search(query, 10);
			for ( ScoreDoc scoreDoc : docs.scoreDocs ) {
			    Document doc = search.doc( scoreDoc.doc );
			    // "FILE" is the field that recorded the original file indexed
			    System.out.println("title>>"+doc.get( "title" ));
			    // ...
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
