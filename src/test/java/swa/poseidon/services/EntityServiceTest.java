package swa.poseidon.services;

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
import java.util.function.Supplier;

public class EntityServiceTest<E,F>
{
	private Supplier<E> supplier;
	private E entity;
	private EntityCore<F> entityCore;
	
	@SuppressWarnings("unchecked")
	public EntityServiceTest(Supplier<E> contructorSupplier) 
	{
	    supplier = contructorSupplier;
	    entity = supplier.get();
	    entityCore = (EntityCore<F>) entity;
	}
	
	@SuppressWarnings("unchecked")
	public void givenEntityList_readAll_returnsCorrectFormList(JpaRepository<E,Integer> repository, EntityService<E,F> service) 
	{
		// GIVEN
		E e1 =  (E) entityCore.newTestEntityWithGivenId(1);
		E e2 =  (E) entityCore.newTestEntityWithGivenId(2);
		E e3 =  (E) entityCore.newTestEntityWithGivenId(3);
		List<E> entityList = (List<E>) Arrays.asList(e1, e2, e3);
		when(repository.findAll()).thenReturn(entityList);
		// WHEN
		List<F> formList = service.readAll();
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
	
	@SuppressWarnings("unchecked")
	public void givenValidForm_create_generatesNewId(JpaRepository<E,Integer> repository, EntityService<E,F> service)
	{
		// GIVEN
		E expected =  (E) entityCore.newTestEntityWithGivenId(1);
		F given =  (F) entityCore.newTestEntityWithIdZero(1).toForm();
		when(repository.save(any((Class<E>)entity.getClass()))).thenReturn(expected);
		// WHEN
		EntityCore<F> resultEntity = (EntityCore<F>) service.create(given);
		// THEN
		assertNotNull(resultEntity);
		FormCore<E> resultForm = (FormCore<E>) resultEntity.toForm();
		assertEquals(1, resultForm.id());
	}
	
	@SuppressWarnings("unchecked")
	public void givenExistingEntity_read_returnsCorrectEntity(JpaRepository<E,Integer> repository, EntityService<E,F> service) 
	{
		// GIVEN
		E expected =  (E) entityCore.newTestEntityWithGivenId(1);
		when(repository.findById(1)).thenReturn(Optional.of(expected));
		// WHEN
		EntityCore<F> resultEntity = (EntityCore<F>) service.read(1);
		// THEN
		assertNotNull(resultEntity);
		FormCore<E> resultForm = (FormCore<E>) resultEntity.toForm();
		assertEquals(1, resultForm.id());
	}
	
	public void givenEntityNotFound_read_throwsNoSuchElementException(JpaRepository<E,Integer> repository, EntityService<E,F> service) 
	{
		// GIVEN
		Integer id = 999;
		when(repository.findById(id)).thenReturn(Optional.empty());
		// WHEN & THEN
		assertThrows(NoSuchElementException.class, () -> service.read(id));
	}
	
	@SuppressWarnings("unchecked")
	public void givenForm_update_returnsSavedEntity(JpaRepository<E,Integer> repository, EntityService<E,F> service)
	{
		// GIVEN
		E expected =  (E) entityCore.newTestEntityWithGivenId(1);
		F given =  (F) entityCore.newTestEntityWithGivenId(1).toForm();
		when(repository.findById(1)).thenReturn((Optional<E>) Optional.of(expected));
		when(repository.save(any((Class<E>)entity.getClass()))).thenReturn(expected);
		// WHEN
		EntityCore<F> resultEntity = (EntityCore<F>) service.update(given);
		// THEN
		assertNotNull(resultEntity);
		FormCore<E> resultForm = (FormCore<E>) resultEntity.toForm();
		assertEquals(1, resultForm.id());
	}
		
	
	public void givenExistingEntity_delete_returnsTrue(JpaRepository<E,Integer> repository, EntityService<E,F> service) 
	{
		// GIVEN
		@SuppressWarnings("unchecked")
		E expected =  (E) entityCore.newTestEntityWithGivenId(1);
		when(repository.findById(1)).thenReturn(Optional.of(expected));
		// WHEN
		boolean result = service.delete(1);
		// THEN
		assertTrue(result);
	}
	
	public void givenEntityNotFound_delete_throwsNoSuchElementException(JpaRepository<E,Integer> repository, EntityService<E,F> service) 
	{
		// GIVEN
		Integer id = 999;
		when(repository.findById(id)).thenReturn(Optional.empty());
		// WHEN & THEN
		assertThrows(NoSuchElementException.class, () -> service.delete(id));
	}	
}
