package swa.poseidon.services;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.mockito.ArgumentMatchers.any;

import swa.poseidon.form.FormCore;
import swa.poseidon.model.EntityCore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class EntityServiceTest<E,F>
{
	protected JpaRepository<E,Integer> entityRepository;
	protected EntityService<E,F> entityService;
	protected E entity;
	protected EntityCore<F> entityCore;
	
	@Test
	@SuppressWarnings("unchecked")
	public void givenEntityList_readAll_returnsCorrectFormList() 
	{
		// GIVEN
		E e1 =  (E) entityCore.newValidTestEntityWithGivenId(1);
		E e2 =  (E) entityCore.newValidTestEntityWithGivenId(2);
		E e3 =  (E) entityCore.newValidTestEntityWithGivenId(3);
		List<E> entityList = (List<E>) Arrays.asList(e1, e2, e3);
		when(entityRepository.findAll()).thenReturn(entityList);
		// WHEN
		List<F> formList = entityService.readAll();
		// THEN
		assertNotNull(formList);
		assertEquals(3, formList.size());
		FormCore<E> f1 = (FormCore<E>) formList.get(0);
		FormCore<E> f2 = (FormCore<E>) formList.get(1);
		FormCore<E> f3 = (FormCore<E>) formList.get(2);
		assertTrue(f1.matches(e1));
		assertTrue(f2.matches(e2));
		assertTrue(f3.matches(e3));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void givenValidForm_create_generatesNewId()
	{
		// GIVEN
		E expected =  (E) entityCore.newValidTestEntityWithGivenId(1);
		F given =  (F) entityCore.newValidTestEntityWithIdZero(1).toForm();
		when(entityRepository.save(any((Class<E>)entity.getClass()))).thenReturn(expected);
		// WHEN
		EntityCore<F> resultEntity = (EntityCore<F>) entityService.create(given);
		// THEN
		assertNotNull(resultEntity);
		FormCore<E> resultForm = (FormCore<E>) resultEntity.toForm();
		assertEquals(1, resultForm.id());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void givenExistingEntity_read_returnsCorrectEntity() 
	{
		// GIVEN
		E expected =  (E) entityCore.newValidTestEntityWithGivenId(1);
		when(entityRepository.findById(1)).thenReturn(Optional.of(expected));
		// WHEN
		EntityCore<F> resultEntity = (EntityCore<F>) entityService.read(1);
		// THEN
		assertNotNull(resultEntity);
		FormCore<E> resultForm = (FormCore<E>) resultEntity.toForm();
		assertEquals(1, resultForm.id());
	}
	
	@Test
	public void givenEntityNotFound_read_throwsNoSuchElementException() 
	{
		// GIVEN
		Integer id = 999;
		when(entityRepository.findById(id)).thenReturn(Optional.empty());
		// WHEN & THEN
		assertThrows(NoSuchElementException.class, () -> entityService.read(id));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void givenForm_update_returnsSavedEntity()
	{
		// GIVEN
		E expected =  (E) entityCore.newValidTestEntityWithGivenId(1);
		F given =  (F) entityCore.newValidTestEntityWithGivenId(1).toForm();
		when(entityRepository.findById(1)).thenReturn((Optional<E>) Optional.of(expected));
		when(entityRepository.save(any((Class<E>)entity.getClass()))).thenReturn(expected);
		// WHEN
		EntityCore<F> resultEntity = (EntityCore<F>) entityService.update(given);
		// THEN
		assertNotNull(resultEntity);
		FormCore<E> resultForm = (FormCore<E>) resultEntity.toForm();
		assertEquals(1, resultForm.id());
	}
		
	
	@Test
	public void givenExistingEntity_delete_returnsTrue() 
	{
		// GIVEN
		@SuppressWarnings("unchecked")
		E expected =  (E) entityCore.newValidTestEntityWithGivenId(1);
		when(entityRepository.findById(1)).thenReturn(Optional.of(expected));
		// WHEN
		boolean result = entityService.delete(1);
		// THEN
		assertTrue(result);
	}
	
	@Test
	public void givenEntityNotFound_delete_throwsNoSuchElementException() 
	{
		// GIVEN
		Integer id = 999;
		when(entityRepository.findById(id)).thenReturn(Optional.empty());
		// WHEN & THEN
		assertThrows(NoSuchElementException.class, () -> entityService.delete(id));
	}	
}
