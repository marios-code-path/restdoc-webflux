package com.example.restcontroller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping(value = "/ok", produces = {MediaType.TEXT_PLAIN_VALUE})
    String getOK() {
        return "OK";
    }

    @GetMapping(value = "hello/{name}", produces = {MediaType.TEXT_PLAIN_VALUE})
    String getHello(@PathVariable String name) {
        return "Hello " + name;
    }
}
