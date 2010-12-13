package uk.org.openmentor.report.properties;

import junit.framework.TestCase;

import uk.org.openmentor.report.properties.ReportClusteredBarChartProperties;
import uk.org.openmentor.report.properties.ReportChartProperties;

public class ClusteredBarChartPropertiesTest extends TestCase
{
  private static final String   TEST_INIT_X_AXIS_LABEL  = "test x-axis label";
  private static final String   TEST_INIT_Y_AXIS_LABEL  = "test y-axis label";
  private static final String[] TEST_INIT_LEGEND_LABELS = 
  	{ "test legend label", "test legend label 2" };
  private static final int[][]  TEST_INIT_DATA          = 
  	{ { 4, 5, 6 }, { 1, 2, 3 } };
  private static final String[] TEST_INIT_AXIS_LABELS   = { "X", "Y", "Z" };

  private static final String   TEST_X_AXIS_LABEL  = "x-axis label";
  private static final String   TEST_Y_AXIS_LABEL  = "y-axis label";
  private static final String[] TEST_LEGEND_LABELS = 
  	{ "legend label", "legend label 2" };
  private static final int[][]  TEST_DATA          = 
  	{ { 1, 2, 3 }, { 4, 5, 6 } };
  private static final String[] TEST_AXIS_LABELS   = { "AX", "BY", "CZ" };
  
  private ReportClusteredBarChartProperties p1_;
  private ReportClusteredBarChartProperties p2_;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    p1_ = 
      new ReportClusteredBarChartProperties( TEST_INIT_X_AXIS_LABEL, 
                                         TEST_INIT_Y_AXIS_LABEL, 
                                         TEST_INIT_LEGEND_LABELS,
                                         TEST_INIT_DATA, 
                                         TEST_INIT_AXIS_LABELS,
                                         ChartPropertiesTest.TEST_INIT_TITLE,
                                         ChartPropertiesTest.TEST_INIT_WIDTH,
                                         ChartPropertiesTest.TEST_INIT_HEIGHT );
    p2_ = new ReportClusteredBarChartProperties();
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
    assertTrue( p1_.getLegendLabels().equals( TEST_INIT_LEGEND_LABELS ) );
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
      p2_.getLegendLabels().equals( 
        ReportClusteredBarChartProperties.DEFAULT_LEGEND_LABELS 
      ) 
    );
    assertTrue( p2_.getData() == ReportClusteredBarChartProperties.DEFAULT_DATA );
    assertTrue( p2_.getAxisLabels() 
                == ReportClusteredBarChartProperties.DEFAULT_AXIS_LABELS );
    assertTrue( 
      p2_.getChartTitle().equals( ReportChartProperties.DEFAULT_CHART_TITLE ) 
    );
    assertTrue( p2_.getWidth() == ReportChartProperties.DEFAULT_CHART_WIDTH );
    assertTrue( p2_.getHeight() == ReportChartProperties.DEFAULT_CHART_HEIGHT );  
    
    p2_.setAxisLabels( TEST_AXIS_LABELS );
    p2_.setData( TEST_DATA );
    p2_.setLegendLabels( TEST_LEGEND_LABELS );
    p2_.setXAxisTitle( TEST_X_AXIS_LABEL );
    p2_.setYAxisTitle( TEST_Y_AXIS_LABEL );
    assertTrue( p2_.getAxisLabels().equals( TEST_AXIS_LABELS ) );
    assertTrue( p2_.getData() == TEST_DATA );
    assertTrue( p2_.getLegendLabels().equals( TEST_LEGEND_LABELS ) );
    assertTrue( p2_.getXAxisTitle().equals( TEST_X_AXIS_LABEL ) );
    assertTrue( p2_.getYAxisTitle().equals( TEST_Y_AXIS_LABEL ) );                                          
  }
}
