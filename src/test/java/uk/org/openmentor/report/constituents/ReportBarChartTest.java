package uk.org.openmentor.report.constituents;

import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

import uk.org.openmentor.businesslogic.BusinessLogicException;
import uk.org.openmentor.report.constituents.ReportBarChart;
import uk.org.openmentor.report.properties.ReportBarChartProperties;


public class ReportBarChartTest extends TestCase
{
  private ReportBarChart jcb_;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    jcb_ = new ReportBarChart( new ReportBarChartProperties() );
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
    jcb_ = null;
  }

  public void testBarChart()
  {
    ByteArrayOutputStream out = null;
    
    assertTrue( jcb_ != null );
    
    try { 
    	out = new ByteArrayOutputStream();
    	out.reset();
    	jcb_.writeChartToStream(out, "image/jpeg");
    	out.reset();
    	jcb_.writeChartToStream(out, "image/png");
    } catch ( BusinessLogicException re ) {
      fail( "Report exception encountered when generating a buffered image " +
      		"of a Bar Chart" );
    }
    
    assertTrue( out.size() > 0 );
  }
}
