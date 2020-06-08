package swa.poseidon.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Rule;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class RuleForm extends Rule
{
	public RuleForm(Rule r)
	{
		super(r);
	}
}
