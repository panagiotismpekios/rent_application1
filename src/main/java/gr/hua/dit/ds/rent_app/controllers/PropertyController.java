package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.*;
import gr.hua.dit.ds.rent_app.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*Controller class for property requests*/
@Controller
@RequestMapping("property")
public class PropertyController {
    private PropertyService propertyService;

    private RenterService renterService;

    private RentApplicationService rentApplicationService;

    private OwnerService ownerService;

    private VerificationRequestService verificationRequestService;


    public PropertyController(PropertyService propertyService, RenterService renterService,
                              RentApplicationService rentApplicationService,
                              OwnerService ownerService, VerificationRequestService verificationRequestService) {
        this.propertyService = propertyService;
        this.renterService = renterService;
        this.rentApplicationService = rentApplicationService;
        this.ownerService = ownerService;
        this.verificationRequestService = verificationRequestService;
    }

    /*Handles requests to display the properties available and returns the page with the properties' list*/
    @GetMapping("")
    public String showProperties(@RequestParam(required = false) String location,
                                 @RequestParam(required = false) Double minCost,
                                 @RequestParam(required = false) Double maxCost,
                                 @RequestParam(required = false) TypeOfProperty typeOfProperty,
                                 @RequestParam(required = false) Integer minSquare_Meters,
                                 @RequestParam(required = false) Integer maxSquare_Meters,
                                 @RequestParam(required = false) Integer bedrooms,
                                 @RequestParam(required = false) Boolean parking,
                                 Model model){
        //Initializes the search filter and sets null where the user has not passed any preference
        SearchFilter filter = new SearchFilter();
        filter.setLocation(location != null && !location.isEmpty() ? location : null);
        filter.setMinCost(minCost != null && minCost > 0 ? minCost : null);
        filter.setMaxCost(maxCost != null && maxCost > 0 ? maxCost : null);
        filter.setTypeOfProperty(typeOfProperty);
        filter.setMinSquare_Meters(minSquare_Meters != null && minSquare_Meters > 0 ? minSquare_Meters : null);
        filter.setMaxSquare_Meters(maxSquare_Meters != null && maxSquare_Meters > 0 ? maxSquare_Meters : null);
        filter.setBedrooms(bedrooms != null && bedrooms > 0 ? bedrooms : null);
        filter.setParking(parking);
        //Loads properties' list and displays it based on user's filters
        List<Property> properties;
        if (location == null && minCost == null && maxCost == null && typeOfProperty == null) {
            properties = propertyService.getAvailablePropertiesForRenters();
        } else {
            properties = propertyService.searchProperties(filter);
        }
        model.addAttribute("properties", properties);//models the model to hold properties' list
        model.addAttribute("searchFilter", filter);//models the model to hold search filter
        model.addAttribute("typeOfProperty", TypeOfProperty.values());//models the model to hold type of property's values
        return "property/properties";
    }

    /*Handles the requests to display a specific property's details and returns the page with the details*/
    @GetMapping("/{id}")
    public String showProperty(@PathVariable Integer id,Model model) {
        model.addAttribute("property", propertyService.getPropertyDetails(id));//models the model to hold a specific property's profile by its id
        return "property/property-profile";
    }


    /*Handles the requests to add new property and returns the page to add the property*/
    @GetMapping("/new")
    public String addProperty(Model model, Principal principal){
        //Initializes and sets property and property profile
        Property property = new Property();
        Property_profile property_profile = new Property_profile();
        property.setProperty_profile(property_profile);
        //Gets the username based on which user is connected and gets the owner with this username
        String username = principal.getName();
        Owner owner = ownerService.getOwnerByUsername(username);
        if (owner == null) {
            throw new RuntimeException("Owner not found for user: " + username);//checks if owner exists
        }
        model.addAttribute("property", property);//models the model to hold the property
        model.addAttribute("typeOfProperty", TypeOfProperty.values());//models the model to hold the type of property's values
        model.addAttribute("property_profile", property_profile);//models the model to hold the property details
        model.addAttribute("owner", owner);//models the model to hold the owner
        return "property/property";
    }

    /*Handles post requests to add new property and returns the page with the updated property list*/
    @PostMapping("/new")
    public String saveProperty(@Valid @ModelAttribute("property") Property property,
                               BindingResult bindingResult, Model model){
        //Gets the username and owner by connected user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Username from SecurityContext: " + username);
        Owner owner = ownerService.getOwnerByUsername(username);
        owner.getUser().getRoles();
        /*checks if the fields for the details of new property is correctly filled from user;
        if not it returns the page for new property*/
        if (bindingResult.hasErrors()) {
            return "property/property";
        }
        //If owner of new property is the Admin, it saves the property
        if (owner.getId()==1){
            System.out.println("Admin ID: " + owner.getId());
            property.setOwner(owner);
            propertyService.saveProperty(property);
        //If owner of property is not the Admin, it creates a property request
        } else if (owner.getId()!=1) {
            VerificationRequest verificationRequest = new VerificationRequest();
            verificationRequest.setUser(owner.getUser());
            verificationRequest.setStatus(Status.Pending);
            verificationRequest.setTypeOfRequest(TypeOfRequest.PropertyApproval);
            verificationRequest.setPropertyProfile(property.getProperty_profile());
            verificationRequest.setTypeOfProperty(property.getTypeOfProperty());
            verificationRequestService.saveVerificationRequest(verificationRequest);
        }
        model.addAttribute("message", "Your property request has been submitted for approval.");
        model.addAttribute("properties", propertyService.getAvailablePropertiesForRenters());//models the model to hold the available list of properties
        model.addAttribute("searchFilter", new SearchFilter());//models the model to hold the search filter
        model.addAttribute("typeOfProperty", TypeOfProperty.values());//models the model to hold type of property's values
        return "property/properties";
    }

    /*Handles the requests to apply for a property*/
    @GetMapping({"/{id}/apply"})
    public String showApplyForProperty(@PathVariable Integer id, Principal principal, Model model){
        //Gets the property by id and checks if exists
        Property property = propertyService.getProperty(id);
        if(property==null){
            throw new RuntimeException("Property not found");
        }
        //Gets the user and the renter connected to user
        String username = principal.getName();
        Renter renter = renterService.getRenterByUsername(username);
        if (renter == null) {
            throw new RuntimeException("Renter not found for user: " + username);
        }
        model.addAttribute("property", property);//models the model to hold property
        model.addAttribute("renter", renter);//models the model to hold renter
        return "property/apply-for-property";
    }

    /*Handles the post request to apply for a property*/
    @PostMapping("/{id}/apply")
    public String applyForProperty(@PathVariable Integer id, Model model) {
        //Gets the username of user connected and the renter of this user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Username from SecurityContext: " + username);
        Renter renter = renterService.getRenterByUsername(username);

        //Gets the property by id
        Property property = propertyService.getProperty(id);
        if (property == null) {
            throw new RuntimeException("Property not found");
        }
        //creates a rent application for the property
        try {
            RentApplication application = rentApplicationService.createApplication(property.getId(), renter.getId());
            model.addAttribute("message", "Application created successfully");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error: " + e.getMessage());
        }
        model.addAttribute("property", property);//models the model to hold the property
        model.addAttribute("renter", renter);//models the model to hold the renter
        return "property/apply-for-property";
    }
}
