package com.example.restcontroller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compute")
public class ExampleController {

    @GetMapping(value = "/root/{number}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Double root(@PathVariable Double number) {
        return Math.sqrt(number);
    }

    @GetMapping(value = "/square/{number}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Double square(@PathVariable Double number) {
        return number * number;
    }
}