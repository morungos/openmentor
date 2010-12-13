package uk.org.openmentor.report.constituents;

import junit.framework.TestCase;

import uk.org.openmentor.report.constituents.Table;

public class TableTest extends TestCase
{
  private static final int[][]  INIT_DATA        = { { 1, 2, 3, 4 }, 
                                                     { 2, 4, 6, 8 }, 
                                                     { 3, 6, 9, 12 } };
  private static final int      INIT_COLS        = 4;
  private static final int      INIT_ROWS        = 3;
  private static final String[] INIT_COL_TITLES  = { "A", "B", "C", "D" };
  private static final String[] INIT_ROW_TITLES  = { "1", "2", "3" };
  private static final String   INIT_TABLE_TITLE = "Test table";
  private static final String[] INIT_COL_TITLES_2 = { "A", "B", "C" };
  private static final String[] INIT_ROW_TITLES_2 = { "1", "2" };
  
  private Table t1_;
  private Table t2_;
  private Table t3_;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    t1_ = new Table( INIT_DATA, INIT_COL_TITLES, INIT_ROW_TITLES,
                     INIT_TABLE_TITLE );
    t2_ = new Table( INIT_DATA, INIT_COL_TITLES_2, INIT_ROW_TITLES_2,
                     INIT_TABLE_TITLE );
    t3_ = new Table( INIT_DATA, null, null, null );
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
    t1_ = null;
    t2_ = null;
    t3_ = null;
  }

  public void testSetup()
  {
    assertTrue( t1_ != null );
    assertTrue( t1_.numColumns() == INIT_COLS );
    assertTrue( t1_.numRows() == INIT_ROWS );
    
    assertTrue( t1_.getColumnTitles() == INIT_COL_TITLES );
    assertTrue( t1_.getRowTitles() == INIT_ROW_TITLES );
    assertTrue( t1_.getTableTitle() == INIT_TABLE_TITLE );
  }
  
  public void testNullAndIncompleteParams()
  {
    assertTrue( t2_.getColumnTitles()[ 2 ].equals( INIT_COL_TITLES_2[ 2 ] ) );
    assertTrue( t2_.getColumnTitles()[ 3 ].equals( "" ) );
    assertTrue( t2_.getRowTitles()[ 1 ].equals( INIT_ROW_TITLES_2[ 1 ] ) );
    assertTrue( t2_.getRowTitles()[ 2 ].equals( "" ) );
    assertTrue( checkArrayEmpty( t3_.getColumnTitles() ) );
    assertTrue( checkArrayEmpty( t3_.getRowTitles() ) );
    assertTrue( t3_.getTableTitle().equals( "" ) );
  }
  
  public void testDataRetrieval()
  {
    assertTrue( t1_.dataItemAt( 1, 1 ) == INIT_DATA[ 0 ][ 0 ] );
    assertTrue( t1_.dataItemAt( 3, 2 ) == INIT_DATA[ 2 ][ 1 ] );
  }
  
  private boolean checkArrayEmpty( String[] a )
  {
    int i = 0;
    
    for ( ; i != a.length; ++i ) {
      if ( a[ i ].equals( "" ) == false ) {
        break;
      }
    }
    
    return ( i == a.length );
  }
}
