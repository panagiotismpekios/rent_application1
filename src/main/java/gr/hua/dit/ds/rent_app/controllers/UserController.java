package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.Status;
import gr.hua.dit.ds.rent_app.entities.TypeOfRequest;
import gr.hua.dit.ds.rent_app.entities.User;
import gr.hua.dit.ds.rent_app.entities.VerificationRequest;
import gr.hua.dit.ds.rent_app.service.VerificationRequestService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/*Controller class for user requests*/
@Controller
public class UserController {

    private VerificationRequestService verificationRequestService;

    public UserController(VerificationRequestService verificationRequestService) {
        this.verificationRequestService = verificationRequestService;
    }

    /*Handles the requests to register on the app and returns the page with the form to register*/
    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user", user);//models the model to hold the user
        return "auth/register";
    }

    /*Handles the post requests to save a user, saves the user and returns the index page*/
    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute User user,  BindingResult bindingResult,
                           @RequestParam(value = "requestOwnerRole", required = false) Boolean requestOwnerRole,
                           Model model){
        //checks if the fields of the user registration are filled correctly; if not it returns the register page with the constraints
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        System.out.println("Roles: "+user.getRoles());
        System.out.println("User data: " + user);
        user.setActive(false);

        //Creates a user request for the Admin
        VerificationRequest request = new VerificationRequest();
        request.setUser(user);
        request.setTypeOfRequest(TypeOfRequest.UserApproval);
        request.setStatus(Status.Pending);
        verificationRequestService.saveVerificationRequest(request);
        //checks if the user requests to register requests an owner role too; if so it creates a request for admin
        if (Boolean.TRUE.equals(requestOwnerRole)) {
            verificationRequestService.submitVerificationRequest(user.getId(), TypeOfRequest.OwnerApproval);
        }

        String message = "User '"+user.getId()+"' saved successfully '!";
        model.addAttribute("msg", message);//
        return "index";
    }
}

