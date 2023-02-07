package com.Batman.Joker.service;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;
import org.springframework.stereotype.Service;

@Service
public class ChuckServiceImpl implements ChuckService{

    private final ChuckNorrisQuotes chuckNorrisQuotes;
    public ChuckServiceImpl() {
        this.chuckNorrisQuotes = new ChuckNorrisQuotes();
    }


    public String getJoke(){
        String joke = chuckNorrisQuotes.getRandomQuote();
        System.out.println(joke);
        return joke;

    }
}
