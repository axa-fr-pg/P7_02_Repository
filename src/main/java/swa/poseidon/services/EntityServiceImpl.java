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
	public List<F> readAll()
	{
		List<E> eList = repository.findAll();
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
	public E create(F f) 
	{
		FormCore<E> fCore = (FormCore<E>) f;
		E e = fCore.toEntity();
		EntityCore<F> eCore = (EntityCore<F>) e;
		eCore.setId(0);
		return repository.save((E)eCore);
	}

	@Override
	public E read(Integer eId) 
	{
		Optional<E> e = repository.findById(eId);
		if (e == null) return null;
		else return e.get();
	}

	@Override
	public E update(E e) 
	{
		return repository.save(e);
	}

	@Override
	public boolean delete(Integer eId) {
		E e = read(eId);
		if (e == null) return false;
		repository.delete(e);
		return true;
	}
}
