package swa.poseidon.form;

public interface FormCore <E>
{
	public E toEntity();
	public Integer id();
	public boolean matches(E e);
}
