package com.zindabad.ecommerce.controller;

import com.zindabad.ecommerce.model.Customer;
import com.zindabad.ecommerce.model.Feedback;
import com.zindabad.ecommerce.repository.CustomerRepository;
import com.zindabad.ecommerce.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
/*import org.springframework.ui.Model;*/
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {

    final
    FeedbackRepository feedbackRepository;

    final
    CustomerRepository customerRepository;

    public FeedbackController(FeedbackRepository feedbackRepository, CustomerRepository customerRepository) {
        this.feedbackRepository = feedbackRepository;
        this.customerRepository = customerRepository;
    }

    //Create feedback...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/allFeedback", method = RequestMethod.POST)
    public String createFeedback(Authentication authentication, @RequestParam String message){
        String username = authentication.getName(); //get Signed UserName...
        Customer customer = customerRepository.findCustomerByUsername(username);

        Feedback feedback = new Feedback(customer, message);
        feedbackRepository.save(feedback);

        return "Feedback created successfully.";
    }

    //Get list of all feedback.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/allFeedback", method = RequestMethod.GET)
    public List allFeedback(){

        return (List) feedbackRepository.findAll();
    }

    //Get the details of a feedback.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.GET)
    public Feedback feedbackDetails(@PathVariable("id") long id){
        Feedback feedback = feedbackRepository.findById(id).get();

        return feedback;
    }

    //Get the list of feedback by a customer.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/myFeedback", method = RequestMethod.GET)
    public List myFeedback(Authentication authentication){
        String username = authentication.getName(); //get Signed UserName...
        Customer customer = customerRepository.findCustomerByUsername(username);

        return feedbackRepository.findFeedbacksByCustomerId(customer.getId());
    }

    //Get the list of feedback by a customer query by admin...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/customerFeedback/{id}", method = RequestMethod.GET)
    public List customerFeedback(@PathVariable("id") long id){
        Customer customer = customerRepository.findById(id).get();

        return feedbackRepository.findFeedbacksByCustomerId(customer.getId());
    }

    //Update feedback.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/updateFeedback/{id}", method = RequestMethod.PUT)
    public String updateFeedback(@PathVariable("id") long id, @RequestParam String message){
        Feedback feedback = feedbackRepository.findById(id).get();
        feedback.setMessage(message);

        feedbackRepository.save(feedback);
        return "Feedback updated successfully.";
    }

    //Delete feedback.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/feedback/delete/{id}", method = RequestMethod.DELETE)
    public String deleteFeedback(@PathVariable("id") long id){
        Feedback feedback = feedbackRepository.findById(id).get();
        feedbackRepository.delete(feedback);

        return "Feedback deleted successfully.";
    }
}
