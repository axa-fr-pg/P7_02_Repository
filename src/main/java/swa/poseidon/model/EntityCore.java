package swa.poseidon.model;

public interface EntityCore<F> 
{
	public void setId(Integer id);
	public F toForm();
	public EntityCore<F> newTestEntityWithIdZero(int index);
	public EntityCore<F> newTestEntityWithGivenId(int index);
}
