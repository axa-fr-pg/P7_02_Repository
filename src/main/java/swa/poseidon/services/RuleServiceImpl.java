package swa.poseidon.services;

import org.springframework.stereotype.Service;

import swa.poseidon.form.RuleForm;
import swa.poseidon.model.Rule;

@Service
public class RuleServiceImpl extends EntityCrudServiceImpl<Rule,RuleForm> implements RuleService 
{
}
