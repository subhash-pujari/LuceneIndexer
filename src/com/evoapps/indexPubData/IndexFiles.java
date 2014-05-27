package com.evoapps.indexPubData;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/** Index all text files under a directory.
 * <p>
 * This is a command-line application demonstrating simple Lucene indexing.
 * Run it with no command-line arguments for usage information.
 */
public class IndexFiles {
  
	IndexWriter writer;
	private static final String indexPath = "/home/subhash/Dropbox/LuceneFolder/IndexNewData";
	private boolean create = true;
	Directory dir;
    Analyzer analyzer;
    IndexWriterConfig iwc;
	public IndexFiles() {
		try {
			dir = FSDirectory.open(new File(indexPath));
			analyzer = new StandardAnalyzer(Version.LUCENE_40);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

  void indextitle(String id, String title){
	  
	  iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
	  if (create) {
	        // Create a new index in the directory, removing any
	        // previously indexed documents:
	        iwc.setOpenMode(OpenMode.CREATE);
	      } else {
	        // Add new documents to an existing index:
	        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	   }
		
	  try {
			writer = new IndexWriter(dir, iwc);
	  } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	  }
	  
	  Document doc = new Document();

      Field pathField = new StringField("path", id, Field.Store.YES);
      doc.add(pathField);
//      doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));
      //doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
      //check the role of UTF-8 string in the doc.add() method
      doc.add(new TextField("contents", title, Field.Store.YES));
     
      try {
    	  if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
    		  // New index, so we just add the document (no old document can be there):
    		  //System.out.println("adding " + file);
        
    		writer.addDocument(doc);
		
      	} else {
      		// Existing index (an old copy of this document may have been indexed) so 
      		// we use updateDocument instead to replace the old one matching the exact 
      		// path, if present:
      		//System.out.println("updating " + file);
      		writer.updateDocument(new Term("path", id), doc);
      		writer.close();
      	}
      } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
  } 
  
  public void indexDocumentList(ArrayList<com.evoapps.indexPubData.Document> list) {
	  	Iterator< com.evoapps.indexPubData.Document> it = list.iterator();
	  	com.evoapps.indexPubData.Document document;
	  	
	  	iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		  if (create) {
		        // Create a new index in the directory, removing any
		        // previously indexed documents:
		        iwc.setOpenMode(OpenMode.CREATE);
		      } else {
		        // Add new documents to an existing index:
		        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		   }
			
		  try {
				writer = new IndexWriter(dir, iwc);
		  } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
	  	
	  	while(it.hasNext()){
	  		document = it.next();
	  		 Document doc = new Document();

	  	      Field pathField = new StringField("path", document.getTitle(), Field.Store.YES);
	  	      doc.add(pathField);
//	  	      doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));
	  	      //doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
	  	      //check the role of UTF-8 string in the doc.add() method
	  	      System.out.println("path>>"+ document.getTitle() +"content>>"+ 
	  	    		  document.getContent());
	  	      doc.add(new TextField("contents", document.getContent(), Field.Store.YES));
	  	    
	  	     System.out.println("doc"+ doc.getFields()); 
	  	     try {
	  	      if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
	      		  // New index, so we just add the document (no old document can be there):
	      		System.out.println("adding " + document.getContent());
	          
	      		writer.addDocument(doc);
	  		
	        	} else {
	        		// Existing index (an old copy of this document may have been indexed) so 
	        		// we use updateDocument instead to replace the old one matching the exact 
	        		// path, if present:
	        		//System.out.println("updating " + file);
	        		writer.updateDocument(new Term("path", document.getTitle()), doc);
	        		writer.close();
	        	}
	  	     
	      	  
	  	      }catch(IOException e){
	  	    	  
	  	      }
	  	}
	  	
	  	
	  	try{
	  		writer.close();
	  	}catch(IOException e){}
	  	
  }
}