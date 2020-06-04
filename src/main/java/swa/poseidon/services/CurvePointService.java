package swa.poseidon.services;

import swa.poseidon.model.CurvePoint;

public interface CurvePointService 
{
	public CurvePoint create(CurvePoint curvePoint);
	
	public CurvePoint read(Integer curvePointId);
	
	public CurvePoint update(CurvePoint curvePoint);
	
	public boolean delete(Integer curvePointId);
}
