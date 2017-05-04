package emotrace.models;

import com.google.api.services.youtube.model.VideoSnippet;
import com.googlecode.objectify.Key;
import emotrace.services.YouTubeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by romanscher on 4/30/17.
 * A video container with additional attributes for displaying video information client-side
 */
public class DisplayVideo {

    public Video video;
    public Channel channel;
    public String title;
    public String thumbnail;
    public String emoji;

    public DisplayVideo() {}

    public DisplayVideo(Video video) {
        this.video = video;
        this.channel = Channel.get_channel_by_id(this.video.channel.getId());
    }

    public static DisplayVideo display_video_from_video(Video video) {
        return new DisplayVideo(video);
    }

    public static List<DisplayVideo> display_videos_from_videos(List<Video> videos) {
        List<DisplayVideo> display_videos = new ArrayList<>();
        for (Video video : videos) {
			display_videos.add(DisplayVideo.display_video_from_video(video));
		}

		return display_videos;
    }

    /**
     * Batch querys YouTube to set the title, thumbnail, etc... for the given display_videos
     */
    public static void add_info_from_youtube(List<DisplayVideo> display_videos) {
        //Retrieve youtube video snippets using the external ids of display_videos
        List<String> external_ids = new ArrayList<>();
        for(DisplayVideo displayVideo : display_videos){
            external_ids.add(displayVideo.video.externalId);
        }
        List<com.google.api.services.youtube.model.Video> youtube_videos = YouTubeService.get_video_snippets(external_ids);

        // Convert list of youtube_videos to a map with video id as key, snippet as value
        HashMap<String, VideoSnippet> youtube_snippets_map = new HashMap<>();
        for (com.google.api.services.youtube.model.Video video : youtube_videos) {
            youtube_snippets_map.put(video.getId(), video.getSnippet());
        }

        // Extract info from snippet corresponding to each display_video
        for (DisplayVideo display_video: display_videos) {
            VideoSnippet video_snippet = youtube_snippets_map.get(display_video.video.externalId);

            if (video_snippet != null) {
                display_video.title = video_snippet.getTitle();
                display_video.thumbnail = video_snippet.getThumbnails().getMedium().getUrl();
            }
        }
    }
}
