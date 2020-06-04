package swa.poseidon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.services.BidService;
import java.util.List;
import javax.validation.Valid;

@Controller
public class BidController 
{
	@Autowired BidService bidService;

    @GetMapping("/bids/list")
    public String list(Model model)
    {
    	List<BidForm> bidFormList = bidService.readAll();
    	model.addAttribute("bids", bidFormList);
        return "/bids/list";
    }

    @GetMapping("/bids/add")
    public String getRequestAdd(Bid bid) 
    {    	
        return "bids/add";
    }

    @PostMapping("/bids/add")
    public String postDataAdd(@Valid Bid bid, BindingResult result, Model model) 
    {
        if (result.hasErrors()) 
        {        	
            return "/bids/add";
        }
        bidService.create(bid);
        return "redirect:/bids/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}
