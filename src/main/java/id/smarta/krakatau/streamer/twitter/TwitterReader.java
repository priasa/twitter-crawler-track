package id.smarta.krakatau.streamer.twitter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import id.smarta.krakatau.streamer.dao.KrakatauRepository;
import id.smarta.krakatau.streamer.dao.TwitterRepository;
import id.smarta.krakatau.streamer.entity.TwitterStatus;
import id.smarta.krakatau.streamer.util.TwitterStreamBuilderUtil;
import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

/**
 * 
 * @author ardi priasa
 *
 */
public class TwitterReader {

	static final Logger LOGGER = LoggerFactory.getLogger(TwitterReader.class);

	private String keywordGroup;
	private TwitterStream stream;
	private ThreadPoolTaskExecutor taskExecutor;
	private KrakatauRepository krakatauRepository;
	private TwitterRepository twitterRepository;
	
	public String readTwitterFeed(final String collectionName) {
		StatusListener listener = new StatusListener() {
			public void onException(Exception e) {
				LOGGER.error("Exception occured:" + e.getMessage());
				e.printStackTrace();
			}

			public void onTrackLimitationNotice(int n) {
				LOGGER.warn("Track limitation notice for " + n);
			}

			public void onStatus(final Status status) {
				String tweetStatus = generateTweetStatus(status, false);
				LOGGER.info(tweetStatus);		
				taskExecutor.execute(new Runnable() {
					public void run() {
						TwitterStatus twitterStatus  = extractTwitterStatus(status);
						twitterRepository.saveStatus(twitterStatus, collectionName.concat("_SA"));
					}
				});
				
				if (status.getRetweetedStatus() != null) {
					taskExecutor.execute(new Runnable() {
						public void run() {
							TwitterStatus twitterStatus  = extractTwitterStatus(status.getRetweetedStatus());
							twitterRepository.saveStatus(twitterStatus, collectionName.concat("_RE"));
						}
					});
				}
				
				if (status.getQuotedStatus() != null) {
					taskExecutor.execute(new Runnable() {
						public void run() {
							TwitterStatus twitterStatus  = extractTwitterStatus(status.getQuotedStatus());
							twitterRepository.saveStatus(twitterStatus, collectionName.concat("_QU"));
						}
					});
				}
			}

			public void onStallWarning(StallWarning arg0) {
				LOGGER.warn("Stall warning");
			}

			public void onScrubGeo(long arg0, long arg1) {
				LOGGER.info("Scrub geo with:" + arg0 + ":" + arg1);
			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				LOGGER.info("Status deletion notice");
			}
		};


		if (stream != null) {
			LOGGER.info("###STREAM NOT NULL");
			stream.shutdown();
		} 

		stream = TwitterStreamBuilderUtil.getStream();
		stream.addListener(listener);
		LOGGER.info("###STREAM AVAILABLE");
				
		List<String> tracks = krakatauRepository.findTwitterKeywords(keywordGroup); 
		
		String[] keywords = tracks.toArray(new String[tracks.size()]);
		
		StringBuilder builder = new StringBuilder();
		for (String keyword : keywords) {
			builder.append(keyword).append(TwitterStreamBuilderUtil.SPACE);
		}
		LOGGER.info("Keywords[" + builder.toString() + "]");
		
		FilterQuery qry = new FilterQuery();
		qry.track(keywords);
		stream.filter(qry);
		
		return "TWITTER_STREAM";
	}

	protected TwitterStatus extractTwitterStatus(Status status) {
		TwitterStatus twitterStatus = new TwitterStatus();
		twitterStatus.setCreatedAt(status.getCreatedAt());
		twitterStatus.setFavorited(status.isFavorited());
		twitterStatus.setFavoritedCount(status.getFavoriteCount());
		twitterStatus.setKeywordGroup(keywordGroup);

		List<String> hashTags = new ArrayList<String>();
		HashtagEntity[] hashtagEntities = status.getHashtagEntities();
		for (HashtagEntity hashtagEntity : hashtagEntities) {
			hashTags.add(hashtagEntity.getText());
		}
		twitterStatus.setHashTags(hashTags);
		
		List<String> mentions = new ArrayList<String>();
		UserMentionEntity[] userMentionEntities =  status.getUserMentionEntities();
		for (UserMentionEntity userMentionEntity : userMentionEntities) {
			mentions.add(userMentionEntity.getName());
		}
		twitterStatus.setMentions(mentions);
		
		if (status.getRetweetedStatus() != null) {
			twitterStatus.setOriginId(status.getRetweetedStatus().getId()); 
		}
		
		List<String> twitterUrls = new ArrayList<String>(); 
		URLEntity[] urlEntities =  status.getURLEntities();
		for (URLEntity urlEntity : urlEntities) {
			twitterUrls.add(urlEntity.getExpandedURL());
		}
		twitterStatus.setTwitterUrls(twitterUrls);
		
		twitterStatus.setQuotedStatusId(status.getQuotedStatusId());
		twitterStatus.setReplyToScreenName(status.getInReplyToScreenName());
		twitterStatus.setReplyToStatusId(status.getInReplyToStatusId());
		twitterStatus.setReplyToUserId(status.getInReplyToUserId());
		twitterStatus.setRetweet(status.isRetweet());
		twitterStatus.setRetweeted(status.isRetweeted());
		twitterStatus.setSource(status.getSource());
		twitterStatus.setText(status.getText());
		twitterStatus.setTweetId(status.getId());
		twitterStatus.setUserId(status.getUser().getId());
		twitterStatus.setUserScreenName(status.getUser().getScreenName());
		return twitterStatus;
	}

//	protected void doSaveTwitterRaw(Status status, String collectionName) {
//		String twitterRaw  = new Gson().toJson(status);
//		TwitterRaw raw = new TwitterRaw();
//		raw.setContent(twitterRaw);
//		raw.setTweetId(status.getId());
//		raw.setKeywordGroup(KEYWORD_GROUP);
//		twitterRepository.saveRaw(raw, collectionName);
//	}

	private String generateTweetStatus(Status status, boolean isRetweet) {
		StringBuilder tweetStatus = new StringBuilder();
		tweetStatus.append(status.getId()).append(",");
		
		String text = status.getText();
		text = text.replaceAll("(\\r|\\n|\\r\\n)+", " ");
		text = text.replace(",", " ");				
		tweetStatus.append(text).append(",");
		
		tweetStatus.append(status.getSource()).append(",");
		tweetStatus.append(status.isFavorited()).append(",");
		tweetStatus.append(status.getFavoriteCount()).append(",");
		tweetStatus.append(status.isRetweet()).append(",");
		tweetStatus.append(status.getRetweetCount()).append(",");
		tweetStatus.append(status.isRetweeted()).append(",");
		tweetStatus.append(status.getUser().getId()).append(",");
		tweetStatus.append(status.getUser().getScreenName());
	
		if (!isRetweet) {
			String originalId = "null";
			if (status.getRetweetedStatus() != null) {
				Status retweetStatus = status.getRetweetedStatus();
				originalId = ""+retweetStatus.getId();
			}
			tweetStatus.append(",").append(originalId);
		}
		return tweetStatus.toString();	
	}
	
	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public TwitterStream getStream() {
		return stream;
	}

	public void setStream(TwitterStream stream) {
		this.stream = stream;
	}

	public void shutdownStream() {
		if (stream != null) {
			LOGGER.info("###READY TO SHUTDOWN");
			stream.shutdown();
		}
	}

	public KrakatauRepository getKrakatauRepository() {
		return krakatauRepository;
	}

	public void setKrakatauRepository(KrakatauRepository krakatauRepository) {
		this.krakatauRepository = krakatauRepository;
	}

	public TwitterRepository getTwitterRepository() {
		return twitterRepository;
	}

	public void setTwitterRepository(TwitterRepository twitterRepository) {
		this.twitterRepository = twitterRepository;
	}

	public String getKeywordGroup() {
		return keywordGroup;
	}

	public void setKeywordGroup(String keywordGroup) {
		this.keywordGroup = keywordGroup;
	}
	
}
