package id.smarta.krakatau.streamer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import id.smarta.krakatau.streamer.entity.TwitterRaw;
import id.smarta.krakatau.streamer.entity.TwitterStatus;

/**
 * 
 * @author X230
 *
 */
public class TwitterRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
    
    public void saveStatus(TwitterStatus entity, String collectionName) {
		mongoTemplate.save(entity, collectionName);
	}
	
    public void saveRaw(TwitterRaw entity, String collectionName) {
		mongoTemplate.save(entity);
	}
    
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
    
}
