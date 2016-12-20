package id.smarta.krakatau.streamer.twitter;

import twitter4j.TwitterException;

public interface TwitterService {
	void doStream() throws IllegalStateException, TwitterException, InterruptedException;
}
