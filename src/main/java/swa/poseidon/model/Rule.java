package swa.poseidon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.form.RuleForm;
import swa.poseidon.services.EntityModelService;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rule implements EntityModelService<RuleForm>
{
 	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer ruleId;
	
	@NotBlank
	@Column(length = 125)
	String name;
	
	@NotBlank
	@Column(length = 125)
	String description;
	
	@NotBlank
	@Column(length = 125)
	String json;
	
	@NotBlank
	@Column(length = 125)
	String template;
	
	@NotBlank
	@Column(length = 125)
	String sqlStr;
	
	@NotBlank
	@Column(length = 125)
	String sqlPart;
	
	@Override
	public void setId(Integer id) {
		setRuleId(id);
	}

	@Override
	public RuleForm newForm() {
		return new RuleForm(this);
	}
}
