package cn.lottery.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("combine")
    public String combine(String type, Model model) {
        model.addAttribute("type", type);
        return "combine";
    }
}
