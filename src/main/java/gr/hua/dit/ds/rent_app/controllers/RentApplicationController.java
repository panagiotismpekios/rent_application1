package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.entities.RentApplication;
import gr.hua.dit.ds.rent_app.entities.Status;
import gr.hua.dit.ds.rent_app.service.OwnerService;
import gr.hua.dit.ds.rent_app.service.RentApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*Controller class for rent application requests*/
@Controller
@RequestMapping("/rent-applications")
public class RentApplicationController {

    public RentApplicationService rentApplicationService;

    public OwnerService ownerService;

    public RentApplicationController(RentApplicationService rentApplicationService, OwnerService ownerService) {
        this.rentApplicationService = rentApplicationService;
        this.ownerService = ownerService;
    }

    /*Handles the requests to display the list of rent applications and returns the page with them*/
    @GetMapping("")
    public String showRentApplications(Model model, Principal principal){
        //Gets the username and the owner by connected user
        String username = principal.getName();
        Owner owner = ownerService.getOwnerByUsername(username);

        //Gets owner's id and the rent applications by its id
        Integer ownerId = owner.getId();
        List<RentApplication> ownerApplications = rentApplicationService.getRentApplicationsByOwnerId(ownerId);

        //models the model to hold the list of rent application
        model.addAttribute("rentApplications", ownerApplications);
        return "rent-application/rent-applications";
    }

    /*Handles the requests to change the status of a rent application and returns the page*/
    @GetMapping("/change-status/{id}")
    public String showChangeApplicationStatus(@PathVariable Integer id, Model model){
        RentApplication application = rentApplicationService.getRentApplication(id);
        model.addAttribute("rentApplication", application);//Models the model to hold the application
        model.addAttribute("status", rentApplicationService.getRentApplication(id).getStatus());//Models the model to hold the status of application
        return "rent-application/change-status";
    }

    /*Handles the post requests to change the status of an application and returns the updated page with applications*/
    @PostMapping("/change-status/{id}")
    public String showUpdatedApplicationStatus(@PathVariable Integer id,
                                               @RequestParam("status") Status status,
                                               Model model) {
        //Prints the rent application and its status by its id
        System.out.println(id);
        RentApplication rentApplication = rentApplicationService.getRentApplication(id);
        System.out.println(rentApplication.getStatus());

        //Checks the status value selected (approved, rejected, pending)
        if (status == Status.Approved) {
            rentApplicationService.approveRentApplicationRequest(id);
            rentApplicationService.deleteApplication(rentApplication);
        }  else if (status == Status.Rejected) {
            rentApplicationService.rejectRentApplicationRequest(id);
            rentApplicationService.deleteApplication(rentApplication);
        }  else if (status == Status.Pending) {
            if (rentApplication.getStatus() != Status.Pending) {
                rentApplication.setStatus(Status.Pending);
                rentApplicationService.saveRentApplication(rentApplication);
            }
        } else {
            throw new RuntimeException("Invalid status change request.");
        }
            //models the model to hold the updated list of applications
            model.addAttribute("rentApplications", rentApplicationService.getRentApplications());
            //models the model to hold the success message
            model.addAttribute("succesMessage", "Status successfully updated");
            return "rent-application/rent-applications";
        }
}
