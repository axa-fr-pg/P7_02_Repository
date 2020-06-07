package swa.poseidon.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swa.poseidon.form.CurvePointForm;
import swa.poseidon.model.CurvePoint;
import swa.poseidon.services.CurvePointService;

@RestController
@RequestMapping("/curvePoints")
public class CurveController extends EntityController<CurvePoint, CurvePointForm>
{
	@Autowired
	CurvePointService autowiredService;

	@PostConstruct
	public void injectService()
	{
		super.entityService = autowiredService;
	}
}
