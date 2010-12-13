package uk.org.openmentor.report.constituents;

import junit.framework.TestCase;

import uk.org.openmentor.report.constituents.ReportClusteredBarChart;
import uk.org.openmentor.report.properties.ReportClusteredBarChartProperties;

public class ReportClusteredBarChartTest extends TestCase
{
  private ReportClusteredBarChart jccb_;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    jccb_ = new ReportClusteredBarChart( new ReportClusteredBarChartProperties() );
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
    jccb_ = null;
  }

  public void testBarChart()
  {
    assertTrue( jccb_ != null );
  }
}