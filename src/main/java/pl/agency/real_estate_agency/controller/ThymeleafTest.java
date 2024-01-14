package pl.agency.real_estate_agency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafTest {
    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("message", "Hello World");
        return "helloworld";
    }

    @GetMapping("/style")
    public String style(){
        return "add-css-js-demo";
    }

    @GetMapping("/addAddress")
    public String address(){
        return "addAddress";
    }

    @GetMapping("/addProperty")
    public String property(){
        return "addProperty";
    }

}
