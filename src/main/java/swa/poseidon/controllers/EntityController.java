package swa.poseidon.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import swa.poseidon.form.FormCore;
import swa.poseidon.model.EntityCore;
import swa.poseidon.services.EntityService;

public abstract class EntityController<E,F> 
{
	protected EntityService<E,F> entityService;
	
    @GetMapping("/list")
    public List<F> list()
    {
        return entityService.readAllForms();
    }

    @PostMapping("/add")
    public ResponseEntity<F> add(@RequestBody @Valid F form) 
    {
    	@SuppressWarnings("unchecked")
		EntityCore<F> entityCore = (EntityCore<F>) entityService.createByForm(form);
        return new ResponseEntity<F>(entityCore.toForm(), HttpStatus.CREATED);
    }

    @SuppressWarnings("unchecked")
	@PutMapping("/update/{id}")
    public ResponseEntity<F> update(@PathVariable Integer id, @RequestBody @Valid F form) throws InvalidRequestException 
    {
    	FormCore<E> formCore = (FormCore<E>) form;
    	if (id == 0 || formCore.id() == null || id != formCore.id().intValue()) throw new InvalidRequestException();
    	EntityCore<F> entityCore = (EntityCore<F>) entityService.updateByForm(form);
        return new ResponseEntity<F>(entityCore.toForm(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<F> read(@PathVariable Integer id) 
    {
    	@SuppressWarnings("unchecked")
		EntityCore<F> entityCore = (EntityCore<F>) entityService.read(id);
        return new ResponseEntity<F>(entityCore.toForm(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) 
    {
        return new ResponseEntity<Boolean>(entityService.delete(id), HttpStatus.ACCEPTED);
    }
}
