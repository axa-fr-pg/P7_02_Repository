package swa.poseidon.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import swa.poseidon.form.CurvePointForm;
import swa.poseidon.model.CurvePoint;
import swa.poseidon.repositories.CurvePointRepository;

@SpringBootTest
public class CurvePointTest extends EntityServiceTest<CurvePoint,CurvePointForm>
{
	@MockBean
	private CurvePointRepository autowiredRepository;
	
	@Autowired
	private CurvePointService autowiredService;
	
	@PostConstruct
	public void injectEntityProperties()
	{
		super.entityRepository = autowiredRepository;
		super.entityService = autowiredService;
		super.entity = new CurvePoint();
		super.entityCore = entity;
	}
}
