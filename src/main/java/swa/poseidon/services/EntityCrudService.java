package swa.poseidon.services;

import java.util.List;

public interface EntityCrudService<E,F> 
{
	public List<F> readAll();

	public E create(E e);
	
	public E read(Integer eId);
	
	public E update(E e);
	
	public boolean delete(Integer eId);
}
