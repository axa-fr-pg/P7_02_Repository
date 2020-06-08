package swa.poseidon.services;

import java.util.List;

public interface EntityService<E,F> 
{
	public List<F> readAllForms();

	public E createByForm(F f);
	
	public E read(Integer eId);
	
	public E updateByForm(F f);
	
	public boolean delete(Integer eId);
}
