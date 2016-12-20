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

	private static final String TWITTER_RETWEET = "TWITTER_RETWEET";
	private static final String TWITTER_STATUS = "TWITTER_STATUS";
	private static final String TWITTER_QUOTE = "TWITTER_QUOTE";
	
	@Autowired
	private MongoTemplate mongoTemplate;
    
    public void saveStatus(TwitterStatus entity) {
		mongoTemplate.save(entity, TWITTER_STATUS);
	}
	
    public void saveRetweet(TwitterStatus entity) {
		mongoTemplate.save(entity, TWITTER_RETWEET);
	}
	
    public void saveQuote(TwitterStatus entity) {
		mongoTemplate.save(entity, TWITTER_QUOTE);
	}

    public void saveRaw(TwitterRaw entity) {
		mongoTemplate.save(entity);
	}
    
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
    
}
