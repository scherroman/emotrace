package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nashahzad on 4/3/2017.
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    //SERVES A PAGE DISPLAYING ALL VIDEOS ON SITE
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String videos(){
        return "video";
    }

    //SERVES A PAGE DISPLAYING A SPECIFIC VIDEO ON SITE, WITH SPECIFIED VIDEO ID
    @RequestMapping(value = "/watch?={video_id}", method = RequestMethod.GET)
    public String video(@PathVariable("video_id") String video_id){
        return "video?=" + video_id;
    }
}
