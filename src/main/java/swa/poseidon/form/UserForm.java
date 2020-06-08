package swa.poseidon.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.User;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class UserForm implements FormCore<User>
{
    Integer userId;

    @NotBlank(message = "username is mandatory")
    String username;
    
    @NotBlank(message = "fullName is mandatory")   
    String fullname;
    
	@NotNull(message="role is mandatory")
	@Positive(message="role must be positive")
	Integer role;

	public UserForm(User u)
	{
		userId=u.getUserId();
		username=u.getUsername();
		fullname=u.getFullname();
		role=u.getRole();
	}

	@Override
	public User toEntity() {
		return new User(this);
	}

	@Override
	public Integer id() {
		return userId;
	}

	@Override
	public boolean matches(User u) {
		if (userId.intValue() != u.getUserId().intValue()) return false;
		if (!username.equals(u.getUsername())) return false;
		if (!fullname.equals(u.getFullname())) return false;
		if (!role.equals(u.getRole())) return false;
		return true;
	}
}
