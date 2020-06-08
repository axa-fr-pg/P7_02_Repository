package swa.poseidon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.form.RuleForm;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class Rule implements EntityCore<RuleForm>
{
 	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer ruleId;
	
	@NotBlank(message="name is mandatory")
	@Column(length = 125)
	String name;
	
	@NotBlank(message="description is mandatory")
	@Column(length = 125)
	String description;
	
	@NotBlank(message="json is mandatory")
	@Column(length = 125)
	String json;
	
	@NotBlank(message="template is mandatory")
	@Column(length = 125)
	String template;
	
	@NotBlank(message="sqlStr is mandatory")
	@Column(length = 125)
	String sqlStr;
	
	@NotBlank(message="sqlPart is mandatory")
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

	public Rule(Rule r)
	{
		this.ruleId=r.getRuleId();
		this.name=r.getName();
		this.description=r.getDescription();
		this.json=r.getJson();
		this.template=r.getTemplate();
		this.sqlStr=r.getSqlStr();
		this.sqlPart=r.getSqlPart();
	}

	@Override
	public void setId(Integer id) {
		ruleId=id;
	}

	@Override
	public RuleForm toForm() {
		return new RuleForm(this);
	}

	@Override
	public EntityCore<RuleForm> newValidTestEntityWithIdZero(int index) {
		return (EntityCore<RuleForm>) new Rule("name"+index, "description"+index, "json"+index, "template"+index, "sqlStr"+index, "sqlPart"+index);
	}

	@Override
	public EntityCore<RuleForm> newValidTestEntityWithGivenId(int index) {
		EntityCore<RuleForm> ec = newValidTestEntityWithIdZero(index);
		ec.setId(index);
		return ec;
	}

	@Override
	public EntityCore<RuleForm> newInvalidTestEntity() {
		return (EntityCore<RuleForm>) new Rule("", "", "", "", "", "");
	}
}
