package swa.poseidon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import swa.poseidon.form.CurvePointForm;
import swa.poseidon.model.CurvePoint;
import swa.poseidon.repositories.CurvePointRepository;

@SpringBootTest
public class CurvePointServiceTest {
	@MockBean
	private CurvePointRepository curvePointRepository;
	
	@Autowired
	private CurvePointService curvePointService;
	
	static public CurvePoint newValidCurvePointForTest(int index) 
	{
		return new CurvePoint( index, new BigDecimal(index*11), new BigDecimal(index*12));
	}
	
	@Test
	public void givenCurvePointFound_read_returnsCorrectCurvePoint() {
		// GIVEN
		CurvePoint testValue1 =  newValidCurvePointForTest(1);
		when(curvePointRepository.findById(testValue1.getCurvePointId())).thenReturn(Optional.of(testValue1));
		// WHEN
		CurvePoint result = curvePointService.read(testValue1.getCurvePointId());
		// THEN
		assertNotNull(result);
		assertEquals(testValue1.getCurvePointId(), result.getCurvePointId());
	}
	
	@Test
	public void givenCurvePointNotFound_read_returnsNoCurvePoint() {
		// GIVEN
		CurvePoint testValue1 =  newValidCurvePointForTest(1);
		when(curvePointRepository.findById(testValue1.getCurvePointId())).thenReturn(null);
		// WHEN
		CurvePoint result = curvePointService.read(testValue1.getCurvePointId());
		// THEN
		assertNull(result);
	}
	
	@Test
	public void givenCurvePointFound_delete_returnsTrue() {
		// GIVEN
		CurvePoint testValue1 =  newValidCurvePointForTest(1);
		when(curvePointRepository.findById(testValue1.getCurvePointId())).thenReturn(Optional.of(testValue1));
		// WHEN
		boolean result = curvePointService.delete(testValue1.getCurvePointId());
		// THEN
		assertTrue(result);
	}
	
	@Test
	public void givenCurvePointNotFound_delete_returnsFalse() {
		// GIVEN
		CurvePoint testValue1 =  newValidCurvePointForTest(1);
		when(curvePointRepository.findById(testValue1.getCurvePointId())).thenReturn(null);
		// WHEN
		boolean result = curvePointService.delete(testValue1.getCurvePointId());
		// THEN
		assertFalse(result);
	}	
}
