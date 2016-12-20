package id.smarta.krakatau.streamer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TwitterStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7116681817659459579L;

	@Id
	private long tweetId;
	private long originId;
	private String text;
	private String source;
	private boolean isFavorited;
	private boolean isRetweet;
	private boolean isRetweeted;
	private int favoritedCount;
	private int retweetCount;
	private long userId;
	private String userScreenName;
	private String replyToScreenName;
	private long replyToUserId;
	private long replyToStatusId;
	private long quotedStatusId;
	private Date createdAt;
	private List<String> hashTags =  new ArrayList<String>();
	private List<String> mentions = new ArrayList<String>();
	private List<String> twitterUrls = new ArrayList<String>(); 
	private String keywordGroup;
	
	public long getTweetId() {
		return tweetId;
	}
	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isFavorited() {
		return isFavorited;
	}
	public void setFavorited(boolean isFavorited) {
		this.isFavorited = isFavorited;
	}
	public boolean isRetweet() {
		return isRetweet;
	}
	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}
	public boolean isRetweeted() {
		return isRetweeted;
	}
	public void setRetweeted(boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}
	public int getFavoritedCount() {
		return favoritedCount;
	}
	public void setFavoritedCount(int favoritedCount) {
		this.favoritedCount = favoritedCount;
	}
	public int getRetweetCount() {
		return retweetCount;
	}
	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserScreenName() {
		return userScreenName;
	}
	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}
	public String getReplyToScreenName() {
		return replyToScreenName;
	}
	public void setReplyToScreenName(String replyToScreenName) {
		this.replyToScreenName = replyToScreenName;
	}
	public long getReplyToUserId() {
		return replyToUserId;
	}
	public void setReplyToUserId(long replyToUserId) {
		this.replyToUserId = replyToUserId;
	}
	public long getReplyToStatusId() {
		return replyToStatusId;
	}
	public void setReplyToStatusId(long replyToStatusId) {
		this.replyToStatusId = replyToStatusId;
	}
	public long getQuotedStatusId() {
		return quotedStatusId;
	}
	public void setQuotedStatusId(long quotedStatusId) {
		this.quotedStatusId = quotedStatusId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getKeywordGroup() {
		return keywordGroup;
	}
	public void setKeywordGroup(String keywordGroup) {
		this.keywordGroup = keywordGroup;
	}
	public long getOriginId() {
		return originId;
	}
	public void setOriginId(long originId) {
		this.originId = originId;
	}
	public List<String> getHashTags() {
		return hashTags;
	}
	public void setHashTags(List<String> hashTags) {
		this.hashTags = hashTags;
	}
	public List<String> getMentions() {
		return mentions;
	}
	public void setMentions(List<String> mentions) {
		this.mentions = mentions;
	}
	public List<String> getTwitterUrls() {
		return twitterUrls;
	}
	public void setTwitterUrls(List<String> twitterUrls) {
		this.twitterUrls = twitterUrls;
	}
	
}
