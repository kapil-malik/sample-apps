package com.kmalik.sample.xml;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class XmlHomeController {
  
    @RequestMapping(value="/")
    public String index(){
        return "redirect:/status";
    }    
}
