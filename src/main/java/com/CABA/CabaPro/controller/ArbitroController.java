package com.CABA.CabaPro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArbitroController {

    @GetMapping("/arbitro/dashboard")
    public String dashboard() {
        return "arbitro/dashboard";
    }

}