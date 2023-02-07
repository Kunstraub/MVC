package com.Batman.Joker.controller;

import com.Batman.Joker.service.ChuckServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JokerController {

    private final ChuckServiceImpl chuckServiceImpl;

    public JokerController(ChuckServiceImpl chuckServiceImpl) {
        this.chuckServiceImpl = chuckServiceImpl;
    }

    @RequestMapping("/joke")
    public String getJokes(Model model){
        model.addAttribute("jokes", chuckServiceImpl.getJoke());
        return "index";
    }
}
