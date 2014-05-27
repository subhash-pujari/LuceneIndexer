package com.evoapps.indexPubData;

public class Document {

	
	private String title;
	private String content;
	/**
	 * @param args
	 */
	public Document(String title, String content){
		this.setTitle(title);
		this.setContent(content);
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
