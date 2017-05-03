package emotrace.services;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import emotrace.models.DisplayVideo;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by romanscher on 5/1/17.
 */
public class YouTubeService {

    /**
     * Our YouTube API key. Visit https://console.developers.google.com/apis/credentials to create an API Key
     */
    private static final String YOUTUBE_API_KEY = "AIzaSyA5pNZg0ldhju3cqgBfOVU3w1WKjrEdtV8";

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;

    public static void init () {
        // Object used to make YouTube Data API requests.
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {}
        }).setApplicationName("emotrace").build();
    }

    /**
     * See https://developers.google.com/apis-explorer/?hl=en_US#p/youtube/v3/youtube.videos.list for API query samples
     */
    /**
     * Retrieves a list of YouTube Video objects containing video snippets (general video info)
     * @param ids list of youtube video ids to retrieve snippets for
     */
    public static List<Video> get_video_snippets(List<String> ids) {
        List<Video> videoResults = new ArrayList<>();

        try {
            // Define the API request for retrieving video snippets (contains title & thumbnail info).
            YouTube.Videos.List videosQuery = youtube.videos().list("snippet");
            videosQuery.setKey(YOUTUBE_API_KEY);
            videosQuery.setId(StringUtils.join(ids, ","));
            // Only retrieve needed fields (Increase efficiency)
            videosQuery.setFields("items(id,snippet(thumbnails,title))");

            // Execute API call
            VideoListResponse videoResponse = videosQuery.execute();
            videoResults = videoResponse.getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videoResults;
    }
}
