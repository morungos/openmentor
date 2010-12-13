package uk.org.openmentor.report.constituents;

import junit.framework.TestCase;

import uk.org.openmentor.report.properties.ReportBarChartProperties;
import uk.org.openmentor.report.constituents.ChartConstituent;
import uk.org.openmentor.report.properties.ReportClusteredBarChartProperties;

public class ChartFactoryTest extends TestCase
{
  private ChartFactory cf_;
  
  protected void setUp() throws Exception
  {
    cf_ = new ChartFactory( "jCharts" );
      
    super.setUp();
  }

  protected void tearDown() throws Exception
  {
    cf_ = null;
    super.tearDown();
  }

  public void testChartCreation()
  {
    ChartConstituent c = null;  

    c = cf_.createBarChart( new ReportBarChartProperties() ); 
    assertTrue( c != null );
    c = cf_.createClusteredBarChart( new ReportClusteredBarChartProperties() );
    assertTrue( c != null );
  }
}
