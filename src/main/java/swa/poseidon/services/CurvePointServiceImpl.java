package swa.poseidon.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.CurvePoint;
import swa.poseidon.repositories.CurvePointRepository;

@Service
@Transactional
public class CurvePointServiceImpl implements CurvePointService 
{
	@Autowired CurvePointRepository curvePointRepository;

	@Override
	public CurvePoint create(CurvePoint curvePoint) 
	{
		curvePoint.setCurvePointId(0);
		return curvePointRepository.save(curvePoint);
	}

	@Override
	public CurvePoint read(Integer curvePointId) 
	{
		Optional<CurvePoint> curvePoint = curvePointRepository.findById(curvePointId);
		if (curvePoint == null) return null;
		else return curvePoint.get();
	}

	@Override
	public CurvePoint update(CurvePoint curvePoint) 
	{
		return curvePointRepository.save(curvePoint);
	}

	@Override
	public boolean delete(Integer curvePointId) {
		CurvePoint curvePoint = read(curvePointId);
		if (curvePoint == null) return false;
		curvePointRepository.delete(curvePoint);
		return true;
	}
}
