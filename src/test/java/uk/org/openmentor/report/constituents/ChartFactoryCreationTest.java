package uk.org.openmentor.report.constituents;

import junit.framework.TestCase;

import junit.framework.Assert;

import uk.org.openmentor.report.exceptions.*;

public class ChartFactoryCreationTest extends TestCase
{
  public void testFactoryCreation()
  {
    try {
      ChartFactory cf = new ChartFactory( "jCharts" );
      assertNotNull(cf);
    } catch ( NoSuchChartLibException nscle ) {
      Assert.fail( "Threw NoSuchChartLibException when creating a " +
                   "ChartFactory instance with legitimate ID (jCharts)" );
    }
    
    try {
      ChartFactory cf2 = new ChartFactory( "Some other chart lib" );
      assertNotNull(cf2);
      Assert.fail( "Failed to throw NoSuchChartLibException when creating " + 
      "a ChartFactory instance with false library id" );
    } catch ( NoSuchChartLibException nscle ) {
    }
  }
}
