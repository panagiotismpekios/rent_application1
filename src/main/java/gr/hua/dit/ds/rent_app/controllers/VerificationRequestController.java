package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.Status;
import gr.hua.dit.ds.rent_app.entities.TypeOfRequest;
import gr.hua.dit.ds.rent_app.entities.VerificationRequest;
import gr.hua.dit.ds.rent_app.service.UserService;
import gr.hua.dit.ds.rent_app.service.VerificationRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*Controller class for verification requests requests*/
@Controller
@RequestMapping("request")
public class VerificationRequestController {

    private VerificationRequestService verificationRequestService;

    private UserService userService;

    public VerificationRequestController(VerificationRequestService verificationRequestService, UserService userService) {
        this.verificationRequestService = verificationRequestService;
        this.userService = userService;
    }

    /*Handles the requests to display the verification requests' list and returns the page with them*/
    @GetMapping("")
    public String showVerificationRequests(Model model){
        //models the model to hold the verification requests' list
        model.addAttribute("verificationRequests", verificationRequestService.getVerificationRequests());
        return "request/requests";
    }

    /*Handles the requests to change the status of a verification request and returns the page to change it*/
    @GetMapping("/change-status/{id}")
    public String showChangeRequestStatus(@PathVariable Integer id, Model model){
        VerificationRequest request = verificationRequestService.getVerificationRequest(id);
        //models the model to hold the verification request and the status of it
        model.addAttribute("request", request);
        model.addAttribute("status", verificationRequestService.getVerificationRequest(id).getStatus());
        return "request/change-status";
    }

    /*Handles the post requests to change the status of a verification request and returns the updated list of verification requests*/
    @PostMapping("/change-status/{id}")
    public String showUpdatedRequestStatus(
            @PathVariable Integer id,
            @RequestParam("status") Status status,
            Model model) {

        System.out.println("Request ID: " + id);
        //Gets the request by id
        VerificationRequest verificationRequest = verificationRequestService.getVerificationRequest(id);
        System.out.println("Current Status: " + verificationRequest.getStatus());
        TypeOfRequest type = verificationRequest.getTypeOfRequest();

        //Checks status and type of request (OwnerApproval, UserApproval, PropertyApproval)
        if (status == Status.Approved) {
            if(type == TypeOfRequest.OwnerApproval) {
                verificationRequestService.approveVerificationRequestOwner(id);
                verificationRequestService.deleteRequest(verificationRequest);
            } else if(type ==TypeOfRequest.UserApproval){
                verificationRequestService.approveVerificationRequestUser(id);
                userService.saveUser(verificationRequest.getUser());
                verificationRequestService.deleteRequest(verificationRequest);
            } else if (type == TypeOfRequest.PropertyApproval) {
                verificationRequestService.approveVerificationRequestProperty(id);
                verificationRequestService.deleteRequest(verificationRequest);
            }
        } else if (status == Status.Rejected) {
            verificationRequestService.rejectVerificationRequest(id);
            verificationRequestService.deleteRequest(verificationRequest);
        } else if (status == Status.Pending) {
            if (verificationRequest.getStatus() != Status.Pending) {
                verificationRequest.setStatus(Status.Pending);
                verificationRequestService.saveVerificationRequest(verificationRequest);
            }
        } else {
            throw new RuntimeException("Invalid status change request.");
        }

        //Models the model to hold verification requests' list
        model.addAttribute("verificationRequests", verificationRequestService.getVerificationRequests());
        model.addAttribute("successMessage", "Status successfully updated");
        return "request/requests";
    }
}
