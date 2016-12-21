package id.smarta.krakatau.streamer.twitter;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import id.smarta.krakatau.streamer.dao.KrakatauRepository;

/**
 * 
 * @author ardi priasa
 *
 */
public class TwitterServiceImpl implements TwitterService {

	static final Logger LOGGER = LoggerFactory.getLogger(TwitterServiceImpl.class);

	private static final String NEED_TO_RESTART = "NEED_TO_RESTART";

	TwitterReader twitterReader;
	KrakatauRepository krakatauRepository;
	String streamTrackId; 
	
	@Override
	public void doStream() throws InterruptedException {
		String twitterId = null;
		while (true) {
			if (twitterId == null) {
				twitterReader.readTwitterFeed(streamTrackId);
				twitterId = UUID.randomUUID().toString();
			}
			Thread.sleep(300000);
			LOGGER.info("###CHECK STREAM STATUS");
			twitterId = checkStreamStatus(twitterId);
		}

	}

	private String checkStreamStatus(String twitterId) {
		String streamStatus = krakatauRepository.findTwitterStreamStatus(streamTrackId);
		LOGGER.info("###STREAM STATUS :[" + streamStatus + "]");
		if (streamStatus != null) {
			if (streamStatus.equalsIgnoreCase(NEED_TO_RESTART)) {
				twitterReader.shutdownStream();
				twitterId = null;
			} 
		}
		return twitterId;
	}

	public TwitterReader getTwitterReader() {
		return twitterReader;
	}

	public void setTwitterReader(TwitterReader twitterReader) {
		this.twitterReader = twitterReader;
	}

	public KrakatauRepository getKrakatauRepository() {
		return krakatauRepository;
	}

	public void setKrakatauRepository(KrakatauRepository krakatauRepository) {
		this.krakatauRepository = krakatauRepository;
	}

	public String getStreamTrackId() {
		return streamTrackId;
	}

	public void setStreamTrackId(String streamTrackId) {
		this.streamTrackId = streamTrackId;
	}

}
