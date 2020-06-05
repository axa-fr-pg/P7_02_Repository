package swa.poseidon.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.User;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
public class UserForm 
{
    Integer userId;
    String username;
    String fullname;
	Integer role;

	public UserForm(User u)
	{
		userId=u.getUserId();
		username=u.getUsername();
		fullname=u.getFullname();
		role=u.getRole();
	}
}
