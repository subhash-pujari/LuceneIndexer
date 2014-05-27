package com.evoapps.indexPubData;

public class Publication {
		
		private int publicationID;
		private String title;
		private String Abstract;
		
		public Publication(int pubId, String title, String Abstract) {
			// TODO Auto-generated constructor stub
		
			this.setPublicationID(pubId);
			this.title = title;
			this.Abstract = Abstract;
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAbstract() {
			return Abstract;
		}
		public void setAbstract(String _abstract) {
			Abstract = _abstract;
		}

		public int getPublicationID() {
			return publicationID;
		}

		public void setPublicationID(int publicationID) {
			this.publicationID = publicationID;
		}
}
