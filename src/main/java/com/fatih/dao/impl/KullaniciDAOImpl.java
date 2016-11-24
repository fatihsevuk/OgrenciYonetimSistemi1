package com.fatih.dao.impl;



import org.bson.Document;

import com.fatih.dao.KullaniciDAO;
import com.fatih.db.AccessMongo;
import com.fatih.model.Kullanici;

import com.mongodb.MongoClient;
import com.mongodb.MongoServerException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class KullaniciDAOImpl implements KullaniciDAO {

	public KullaniciDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean kullaniciEkle(Kullanici kullanici) throws Exception {

		boolean isRegister = true;

		AccessMongo accessMongo = new AccessMongo();

		try {

			MongoCollection<Document> collection = accessMongo.getCollection("kullanicilar");

			Document document = new Document();
			document.put("kAdi", kullanici.getkAdi());
			document.put("kEmail", kullanici.getkEmail());
			document.put("kParola", kullanici.getkParola());

			try {
				collection.insertOne(document);

			} catch (MongoServerException e) {
				isRegister = false;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {

			accessMongo.closeMongoClient();
		}
		return isRegister;
	}

	private void baglantiSonlandir(MongoClient client) {
		try {

			if (client != null) {
				client.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean kullaniciGiris(String kAdi, String kParola) throws Exception {

		boolean isLogin = false;
		AccessMongo accessMongo = new AccessMongo();

		try {

			MongoCollection<Document> collection = accessMongo.getCollection("kullanicilar");

			FindIterable<Document> result = accessMongo.getCollection("kullanicilar").find();

			MongoCursor<Document> cursor = result.iterator();

			while (cursor.hasNext()) {

				Document document = cursor.next();

				String kullaniciAdi = document.getString("kAdi");
				String parola = document.getString("kParola");

				if (kullaniciAdi == kAdi && parola == kParola) {
					isLogin = true;
				} else {
					isLogin = false;
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return isLogin;

	}

}
