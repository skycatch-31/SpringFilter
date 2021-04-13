package com.jsh.filter.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {
    @GetMapping("/sample/filter/first")
    public String first(){
        return "first call";
    }

    @GetMapping("/sample/filter/second")
    public String second(){
        return "sencond call";
    }
}
