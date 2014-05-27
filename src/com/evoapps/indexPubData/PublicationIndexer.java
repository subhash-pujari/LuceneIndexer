package com.evoapps.indexPubData;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class PublicationIndexer {

		//Index directory where to store the indexes
		private Directory dir;
		//Analyzer object to anayze the text input and remove stop words
		private Analyzer analyzer;
		
		//Configuration object for IndexWriter
		private IndexWriterConfig iwc;
		
		private IndexWriter writer;
	
		private static final String INDEX_PATH = "/home/subhash/LuceneFolder/PublicationIndex";
		
		public PublicationIndexer() {
			// TODO Auto-generated constructor stub
			  try {
				dir = FSDirectory.open(new File(INDEX_PATH));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		      analyzer = new StandardAnalyzer(Version.LUCENE_40);
		      
		      
		      //writer for the index
		     
		      
		}
		
		boolean indexPublication(Publication publication){
			
			 try {
				 	iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
			      
			      //configure to add index to existing index
				 	iwc.setOpenMode(OpenMode.CREATE);
				 	writer = new IndexWriter(dir, iwc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			Document doc  = new Document();
			
			//field for id
			Field id = new StringField("id", String.valueOf(publication.getPublicationID()), Field.Store.YES);
			
			//field for title
			Field title = new TextField("title", publication.getTitle(), Field.Store.YES);
			
			//field for abstract
			Field Abstract = new StringField("abstract", publication.getAbstract(), Field.Store.NO);
			
			//add all the fields to document
			doc.add(id);
			doc.add(title);
			doc.add(Abstract);
			
			try {
				System.out.println("id>>"+publication.getPublicationID()+
			"title>>"+ publication.getTitle()+
			"abstract>>"+publication.getAbstract());
				writer.addDocument(doc);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return false;
		}

		void indexTest(String title, String content){
			try {
			 	iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		      
		      //configure to add index to existing index
			 	iwc.setOpenMode(OpenMode.CREATE);
			 	writer = new IndexWriter(dir, iwc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Document doc  = new Document();
			
			//field for id
//			Field id = new StringField("id", String.valueOf(publication.getPublicationID()), Field.Store.YES);
			
			//field for title
			Field titleIndex = new TextField("title", title, Field.Store.YES);
			
			//field for abstract
			Field contentIndex = new StringField("content", content, Field.Store.NO);
			
			//add all the fields to document
			doc.add(titleIndex);
			doc.add(contentIndex);
			
			try {
				System.out.println("title>>"+ title+"content>>"+content);
				writer.addDocument(doc);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		@Override
		protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
			super.finalize();
			writer.close();
		}
}
