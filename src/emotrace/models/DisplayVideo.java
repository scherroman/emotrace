package emotrace.models;

import com.googlecode.objectify.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romanscher on 4/30/17.
 * A video container with additional attributes for displaying video information client-side
 */
public class DisplayVideo {

    public Video video;
    public String title;
    public String thumbnail;
    public String emoji;

    public DisplayVideo() {
        super();
    }

    public DisplayVideo(Video video) {
        this.video = video;
    }

    public DisplayVideo(Video video, String title, String thumbnail, String emoji) {
        this.video = video;
        this.title = title;
        this.thumbnail = thumbnail;
        this.emoji = emoji;
    }

    public static List<DisplayVideo> display_videos_from_videos(List<Video> videos) {
        List<DisplayVideo> display_videos = new ArrayList<DisplayVideo>();
        for (Video video : videos) {
			display_videos.add(new DisplayVideo(video));
		}

		return display_videos;
    }
}
