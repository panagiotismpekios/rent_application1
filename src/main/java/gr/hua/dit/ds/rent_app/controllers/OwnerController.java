package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.service.OwnerService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*Controller class for owner requests*/
@Controller
@RequestMapping("owner")
public class OwnerController {

    private OwnerService ownerService;

    public OwnerController(OwnerService ownerService){ this.ownerService = ownerService;}

    /*Handles requests for owners list and returns the owners page which shows the list*/
    @GetMapping("")
    public String showOwners(Model model){
        model.addAttribute("owners", ownerService.getOwners());//models the model to hold the owners' list
        return "owner/owners";
    }

    /*Handles requests for displaying a specific owner and returns the page with the owner's details*/
    @GetMapping("/{id}")
    public String showOwner(@PathVariable Integer id, Model model){
        model.addAttribute("owner", ownerService.getOwner(id));//models the model to hold the owner
        return "owner/owner";
    }






}
