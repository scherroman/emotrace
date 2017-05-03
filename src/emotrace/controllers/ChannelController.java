package emotrace.controllers;

import emotrace.models.Channel;
import emotrace.models.DisplayVideo;
import emotrace.models.Video;
import emotrace.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by nashahzad on 4/5/17.
 */
@Controller
public class ChannelController {
    
    public static final int NUM_VIDEOS_PER_PAGE = 16;

    // EXTERNAL ROUTES

    /**
     * Renders the page for a channel
     * @return template for the channel page
     */
    @RequestMapping(value = "/{channel_id}", method = RequestMethod.GET)
    public String channel(@PathVariable("channel_id") Long channel_id, Model model){
        Channel channel = Channel.get_channel_by_id(channel_id);
        List<Video> videos = Video.get_videos_by_channel_id(channel_id, NUM_VIDEOS_PER_PAGE, 0);
        List<DisplayVideo> display_videos = DisplayVideo.display_videos_from_videos(videos);
        DisplayVideo.add_info_from_youtube(display_videos);

        model.addAttribute("channel", channel);
        model.addAttribute("videos", display_videos);
        model.addAttribute("form_video", new Video());
        model.addAttribute("is_owner", LoginService.is_current_user(channel.owner));
        LoginService.add_current_user_info_to_template(model);

        return "channel";
    }
    
    @RequestMapping(value = "/{channel_id}/videos_scroll", method = RequestMethod.GET)
    public String scroll_channels(@PathVariable("channel_id") String channel_id,
                                  @RequestParam("offset") int offset, Model model) {
        offset = offset * NUM_VIDEOS_PER_PAGE;
        Channel channel = Channel.get_channel_by_id(channel_id);
        List<Video> videos = Video.get_videos_by_channel_id(channel_id, NUM_VIDEOS_PER_PAGE, offset);
        List<DisplayVideo> display_videos = DisplayVideo.display_videos_from_videos(videos);
        DisplayVideo.add_info_from_youtube(display_videos);

        model.addAttribute("channels", display_videos);

        if (LoginService.is_current_user(channel.owner)) {
            return "fragment_collections/video_cards_editable";
        }
        else {
            return "fragment_collections/video_cards";
        }
    }

    // INTERNAL ROUTES

    /**
     * Creates a new channel
     * @return template fragment for the channel
     */
    @RequestMapping(value = "/forms/create", method = RequestMethod.POST)
    public String create_channel(@ModelAttribute Channel channel, Model model) {
        channel.create();

        model.addAttribute("channel", channel);

        return "fragments/channel_card";
    }

    /**
     * Creates a new channel
     * @return template fragment for the editable channel
     */
    @RequestMapping(value = "/forms/create_editable", method = RequestMethod.POST)
    public String create_channel_editable(@ModelAttribute Channel channel, Model model) {
        channel.create();

        model.addAttribute("channel", channel);

        return "fragments/channel_card_editable";
    }

    /**
     * Deletes a channel
     * @return HttpStatus.OK
     */
    @RequestMapping(value = "/forms/delete", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void delete_channel(@ModelAttribute Channel channel) {
        channel.delete();
    }

    /**
     * Updates the information for a channel
     * @return HttpStatus.OK
     */
    @RequestMapping(value = "/forms/rename", method = RequestMethod.POST)
    public String rename_channel() {
        return "";
    }

    /**
     * Updates Channel name and description in datastore
     * @param channel Channel object with info to put into datastore
     * @param model Model object of the page
     * @return Returns a new card with the updated info
     */
    @RequestMapping(value = "/forms/edit_channel", method = RequestMethod.POST)
    public String edit_channel(@ModelAttribute Channel channel, Model model){
        Channel toChange = Channel.get_channel_by_id(channel.getId());
        toChange.setName(channel.getName());
        toChange.setDescription(channel.getDescription());
        toChange.create();

        model.addAttribute("channel", channel);

        return "fragments/channel_card_editable";
    }
}
