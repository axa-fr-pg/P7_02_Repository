package swa.poseidon.integration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import swa.poseidon.form.CurvePointForm;
import swa.poseidon.model.CurvePoint;
import swa.poseidon.repositories.CurvePointRepository;
import swa.poseidon.services.CurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerIT extends EntityControllerIT<CurvePoint,CurvePointForm>
{
	@Autowired
	private CurvePointRepository autowiredRepository;
	
	@Autowired
	private CurvePointService autowiredService;

	@Autowired
	private MockMvc autowiredMvc;
	
	@PostConstruct
	public void injectEntityProperties()
	{
		super.entityRepository = autowiredRepository;
		super.entityService = autowiredService;
		super.entity = new CurvePoint();
		super.entityCore = entity;
		super.mvc = autowiredMvc;
		entityRootRequestMapping = "/curvePoints";
	}
}
