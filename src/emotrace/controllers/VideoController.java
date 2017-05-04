package emotrace.controllers;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONString;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import emotrace.models.DisplayVideo;
import emotrace.models.RawEmotion;
import emotrace.models.Video;
import emotrace.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by nashahzad on 4/3/2017.
 */
@Controller
public class VideoController {

    // EXTERNAL ROUTES

    /**
     * Renders the page for a video
     * @return template for the video page
     */
    @RequestMapping(value = "/{video_id}", method = RequestMethod.GET)
    public String video(@PathVariable("video_id") String video_id, Model model) {
        Video video = Video.get_video_by_id(video_id);
        DisplayVideo display_video = DisplayVideo.display_video_from_video(video);
        DisplayVideo.add_info_from_youtube(new ArrayList<>(Arrays.asList(display_video)));

        model.addAttribute("video", display_video);
        LoginService.add_current_user_info_to_template(model);

        return "video";
    }

    // MODEL ATTRIBUTES

    /**
     * A Video ModelAttribute which returns a Video from a channel_id
     * IS CALLED EVERY TIME A REQUEST IS MADE FOR *ANY* @RequestMapping...
     */
    @ModelAttribute("video_from_channel_id")
    public Video video_from_channel_id(Long channelId, String externalId) {
        // Need to cover the case where nothing is passed, because this method is called any time
        // a @RequestMapping is triggered, regardless of whether it actually takes this @ModelAttribute as a parameter
        if (channelId == null) {
            return new Video();
        }

        return new Video(channelId, externalId);
    }

    // INTERNAL ROUTES

    /**
     * Creates a new video
     * @return template fragment for the video
     */
    @RequestMapping(value = "/forms/create", method = RequestMethod.POST)
    public String create_video(@ModelAttribute("video_from_channel_id") Video video, Model model) {
        video.create();

        DisplayVideo display_video = new DisplayVideo(video);
        DisplayVideo.add_info_from_youtube(new ArrayList<>(Arrays.asList(display_video)));

        model.addAttribute("video", display_video);

        return "fragments/video_card";
    }

    /**
     * Creates a new video
     * @return template fragment for the editable video
     */
    @RequestMapping(value = "/forms/create_editable", method = RequestMethod.POST)
    public String create_video_editable(@ModelAttribute("video_from_channel_id") Video video, Model model) {
        video.create();

        DisplayVideo display_video = new DisplayVideo(video);
        DisplayVideo.add_info_from_youtube(new ArrayList<>(Arrays.asList(display_video)));

        model.addAttribute("video", display_video);

        return "fragments/video_card_editable";
    }

    /**
     * Deletes a video
     * @return HttpStatus.OK
     */
    @RequestMapping(value = "/forms/delete", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void delete_video(@ModelAttribute Video video) {
        video.delete();
    }

    /**
     * Stores aggregate emotion data for video
     */
    @RequestMapping(value="/forms/store_emotion_data", method=RequestMethod.POST)
    public String store_emotion_data(Long video_id, JSONArray arr) {
            for(int i = 0; i < arr.length(); i++){
                RawEmotion emotion = null;
                try {
                    JSONObject jsonObject = (JSONObject) arr.get(i);
                    Gson gson = new Gson();
                    emotion = gson.fromJson(jsonObject.toString(), RawEmotion.class);
                    emotion.video = Video.getKey(video_id);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                RawEmotion current_data = RawEmotion.get_raw_emotion_by_video_timestamp(emotion.video, emotion.timestamp);
                if(current_data != null){
                    // aggregate values
                    current_data.anger += emotion.anger;
                    current_data.joy += emotion.joy;
                    current_data.sadness += emotion.sadness;
                    current_data.disgust += emotion.disgust;
                    current_data.contempt += emotion.contempt;
                    current_data.fear += emotion.fear;
                    current_data.surprise += emotion.surprise;
                    current_data.valence += emotion.valence;
                    current_data.engagement += emotion.engagement;

                    // update on DataStore
                    current_data.create();
                } else{
                    emotion.create();
                }
            }


        return null;
    }
}
