package swa.poseidon.services;

import org.springframework.stereotype.Service;

import swa.poseidon.form.UserForm;
import swa.poseidon.form.UserFormWithPassword;
import swa.poseidon.model.User;

@Service
public class UserServiceImpl extends EntityServiceImpl<User,UserForm> implements UserService
{
	public User createByFormWithPassword(UserFormWithPassword form) 
	{
		User newUser = new User(form);
		newUser.setId(0); // ensure that save will generate a new entry and not overwrite anything
		return repository.save(newUser);
	}

	public User updateByFormWithPassword(UserFormWithPassword form)  
	{
		read(form.id()); // check if exists
		return repository.save(form.toEntity());
	}
}
