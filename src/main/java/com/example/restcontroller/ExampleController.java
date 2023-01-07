package com.example.restcontroller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/sum", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Double sumOf(@RequestBody Double[] numbers) {
        return sum(numbers);
    }

    public double sum(Double[] array) {
        double sum = 0;
        for (Double d : array) {
            sum += d;
        }
        return sum;
    }

}
