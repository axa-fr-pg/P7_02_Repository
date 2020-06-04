package swa.poseidon.services;

import org.springframework.stereotype.Service;
import swa.poseidon.form.CurvePointForm;
import swa.poseidon.model.CurvePoint;

@Service
public class CurvePointServiceImpl extends EntityCrudServiceImpl<CurvePoint,CurvePointForm> implements CurvePointService 
{
}
