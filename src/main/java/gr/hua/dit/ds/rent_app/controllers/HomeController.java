package gr.hua.dit.ds.rent_app.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*Controller class for home requests*/
@RequestMapping("/")
public class HomeController {

    /*Handles home page requests and returns the home page*/
    @GetMapping
    public String home(Model model) {

        model.addAttribute("title", "Home");
        return "index";
    }
}
