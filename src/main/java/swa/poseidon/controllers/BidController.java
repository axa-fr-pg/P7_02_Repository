package swa.poseidon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swa.poseidon.form.BidForm;
import swa.poseidon.form.BidFormList;
import swa.poseidon.model.Bid;
import swa.poseidon.services.BidService;
import javax.validation.Valid;

@RestController
@RequestMapping("/bids")
public class BidController 
{
	@Autowired BidService bidService;

    @GetMapping("/list")
    public BidFormList list()
    {
        return new BidFormList(bidService.readAll());
    }

    @PostMapping("/add")
    public ResponseEntity<BidForm> add(@RequestBody @Valid BidForm bidForm) 
    {
        return new ResponseEntity<BidForm>(new BidForm(bidService.create(bidForm)), HttpStatus.CREATED);
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}
