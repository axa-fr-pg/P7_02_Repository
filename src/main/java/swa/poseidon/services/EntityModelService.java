package swa.poseidon.services;

public interface EntityModelService<F> 
{
	public void setId(Integer id);
	
	public F newForm();
}
