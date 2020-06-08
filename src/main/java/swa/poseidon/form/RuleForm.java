package swa.poseidon.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Rule;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class RuleForm extends Rule implements FormCore<Rule>
{
	public RuleForm(Rule r)
	{
		super(r);
	}

	@Override
	public Rule toEntity() {
		return new Rule(this);
	}

	@Override
	public Integer id() {
		return super.getRuleId();
	}

	@Override
	public boolean matches(Rule r) {
		if (super.getRuleId().intValue() != r.getRuleId().intValue()) return false;
		if (!super.getName().equals(r.getName())) return false;
		if (!super.getDescription().equals(r.getDescription())) return false;
		if (!super.getJson().equals(r.getJson())) return false;
		if (!super.getTemplate().equals(r.getTemplate())) return false;
		if (!super.getSqlStr().equals(r.getSqlStr())) return false;
		if (!super.getSqlPart().equals(r.getSqlPart())) return false;
		return true;
	}
}
