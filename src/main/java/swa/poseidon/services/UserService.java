package swa.poseidon.services;

import swa.poseidon.form.UserForm;
import swa.poseidon.form.UserFormWithPassword;
import swa.poseidon.model.User;

public interface UserService extends EntityService<User,UserForm>
{
	public User createByFormWithPassword(UserFormWithPassword form);
	
	public User updateByFormWithPassword(UserFormWithPassword form);

}
