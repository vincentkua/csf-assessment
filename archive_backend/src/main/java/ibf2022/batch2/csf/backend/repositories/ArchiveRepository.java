package ibf2022.batch2.csf.backend.repositories;

import java.util.Date;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Archive;

@Repository
public class ArchiveRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object recordBundle(Archive newarchive) {

		String bundleId = UUID.randomUUID().toString().substring(0, 8);

		Date datenow = new Date();
		Document toinsert = new Document(); 
		toinsert.put("bundleId", bundleId); 
		toinsert.put("date", datenow); 
		toinsert.put("title", newarchive.getTitle()); 
		toinsert.put("name", newarchive.getName()); 
		toinsert.put("comments", newarchive.getComments()); 
		toinsert.put("url", newarchive.getUrls()); 
		Document newDoc = mongoTemplate.insert(toinsert, "archieves"); 
		System.out.println("New Archive Inserted: " + newDoc); 		
		return bundleId;
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundleByBundleId(/* any number of parameters here */) {
		return null;
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundles(/* any number of parameters here */) {
		return null;
	}


}
