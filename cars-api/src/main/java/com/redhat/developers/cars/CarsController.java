package com.redhat.developers.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/cars")
public class CarsController {

    @GetMapping(value = "/",produces = "text/html")
    public String home() {
        return "<h1>Hello!!</h1><p><h2>Welcome to my world of cars</h2></p><p>Click <a href=\"/cars/list\">list</a> to get a " +
            "list of my favorite cars</p>";
    }

    @RequestMapping(value = "/list", produces = "application/json")
    public List<String> cars() {
        return Arrays.asList("BMW", "Hyundai Verna", "Audi", "Ferrari");
    }
}
