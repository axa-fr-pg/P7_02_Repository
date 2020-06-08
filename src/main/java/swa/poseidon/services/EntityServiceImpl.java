package swa.poseidon.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.form.FormCore;
import swa.poseidon.model.EntityCore;

@Transactional
public abstract class EntityServiceImpl<E,F> implements EntityService<E,F>
{
	@Autowired JpaRepository<E, Integer> repository;
	
	@Override
	public List<F> readAllForms()
	{
		List<E> eList = (List<E>) repository.findAll();
		List<F> fList = new ArrayList<F>();
		for (E e : eList) 
		{
			@SuppressWarnings("unchecked")
			EntityCore<F> eCore = (EntityCore<F>) e;
			F f = eCore.toForm();
			fList.add(f);
		}
		return fList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E createByForm(F f) 
	{
		FormCore<E> fCore = (FormCore<E>) f;
		E e = fCore.toEntity();
		EntityCore<F> eCore = (EntityCore<F>) e;
		eCore.setId(0); // ensure that save will generate a new entry and not overwrite anything
		return repository.save((E)eCore);
	}

	@Override
	public E read(Integer eId) 
	{
		Optional<E> e = repository.findById(eId);
		return e.get(); // throws java.util.NoSuchElementException
	}

	@Override
	public E updateByForm(F f)  
	{
		@SuppressWarnings("unchecked")
		FormCore<E> fCore = (FormCore<E>) f;
		read(fCore.id()); // check if exists
		return repository.save(fCore.toEntity());
	}

	@Override
	public boolean delete(Integer eId) 
	{
		E e = read(eId);
		if (e == null) return false;
		repository.delete(e);
		return true;
	}
}
