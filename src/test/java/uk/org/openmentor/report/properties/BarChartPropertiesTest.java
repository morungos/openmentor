package uk.org.openmentor.report.properties;

import junit.framework.TestCase;

import uk.org.openmentor.report.properties.ReportBarChartProperties;
import uk.org.openmentor.report.properties.ReportChartProperties;

public class BarChartPropertiesTest extends TestCase
{
  private static final String   TEST_INIT_X_AXIS_LABEL = "test x-axis label";
  private static final String   TEST_INIT_Y_AXIS_LABEL = "test y-axis label";
  private static final String   TEST_INIT_LEGEND_LABEL = "test legend label";
  private static final int[]    TEST_INIT_DATA         = { 4, 5, 6 };
  private static final String[] TEST_INIT_AXIS_LABELS  = { "X", "Y", "Z" };

  private static final String   TEST_X_AXIS_LABEL = "x-axis label";
  private static final String   TEST_Y_AXIS_LABEL = "y-axis label";
  private static final String   TEST_LEGEND_LABEL = "legend label";
  private static final int[]    TEST_DATA         = { 1, 2, 3 };
  private static final String[] TEST_AXIS_LABELS  = { "AX", "BY", "CZ" };
  
  private ReportBarChartProperties p1_;
  private ReportBarChartProperties p2_;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    p1_ = new ReportBarChartProperties( TEST_INIT_X_AXIS_LABEL, 
                                    TEST_INIT_Y_AXIS_LABEL, 
                                    TEST_INIT_LEGEND_LABEL,
                                    TEST_INIT_DATA, 
                                    TEST_INIT_AXIS_LABELS,
                                    ChartPropertiesTest.TEST_INIT_TITLE,
                                    ChartPropertiesTest.TEST_INIT_WIDTH,
                                    ChartPropertiesTest.TEST_INIT_HEIGHT );
    p2_ = new ReportBarChartProperties();
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
    p1_ = null;
    p2_ = null;
  }

  public void testProperties()
  {   
    assertTrue( p1_.getXAxisTitle().equals( TEST_INIT_X_AXIS_LABEL ) );
    assertTrue( p1_.getYAxisTitle().equals( TEST_INIT_Y_AXIS_LABEL ) );
    assertTrue( p1_.getLegendLabel().equals( TEST_INIT_LEGEND_LABEL ) );
    assertTrue( p1_.getData() == TEST_INIT_DATA );
    assertTrue( p1_.getAxisLabels() == TEST_INIT_AXIS_LABELS );
    assertTrue( 
      p1_.getChartTitle().equals( ChartPropertiesTest.TEST_INIT_TITLE ) 
    );
    assertTrue( p1_.getWidth() == ChartPropertiesTest.TEST_INIT_WIDTH );
    assertTrue( p1_.getHeight() == ChartPropertiesTest.TEST_INIT_HEIGHT );    

    
    assertTrue( 
      p2_.getXAxisTitle().equals( ReportBarChartProperties.DEFAULT_X_AXIS_TITLE ) 
    );
    assertTrue( 
      p2_.getYAxisTitle().equals( ReportBarChartProperties.DEFAULT_Y_AXIS_TITLE ) 
    );
    assertTrue( 
      p2_.getLegendLabel().equals( ReportBarChartProperties.DEFAULT_LEGEND_LABEL ) 
    );
    assertTrue( p2_.getData() == ReportBarChartProperties.DEFAULT_DATA );
    assertTrue( p2_.getAxisLabels() 
                == ReportBarChartProperties.DEFAULT_AXIS_LABELS );
    assertTrue( 
      p2_.getChartTitle().equals( ReportChartProperties.DEFAULT_CHART_TITLE ) 
    );
    assertTrue( p2_.getWidth() == ReportChartProperties.DEFAULT_CHART_WIDTH );
    assertTrue( p2_.getHeight() == ReportChartProperties.DEFAULT_CHART_HEIGHT );  
    
    p2_.setAxisLabels( TEST_AXIS_LABELS );
    p2_.setData( TEST_DATA );
    p2_.setLegendLabel( TEST_LEGEND_LABEL );
    p2_.setXAxisTitle( TEST_X_AXIS_LABEL );
    p2_.setYAxisTitle( TEST_Y_AXIS_LABEL );
    assertTrue( p2_.getAxisLabels().equals( TEST_AXIS_LABELS ) );
    assertTrue( p2_.getData() == TEST_DATA );
    assertTrue( p2_.getLegendLabel().equals( TEST_LEGEND_LABEL ) );
    assertTrue( p2_.getXAxisTitle().equals( TEST_X_AXIS_LABEL ) );
    assertTrue( p2_.getYAxisTitle().equals( TEST_Y_AXIS_LABEL ) );                                          
  }
}
