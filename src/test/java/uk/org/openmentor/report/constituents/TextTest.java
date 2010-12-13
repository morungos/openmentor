package uk.org.openmentor.report.constituents;

import junit.framework.TestCase;
import uk.org.openmentor.report.constituents.Text;

public class TextTest extends TestCase
{
  private static final String INIT_TEXT = "Test text";
  
  private Text t1_;
  
  protected void setUp()
    throws Exception
  {
    super.setUp();
    t1_ = new Text( INIT_TEXT );
  }

  protected void tearDown()
    throws Exception
  {
    super.tearDown();
    t1_ = null;
  }

  public void testText()
  {
    assertTrue( t1_.getText().equals( INIT_TEXT ) ); 
  }
}
