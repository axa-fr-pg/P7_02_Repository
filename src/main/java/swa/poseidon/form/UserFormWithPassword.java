package swa.poseidon.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.User;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class UserFormWithPassword extends UserForm
{
    @Pattern(regexp=".*[A-Z].*", message="password must contain at least one upper case character")
    @Size(min=8, message="password minimal length is 8 characters")
    @Pattern(regexp=".*[0-9].*", message="password must contain at least one digit")
    @Pattern(regexp=".*[^[[ a-zA-Z0-9]]].*", message="password must contain at least one symbol")
    String password;
    
	public UserFormWithPassword(User u)
	{
		super(u);
		password=u.getPassword();
	}

	@Override
	public User toEntity() {
		return new User(this);
	}
}
