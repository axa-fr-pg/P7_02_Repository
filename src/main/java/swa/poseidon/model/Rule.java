package swa.poseidon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.form.RuleForm;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class Rule implements EntityCore<RuleForm>
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
	
	public Rule(String name, String description, String json, String template, String sqlStr, String sqlPart)
	{
		// ruleId set to 0
		this.name=name;
		this.description=description;
		this.json=json;
		this.template=template;
		this.sqlStr=sqlStr;
		this.sqlPart=sqlPart;
	}

	@Override
	public void setId(Integer id) {
		setRuleId(id);
	}

	@Override
	public RuleForm newForm() {
		return new RuleForm(this);
	}
}
