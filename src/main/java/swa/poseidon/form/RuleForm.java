package swa.poseidon.form;

import swa.poseidon.model.Rule;

public class RuleForm extends Rule
{
	public RuleForm(Rule r)
	{
		super.setRuleId(r.getRuleId());
		super.setName(r.getName());
		super.setDescription(r.getDescription());
		super.setJson(r.getJson());
		super.setTemplate(r.getTemplate());
		super.setSqlStr(r.getSqlStr());
		super.setSqlPart(r.getSqlPart());
	}
}
