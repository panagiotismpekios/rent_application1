package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.service.RenterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*Controller class for renter requests*/
@Controller
@RequestMapping("renter")
public class RenterController {

    private RenterService renterService;

    public RenterController(RenterService renterService) {this.renterService = renterService;}

    /*Handles requests to display the renters and returns the page with the list of renters*/
    @GetMapping("")
    public String showRenters(Model model){
        model.addAttribute("renters", renterService.getRenters());//models the model to hold the list of renters
        return "renter/renters";
    }

    /*Handles the requests to display a specific renter and returns the page of the specific renter*/
    @GetMapping("/{id}")
    public String showRenter(@PathVariable Integer id, Model model){
        model.addAttribute("renter", renterService.getRenter(id));//Models the model to hold the specific renter
        return "renter/renter";
    }
}
