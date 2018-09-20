package edu.as.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dell on 2016/12/13.
 */

@Controller
@RequestMapping("/Topic")
public class TopicController {
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "Topic/main";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String all() {
        return "Topic/all";
    }

    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    public String topic() {
        return "Topic/topic_new";
    }

    @RequestMapping(value = "/document", method = RequestMethod.GET)
    public String document() {
        return "Topic/document";
    }

    @RequestMapping(value = "/word", method = RequestMethod.GET)
    public String word() {
        return "Topic/word";
    }

}
