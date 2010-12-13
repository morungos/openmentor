package uk.org.openmentor.report.properties;

import junit.framework.TestCase;

import uk.org.openmentor.report.properties.ReportChartProperties;

public class ChartPropertiesTest extends TestCase
{
  public static final String TEST_INIT_TITLE  = "test init title";
  public static final int    TEST_INIT_WIDTH  = 50;
  public static final int    TEST_INIT_HEIGHT = 60;
  public static final String TEST_TITLE       = "test title";
  public static final int    TEST_WIDTH       = 110;
  public static final int    TEST_HEIGHT      = 120;
  
  private ReportChartProperties p1_;
  private ReportChartProperties p2_;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    p1_ = new ReportChartProperties();
    p2_ = new ReportChartProperties( TEST_INIT_TITLE, TEST_INIT_WIDTH, 
                                 TEST_INIT_HEIGHT );
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
    p1_ = null;
    p2_ = null;
  }

  public void testProperties()
  {
    assertTrue( 
      p1_.getChartTitle().equals( ReportChartProperties.DEFAULT_CHART_TITLE ) 
    );
    assertTrue( p1_.getWidth() == ReportChartProperties.DEFAULT_CHART_WIDTH );
    assertTrue( p1_.getHeight() == ReportChartProperties.DEFAULT_CHART_HEIGHT );
    assertTrue( p2_.getChartTitle().equals( TEST_INIT_TITLE ) );
    assertTrue( p2_.getWidth() == TEST_INIT_WIDTH );
    assertTrue( p2_.getHeight() == TEST_INIT_HEIGHT );
    
    p2_.setChartTitle( TEST_TITLE );
    p2_.setWidth( TEST_WIDTH );
    p2_.setHeight( TEST_HEIGHT );
    assertTrue( p2_.getChartTitle().equals( TEST_TITLE ) );
    assertTrue( p2_.getWidth() == TEST_WIDTH );
    assertTrue( p2_.getHeight() == TEST_HEIGHT );
  }
}
