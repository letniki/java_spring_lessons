package org.okten.springdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // == @Controller + @ResponseBody
public class BasicController {

    @GetMapping("/hello/{name}")
    public String helloOkten(@PathVariable String name, @RequestParam String city) {
        return "Hello %s from %s".formatted(name, city);
    }
}
