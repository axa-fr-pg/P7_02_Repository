package swa.poseidon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swa.poseidon.form.UserForm;
import swa.poseidon.form.UserFormWithPassword;
import swa.poseidon.model.User;
import swa.poseidon.services.UserService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController
{
	@Autowired
	private UserService entityService;
	
    @GetMapping("/list")
    public List<UserForm> list()
    {
        return entityService.readAllForms();
    }

    @PostMapping("/add")
    public ResponseEntity<UserForm> add(@RequestBody @Valid UserFormWithPassword form) 
    {
		User newUser = entityService.createByForm(form);
        return new ResponseEntity<UserForm>(newUser.toForm(), HttpStatus.CREATED);
    }

	@PutMapping("/update/{id}")
    public ResponseEntity<UserForm> update(@PathVariable Integer id, @RequestBody @Valid UserFormWithPassword form) throws InvalidRequestException 
    {
    	if (id == 0 || form.id() == null || id != form.id().intValue()) throw new InvalidRequestException();
    	User updatedUser =  entityService.updateByForm(form);
        return new ResponseEntity<UserForm>(updatedUser.toForm(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<UserForm> read(@PathVariable Integer id) 
    {
		User foundUser = entityService.read(id);
        return new ResponseEntity<UserForm>(foundUser.toForm(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) 
    {
        return new ResponseEntity<Boolean>(entityService.delete(id), HttpStatus.ACCEPTED);
    }




/*
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
*/

}
