package id.smarta.krakatau.streamer.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TWITTER_RAW")
public class TwitterRaw implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5180578896127112624L;

	@Id
	private long tweetId;
	private String content;
	private String keywordGroup;

	public long getTweetId() {
		return tweetId;
	}
	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKeywordGroup() {
		return keywordGroup;
	}
	public void setKeywordGroup(String keywordGroup) {
		this.keywordGroup = keywordGroup;
	}

}
