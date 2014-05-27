package com.evoapps.indexPubData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHandler {
		
		Connection connect;
		
		DatabaseHandler(String databaseName){
			try {
				connect = DriverManager
					      .getConnection("jdbc:mysql://localhost/"+databaseName+"?"
						          + "user=tiger&password=user@123&dontTrackOpenResources=true");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ArrayList<Publication> getAllPublicationData(){
			String query = "select ID, Title, Abstract from  Publication;";
			ResultSet set = null;
			Statement stmt;
			try {
				stmt = connect.createStatement();
				set = stmt.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(set != null){
				return convertToPublicationList(set);
			}else{
				return null;
			}
		}
		
		ArrayList<Publication> getDataForCommunity(int CommunityNumber){
			
			ArrayList<Publication> list = new ArrayList<Publication>();
			ResultSet set = null;
			String query = "select Publication.ID, Title, Abstract from Publication, CommunityPublication where Publication.ID = CommunityPublication.publication and CommunityPublication.community="+CommunityNumber+";";
			Statement stmt;
			try {
				stmt = connect.createStatement();
				set = stmt.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(set != null){
				return convertToPublicationList(set);
			}else{
				return null;
			}
		}

		ArrayList<Publication> convertToPublicationList(ResultSet set){
			
			ArrayList<Publication> pubList = new ArrayList<Publication>();
			try {
				while(set.next()){
					pubList.add(new Publication(set.getInt("ID"), set.getString("Title"), set.getString("Abstract")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return pubList;
		}
}
