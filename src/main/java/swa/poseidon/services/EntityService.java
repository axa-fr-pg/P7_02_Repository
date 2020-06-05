package swa.poseidon.services;

import java.util.List;

public interface EntityService<E,F> 
{
	public List<F> readAll();

	public E create(F f);
	
	public E read(Integer eId);
	
	public E update(E e);
	
	public boolean delete(Integer eId);
}
