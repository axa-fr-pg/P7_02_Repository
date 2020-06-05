package swa.poseidon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swa.poseidon.form.BidForm;
import swa.poseidon.form.BidFormList;
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

    @PutMapping("/update/{id}")
    public ResponseEntity<BidForm> update(@PathVariable Integer id, @RequestBody @Valid BidForm bidForm) throws InvalidRequestException 
    {
    	if (id == 0 || bidForm.getBidId() == null || id != bidForm.getBidId().intValue()) throw new InvalidRequestException();
        return new ResponseEntity<BidForm>(new BidForm(bidService.update(bidForm)), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BidForm> read(@PathVariable Integer id) 
    {
        return new ResponseEntity<BidForm>(new BidForm(bidService.read(id)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
    
    //TODO gérer les exceptions
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
