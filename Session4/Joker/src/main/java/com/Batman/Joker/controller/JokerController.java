package com.Batman.Joker.controller;

import com.Batman.Joker.service.ChuckService;
import com.Batman.Joker.service.ChuckServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JokerController {

    private final ChuckService chuckService;

    public JokerController(ChuckService chuckService) {
        this.chuckService = chuckService;
    }

    @RequestMapping({"/",""})
    public String getJokes(Model model){
        model.addAttribute("jokes", chuckService.getJoke());
        return "index";
    }
}
