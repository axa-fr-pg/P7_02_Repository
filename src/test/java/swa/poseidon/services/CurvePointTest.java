package swa.poseidon.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import swa.poseidon.form.CurvePointForm;
import swa.poseidon.model.CurvePoint;
import swa.poseidon.repositories.CurvePointRepository;

@SpringBootTest
public class CurvePointTest
{
	EntityServiceTest <CurvePoint,CurvePointForm> entityServiceTest = new EntityServiceTest <CurvePoint,CurvePointForm>(CurvePoint::new);
	
	@MockBean
	private CurvePointRepository repository;
	
	@Autowired
	private CurvePointService service;
	
	@Test
	public void givenEntityList_readAll_returnsCorrectFormList()
	{
		entityServiceTest.givenEntityList_readAll_returnsCorrectFormList(repository, service);
	}
	
	@Test
	public void givenValidForm_create_generatesNewId()
	{
		entityServiceTest.givenValidForm_create_generatesNewId(repository, service);
	}

	@Test
	public void givenExistingEntity_read_returnsCorrectEntity()
	{
		entityServiceTest.givenExistingEntity_read_returnsCorrectEntity(repository, service);
	}

	@Test
	public void givenEntityNotFound_read_throwsNoSuchElementException()
	{
		entityServiceTest.givenEntityNotFound_read_throwsNoSuchElementException(repository, service);
	}

	@Test
	public void givenForm_update_returnsSavedEntity()
	{
		entityServiceTest.givenForm_update_returnsSavedEntity(repository, service);
	}

	@Test
	public void givenExistingEntity_delete_returnsTrue()
	{
		entityServiceTest.givenExistingEntity_delete_returnsTrue(repository, service);
	}

	@Test
	public void givenEntityNotFound_delete_throwsNoSuchElementException()
	{
		entityServiceTest.givenEntityNotFound_delete_throwsNoSuchElementException(repository, service);
	}

}
