package com.kmalik.sample.text;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TextHomeController {
  
    @RequestMapping(value="/")
    public String index(){
        return "redirect:/status";
    }    
}
