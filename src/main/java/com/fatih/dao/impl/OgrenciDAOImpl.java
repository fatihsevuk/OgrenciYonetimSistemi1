package com.fatih.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.*;

import javax.sql.DataSource;

import org.bson.Document;

import com.fatih.dao.OgrenciDAO;
import com.fatih.db.AccessMongo;
import com.fatih.model.Ogrenci;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class OgrenciDAOImpl implements OgrenciDAO{
	
	
	
	
	
	public OgrenciDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	
	public List<Ogrenci> ogrenciListele() throws Exception{
		
		List<Ogrenci> ogrenciList=new ArrayList<Ogrenci>();
		
		AccessMongo accessMongo=new AccessMongo();
		
		try {
			
			FindIterable<Document> result = accessMongo.getCollection("ogrenciler").find();
			
			 MongoCursor<Document> cursor = result.iterator();
			
			
			while(cursor.hasNext()){
				
				 Document document = cursor.next();	
				
				int id=document.getInteger("id");
				String ad=document.getString("ad");
				String soyad=document.getString("soyad");
				String email=document.getString("email");
				
				Ogrenci ogrenci=new Ogrenci(id, ad, soyad, email);
				
				ogrenciList.add(ogrenci);			
				
			}
			
			
			
			
					
			return ogrenciList;
			
		
		} 
		finally{
			accessMongo.closeMongoClient();
			
		}
		
		
		
	}
	
	

	public void ogrenciEkle(Ogrenci ogrenci) throws SQLException {
		AccessMongo accessMongo=new AccessMongo();
		
		try{
			FindIterable<Document> result = accessMongo.getCollection("ogrenciler").find();
			
			MongoCursor<Document> cursor = result.iterator();
			
			MongoCollection<Document> collection = accessMongo.getCollection("ogrenciler");
			
			Document document = new Document();
			document.put("ad", ogrenci.getAd());
			document.put("email", ogrenci.getEmail());
			document.put("soyad", ogrenci.getSoyad());
			document.put("_id", ogrenci.getId());
			
			collection.insertOne(document);
			
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		finally{
			accessMongo.closeMongoClient();
		}
			
		
		
	}

	public void ogrenciSil(int id) throws SQLException {
		
		AccessMongo accessMongo=new AccessMongo();
		
		try{
			
			
			
			int ogrenciId=id;
			
			accessMongo.getCollection("ogrenciler").deleteMany(new Document("_id",ogrenciId));
			
			
			
		}finally{
			accessMongo.closeMongoClient();
		}
		
		
		
		
		
	}

	public void ogrenciGuncelle(Ogrenci ogrenci) throws SQLException {
		
		AccessMongo accessMongo=new AccessMongo();
		
		try{
		accessMongo.getCollection("ogrenciler").updateMany(new Document("_id",ogrenci.getId()),
																		new Document("$set",new Document("_id",ogrenci.getId())).
																		append("$set", new Document("ad",ogrenci.getAd())).
																		append("$set", new Document("soyad",ogrenci.getSoyad())).
																		append("$set", new Document("email",ogrenci.getEmail())));
		
		
		}finally{
			accessMongo.closeMongoClient();
		}
	}


	public Ogrenci ogrenciGetir(String ogrenciId) throws Exception {
		Ogrenci ogrenci=null;
		
		AccessMongo accessMongo=new AccessMongo();
		
		
		int OgrenciId;
		
		try{
			OgrenciId=Integer.parseInt(ogrenciId);
			
			accessMongo.getCollection("ogrenciler");
			
			FindIterable<Document> result = accessMongo.getCollection("ogrenciler").find(new Document("_id",OgrenciId));
			
			MongoCursor<Document> cursor = result.iterator();
			
			while(cursor.hasNext()){
				
				
				Document document=cursor.next();
				
				int id=document.getInteger("_id");
				String ad=document.getString("ad");
				String soyad=document.getString("soyad");
				String email=document.getString("email");
				
				ogrenci=new Ogrenci(id, ad, soyad, email);
				
				
				
				
			}
			
			
			
			
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
				
				accessMongo.closeMongoClient();
			}
			
			return ogrenci;
			
		
		
	}

}
