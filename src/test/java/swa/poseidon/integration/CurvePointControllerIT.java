package swa.poseidon.integration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import swa.poseidon.form.CurvePointForm;
import swa.poseidon.model.CurvePoint;
import swa.poseidon.repositories.CurvePointRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerIT extends EntityControllerIT<CurvePoint,CurvePointForm>
{
	@Autowired
	private CurvePointRepository autowiredRepository;
	
	@Autowired
	private MockMvc autowiredMvc;
	
	@PostConstruct
	public void injectEntityPropertiesIntoSuper()
	{
		entityRepository = autowiredRepository;
		entity = new CurvePoint();
		entityCore = entity;
		mvc = autowiredMvc;
		entityRootRequestMapping = "/curvePoints";
		numberOfEntityFieldsToValidate = 3;
	}
}
