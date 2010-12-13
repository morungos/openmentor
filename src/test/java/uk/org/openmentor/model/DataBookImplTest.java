package uk.org.openmentor.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import uk.org.openmentor.model.DataBook;
import uk.org.openmentor.model.DataBookImpl;

public class DataBookImplTest extends TestCase {
	
	/** Private variable for the DataBook. */
	private DataBook book;

	/** Set up the tests. */
	protected void setUp() throws Exception {
		super.setUp();
		book = new DataBookImpl();
	}

	/** Close down the tests. */
	protected void tearDown() throws Exception {
		super.tearDown();
		book = null;
	}

	/*
	 * Test method for 'uk.org.openmentor.model.DataBookImpl.setDataPoints(List)'
	 */
	public final void testDataPoints() {
		List<String> values = new ArrayList<String>();
		values.add("A");
		values.add("B");
		book.setDataPoints(values);
		
		List retrieved = book.getDataPoints();
		assertTrue(retrieved.size() == 2);
		assertEquals(retrieved.get(0), "A");
		assertEquals(retrieved.get(1), "B");
	}

	/*
	 * Test method for 'uk.org.openmentor.model.DataBookImpl.setDataSeries(String, List)'
	 */
	public final void testSetDataSeries() {
		List<String> values = new ArrayList<String>();
		values.add("A");
		values.add("B");
		values.add("C");
		book.setDataSeries("first", values);
		
		List retrieved = book.getDataSeries("first");
		assertTrue(retrieved.size() == 3);
		assertEquals(retrieved.get(0), "A");
		assertEquals(retrieved.get(1), "B");
		assertEquals(retrieved.get(2), "C");
	}
}
