package emotrace.controllers;

import emotrace.models.DisplayVideo;
import emotrace.models.Video;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by romanscher on 5/1/17.
 */
@Controller
public class VideosController {

    public static final int NUM_VIDEOS_PER_PAGE = 16;

    @RequestMapping(value = "videos", method = RequestMethod.GET)
    public String index(Model model) {
        List<Video> videos = Video.scroll_videos(NUM_VIDEOS_PER_PAGE, 0);
        // Create DisplayVideos, which add title, thumbnail, etc...
        List<DisplayVideo> display_videos = DisplayVideo.display_videos_from_videos(videos);

        model.addAttribute("videos", display_videos);
        LoginController.add_current_user_info_to_template(model);

        return "videos";
    }

    // INTERNAL ROUTES

    @RequestMapping(value = "/scroll", method = RequestMethod.GET)
    public String scroll_channels(@RequestParam("offset") int offset, Model model) {
        List<Video> videos = Video.scroll_videos(NUM_VIDEOS_PER_PAGE, offset);
        // Create DisplayVideos, which add title, thumbnail, etc...
        List<DisplayVideo> display_videos = DisplayVideo.display_videos_from_videos(videos);

        model.addAttribute("videos", display_videos);

        return "fragment_collections/video_cards";
    }
}
