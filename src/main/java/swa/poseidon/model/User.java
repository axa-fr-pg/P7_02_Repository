package swa.poseidon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class User 
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer userId;
    
    @NotBlank(message = "Username is mandatory")
	@Column(length = 30)
    String username;
    
    @NotBlank(message = "Password is mandatory")
	@Column(length = 125)
    String password;

    @NotBlank(message = "FullName is mandatory")
	@Column(length = 125)
    String fullname;
    
    @NotBlank(message = "Role is mandatory")
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
}
