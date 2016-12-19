package id.smarta.krakatau.streamer.util;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamBuilderUtil {

	public static final String SPACE = " ";

	public static TwitterStream getStream() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("Ta46kgvineFZf99XGrueT89Bo");
		cb.setOAuthConsumerSecret("UfLCW3kxoas64zOITklya6HrRVOLT48sGbjNbTov0zveUAN9qS");
		cb.setOAuthAccessToken("4629899430-LTJPhX4rGezvUfh3ThHrLexxjGhHt0ieWoz5ThH");
		cb.setOAuthAccessTokenSecret("QSv2k4CvmH5n9akx9M5mabPOIVtqyOYy5cbvLmum3pjem");

		return new TwitterStreamFactory(cb.build()).getInstance();
	}
}
