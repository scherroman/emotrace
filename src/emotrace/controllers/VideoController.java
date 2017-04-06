package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by nashahzad on 4/3/2017.
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    //SERVES A PAGE DISPLAYING A SPECIFIC VIDEO ON SITE, WITH SPECIFIED VIDEO ID
    @RequestMapping(value = "/{video_id}", method = RequestMethod.GET)
    public String video(@PathVariable("video_id") String video_id, Model model){
        model.addAttribute("video_id", video_id);
        return "video";
    }
}
