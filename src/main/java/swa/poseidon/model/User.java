package swa.poseidon.model;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.form.UserForm;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class User implements EntityCore<UserForm>
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer userId;
    
	@Column(length = 30)
    String username;
    
	@Column(length = 125)
    String password;

	@Column(length = 125)
    String fullname;
    
	@Column(columnDefinition = "TINYINT")
	Integer role;
    
    public User(String username, String password, String fullname, Integer role)
    {
		// userId set to 0;
		this.username=username;
		this.password=password;
		this.fullname=fullname;
		this.role=role;
    }

    public User(UserForm f)
    {
		userId=f.getUserId();
		username=f.getUsername();
		fullname=f.getFullname();
		role=f.getRole();
    }

	@Override
	public void setId(Integer id) {
		userId = id;
	}

	@Override
	public UserForm toForm() {
		return new UserForm(this);
	}

	@Override
	public EntityCore<UserForm> newValidTestEntityWithIdZero(int index) 
	{
		return (EntityCore<UserForm>) new User("username"+index, "password"+index, "fullname"+index, index*11);
	}

	@Override
	public EntityCore<UserForm> newValidTestEntityWithGivenId(int index) 
	{
		EntityCore<UserForm> ec = newValidTestEntityWithIdZero(index);
		ec.setId(index);
		return ec;
	}

	@Override
	public EntityCore<UserForm> newInvalidTestEntity() 
	{
		return (EntityCore<UserForm>) new User("", "", "", -1);
	}
}
