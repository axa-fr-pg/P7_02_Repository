package swa.poseidon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swa.poseidon.form.RuleForm;
import swa.poseidon.model.Rule;
import swa.poseidon.services.RuleService;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/rules")
public class RuleController extends EntityController<Rule,RuleForm>
{
	@Autowired
	RuleService autowiredService;

	@PostConstruct
	public void injectService()
	{
		super.entityService = autowiredService;
	}
}
