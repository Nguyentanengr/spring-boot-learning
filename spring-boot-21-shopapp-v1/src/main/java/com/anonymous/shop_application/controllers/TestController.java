package com.anonymous.shop_application.controllers;


import com.anonymous.shop_application.dtos.responses.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${api.prefix}/tests")
public class TestController {

    @GetMapping
    @ResponseBody
    public Response getMessage() {
        return new Response("This is test message");
    }

}
