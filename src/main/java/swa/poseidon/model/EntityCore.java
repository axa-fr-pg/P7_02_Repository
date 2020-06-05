package swa.poseidon.model;

public interface EntityCore<F> 
{
	public void setId(Integer id);
	
	public F toForm();	
}
