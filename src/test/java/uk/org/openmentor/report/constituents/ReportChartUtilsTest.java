package uk.org.openmentor.report.constituents;

import java.awt.Paint;
import java.awt.Color;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.report.constituents.ReportChartUtils;

public class ReportChartUtilsTest extends TestCase
{
  private static Log log = LogFactory.getLog(ReportChartUtilsTest.class);
  private static final int     NUM_PAINTS              = 5;
  private static final Paint[] PAINTS_INTERNAL_CHECK   = 
  	{ Color.BLACK, Color.BLUE, Color.RED, Color.RED };
  
  public void testGeneratePaints()
  {
    assertFalse( checkPaintsUnique( PAINTS_INTERNAL_CHECK ) );
    
    Paint[] p = ReportChartUtils.generatePaints( NUM_PAINTS );
    
    assertTrue( p.length == NUM_PAINTS );
    assertTrue( checkPaintsUnique( p ) );
  }
  
  private boolean checkPaintsUnique( Paint[] p )
  {
    boolean unique = true;
    
    for ( int i = 0; i != p.length - 1 && unique; ++i ) {
      for ( int j = i + 1; j != p.length && unique; ++j ) {
        if (log.isDebugEnabled()) {
            log.debug("i = " + i + "; j = " + j + ".");
            if (p[j] == null ) {
                log.debug("Undefined color p[" + j + "].");
            }
        }
        unique = ( ( Color )p[ i ] != ( Color )p[ j ] );
        if (log.isDebugEnabled()) {
            log.debug("Unique is " + unique);
        }
      }
    }
    if (log.isDebugEnabled()) {
        log.debug("Returning " + unique);
    }
    
    return unique;
  }
}
