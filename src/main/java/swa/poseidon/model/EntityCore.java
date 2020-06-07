package swa.poseidon.model;

public interface EntityCore<F> 
{
	public void setId(Integer id);
	public F toForm();
	public EntityCore<F> newValidTestEntityWithIdZero(int index);
	public EntityCore<F> newValidTestEntityWithGivenId(int index);
	public EntityCore<F> newInvalidTestEntity();
}
