package emotrace.controllers;

import emotrace.models.Channel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nashahzad on 4/5/17.
 */
@Controller
public class ChannelController {

    // Displays a channel
    @RequestMapping(value = "/{channel_id}", method = RequestMethod.GET)
    public String channel(@PathVariable("channel_id") Long channel_id, Model model, ModelMap modelMap){
        Channel channel = Channel.get_channel_by_id(channel_id);

        model.addAttribute("channel", channel);

        LoginController.add_current_user_info_to_template(modelMap);

        return "channel";
    }

    @RequestMapping(value = "/forms/create", method = RequestMethod.POST)
    public String create_channel(@ModelAttribute Channel channel, Model model) {
        channel.create();

        model.addAttribute("channel", channel);

        return "fragments/channel_card";
    }

    @RequestMapping(value = "/forms/create_editable", method = RequestMethod.POST)
    public String create_channel_editable(@ModelAttribute Channel channel, Model model) {
        channel.create();

        model.addAttribute("channel", channel);

        return "fragments/channel_card_editable";
    }

    @RequestMapping(value = "/forms/delete", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void delete_channel(@ModelAttribute Channel channel) {

        System.out.println(channel);
        System.out.println("channel id: " + channel.id);
        System.out.println("channel name: " + channel.id);

        channel.delete();
    }

    @RequestMapping(value = "/forms/rename", method = RequestMethod.POST)
    public String rename_channel() {
        return "";
    }

    @RequestMapping(value = "/forms/edit_channel", method = RequestMethod.POST)
    public String edit_channel(@ModelAttribute Channel channel, Model model){
        String s = "fdsa";
        System.out.println("triggered");
        return "";
    }
}
