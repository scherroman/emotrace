package emotrace.controllers;

import emotrace.models.DisplayVideo;
import emotrace.models.Video;
import emotrace.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by romanscher on 5/1/17.
 * Root route: /videos/*
 */
@Controller
public class VideosController {

    public static final int NUM_VIDEOS_PER_PAGE = 16;

    /**
     * Serves a page displaying all the videos on the site initially showing 16 videos
     * @param model Model of the page
     * @return Returns page displaying all the videos on the page
     */
    @RequestMapping(value = "videos", method = RequestMethod.GET)
    public String index(Model model) {
        List<Video> videos = Video.scroll_videos(NUM_VIDEOS_PER_PAGE, 0);
        // Create DisplayVideos, which add title, thumbnail, etc...
        List<DisplayVideo> display_videos = DisplayVideo.display_videos_from_videos(videos);
        DisplayVideo.add_info_from_youtube(display_videos);

        model.addAttribute("videos", display_videos);
        LoginService.add_current_user_info_to_template(model);

        return "videos";
    }

    /**
     * Helps with scrolling the page and displaying more video cards
     * @param offset Offset of the video cards
     * @param model Model of the page
     * @return Returns a video card fragment
     */
    @RequestMapping(value = "/scroll", method = RequestMethod.GET)
    public String scroll_channels(@RequestParam("offset") int offset, Model model) {
        List<Video> videos = Video.scroll_videos(NUM_VIDEOS_PER_PAGE, offset);
        // Create DisplayVideos, which add title, thumbnail, etc...
        List<DisplayVideo> display_videos = DisplayVideo.display_videos_from_videos(videos);
        DisplayVideo.add_info_from_youtube(display_videos);

        model.addAttribute("videos", display_videos);

        return "fragment_collections/video_cards";
    }
}
