/* =======================================================================
 * Copyright 2004-2006 The OpenMentor Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */


/*
 * Substantially modified by Stuart Watt to implement text extraction.
 * A lot of this we do not need, and may therefore dispense with.
 * Non-complex files are now more or less irrelevant, so we can forget
 * them for the moment. On the other hand, a good amount of the code
 * here is incorrect in small details, and needs to be fixed.
 *
 * Much of the problem revolves around file offsets or FCs. These are
 * not always as they should be. fcMin is used, yet it should not really
 * be useful to complex files. 
 */

package uk.org.openmentor.extractor.impl;

import uk.org.openmentor.extractor.Extractor;
import uk.org.openmentor.extractor.impl.util.BTreeSet;
import uk.org.openmentor.extractor.impl.util.PropertyNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.poifs.filesystem.DocumentEntry;

import org.apache.poi.util.LittleEndian;

public class ExtractStandard implements Extractor {
    /** Byte buffer containing the main Document stream. */
    private byte[] header;

    /** Document's text blocks. */
    private BTreeSet text = new BTreeSet();

    /** Length of main document text stream. */
    private int ccpText;

    /** Length of footnotes text. */
    private int ccpFtn;

    /** Length of header text. */
    private int ccpHdd;

    /** Length of annotation text. */
    private int ccpAtn;

    /*
     * Annotations in a few cases may contain fields. Actually, so may all the
     * text, which is irritating. So we need a way of skipping out all the
     * field stuff, and for this, regular expressions are a good solution.
     * Essentially, we should look for ASCII 19, which starts a field, and
     * ASCII 21, which ends the field. Delete these, and all other text, except
     * stuff which follows ASCII 20, which is the text view.
     */
    private Pattern fieldPattern = Pattern
            .compile("\\x13[^\\x14\\x15]*(?:\\x14([^\\x15]*))?\\x15");

    private Pattern cleanPattern = Pattern.compile("\\x0b");

    /** OLE stuff*/
    private InputStream istream;

    /** OLE stuff*/
    private POIFSFileSystem filesystem;

    private static Log log = LogFactory.getLog(ExtractStandard.class);

    public ExtractStandard() {
    }

    /**
     * Constructs a Word document from the input. Parses the document and places
     * all the important stuff into data structures.
     *
     * @param stream The stream representing the original file
     * @throws IOException if there is a problem while parsing the document.
     */
    public synchronized void extract(InputStream stream) throws IOException {
        extractStream(stream);
    }
    
    /*
     * Most of this code is broken - in a complex file, you can't assume that
     * the file offset can be calculated in this way. Instead, we should not use
     * fcMin, but use the piece table to calculate the offsetting. This is best
     * done once and cached.
     */

    private void extractStream(InputStream inputStream) throws IOException {
        //Clear the text buffer
        text.clear();
        
        //do OLE stuff
        istream = inputStream;
        filesystem = new POIFSFileSystem(istream);

        //get important stuff from the Header block and parse all the
        //data structures
        readFIB();
        
        istream.close();
    }

    /**
     * Extracts the main document stream from the POI file then hands off to other
     * functions that parse other areas.
     *
     * @throws IOException
     */
    private void readFIB() throws IOException {
        //get the main document stream
        DocumentEntry headerProps = (DocumentEntry) filesystem.getRoot()
                .getEntry("WordDocument");

        //I call it the header but its also the main document stream
        header = new byte[headerProps.getSize()];
        InputStream input = filesystem.createDocumentInputStream("WordDocument");
        input.read(header);
        input.close();
        
        if (log.isTraceEnabled()) {
            log.trace("Got datablock, length: "+header.length);
        }

        //Get the information we need from the header
        int info = LittleEndian.getShort(header, 0xa);

        ccpText = LittleEndian.getInt(header, 0x4c);
        ccpFtn = LittleEndian.getInt(header, 0x50);
        ccpHdd = LittleEndian.getInt(header, 0x54);
        ccpAtn = LittleEndian.getInt(header, 0x5c);

        int charPLC = LittleEndian.getInt(header, 0xfa);
        int charPlcSize = LittleEndian.getInt(header, 0xfe);
        int parPLC = LittleEndian.getInt(header, 0x102);
        int parPlcSize = LittleEndian.getInt(header, 0x106);
        boolean useTable1 = (info & 0x200) != 0;

        //process the text and formatting properties
        processComplexFile(useTable1, charPLC, charPlcSize, parPLC, parPlcSize);
    }

    /**
     * Extracts the correct Table stream from the POI filesystem then hands off to
     * other functions to process text and formatting info. the name is based on
     * the fact that in Word 8(97) all text (not character or paragraph formatting)
     * is stored in complex format.
     *
     * @param useTable1 boolean that specifies if we should use table1 or table0
     * @param charTable offset in table stream of character property bin table
     * @param charPlcSize size of character property bin table
     * @param parTable offset in table stream of paragraph property bin table.
     * @param parPlcSize size of paragraph property bin table.
     * @return boolean indocating success of
     * @throws IOException
     */
    private void processComplexFile(boolean useTable1, int charTable,
            int charPlcSize, int parTable, int parPlcSize) throws IOException {

        //get the location of the piece table
        int complexOffset = LittleEndian.getInt(header, 0x1a2);

        String tablename = null;
        DocumentEntry tableEntry = null;
        if (useTable1) {
            tablename = "1Table";
        } else {
            tablename = "0Table";
        }
        tableEntry = (DocumentEntry) filesystem.getRoot().getEntry(tablename);

        //load the table stream into a buffer
        int size = tableEntry.getSize();
        byte[] tableStream = new byte[size];
        filesystem.createDocumentInputStream(tablename).read(tableStream);

        findText(tableStream, complexOffset);
    }

    public String getBody() {
        return getText(0, ccpText);
    }

    public Set getAnnotations() {
        String result = getText(ccpText + ccpFtn + ccpHdd, ccpAtn);

        /*
         * Splitting needs to be handled carefully. In practice, annotation
         * marks identified by ASCII 05, but these may occur after a field (!).
         * There is a proper way to handle annotations, which involves using a
         * different table elsewhere in the Word file, but this also needs to be
         * decoded and it is a lot of work for little gain.
         */
        String[] all = result.split("\005");
        Set<String> values = new HashSet<String>();
        for (int x = 1; x < all.length; x++) {
            values.add(removeFields(all[x]).trim());
        }
        return (Set) values;
    }

    private String removeFields(String text) {
        /*
         * Amended to remove all field information - even the visible text. This
         * is a bad idea in one sense - strictly it is invalid, as we are not
         * really correct about annotation boundaries. But in another sense,
         * fields are not written by people, so we can probably ignore them
         * validly anyway.
         */
        String result = fieldPattern.matcher(text).replaceAll("");
        result = cleanPattern.matcher(result).replaceAll("");
        if (log.isTraceEnabled()) {
            log.trace("Removed field: " + result);
        }
        return result;
    }

    private String getText(int start, int length) {
        List<PropertyNode> result = getTextChunks(start, length, text);
        StringBuffer buf = new StringBuffer();
        int textSize = result.size();
        int oStart = -1;

        for (int z = 0; z < textSize; z++) {

            TextPiece piece = (TextPiece) result.get(z);
            int pStart = piece.getStart();
            int pEnd = piece.getEnd();
            int textStart = piece.getPhysical();
            int textEnd = textStart + (pEnd - pStart);

            oStart = Math.max(oStart, pStart);
            
            if (log.isTraceEnabled()) {
                log.trace("Adding text from "+textStart+" to "+textEnd);
            }

            if (piece.usesUnicode()) {
                addUnicodeText(textStart, textEnd, buf);
            } else {
                addText(textStart, textEnd, buf);
            }

        }
        String theResult = buf.toString();
        start = start - oStart;

        if (log.isTraceEnabled()) {
            log.trace("Substring from " + start + " to " + (start + length));
        }

        return theResult.substring(start, start + length);
    }

    private List<PropertyNode> getTextChunks(int start, int length, BTreeSet set) {
        BTreeSet.BTreeNode theRoot = set.root;
        List<PropertyNode> results = new ArrayList<PropertyNode>();
        int end = start + length;

        BTreeSet.Entry[] entries = theRoot.entries;
        if (log.isTraceEnabled()) {
            log.trace("Start: " + start);
            log.trace("End: " + end);
            log.trace("Count of entries: " + entries.length);
        }

        for (Iterator i = set.iterator(); i.hasNext();) {
            PropertyNode xNode = (PropertyNode) i.next();
            int xStart = xNode.getStart();
            int xEnd = xNode.getEnd();
            if (log.isTraceEnabled()) {
                log.trace("Entry: start=" + xStart + ", end=" + xEnd);
            }
            if (xStart < end) {
                if (start < xEnd) {
                    if (log.isTraceEnabled()) {
                        log.trace("Adding entry");
                    }
                    results.add(xNode);
                }
            } else {
                break;
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Done");
        }
        return results;
    }

    /*
     * Somewhere round here there is an off by 512 error, at least when a 
     * header appears. The header is not being found properly, and we are
     * getting text from 512 bytes earlier than we should. The data from
     * findText seems to be more or less OK. 
     */
    private void findText(byte[] tableStream, int complexOffset)
            throws IOException {
        //actual text
        int pos = complexOffset;
        //skips through the prms before we reach the piece table. These contain data
        //for actual fast saved files
        //System.out.println("pos1: "+pos);
        while (tableStream[pos] == 1) {
            pos++;
            int skip = LittleEndian.getShort(tableStream, pos);
            pos += 2 + skip;
        }
        //System.out.println("pos2: "+pos);
        if (tableStream[pos] != 2) {
            throw new IOException("corrupted Word file");
        } else {
            //parse out the text pieces
            //System.out.println("pos3: "+pos);
            int pieceTableSize = LittleEndian.getInt(tableStream, ++pos);
            pos += 4;
            int pieces = (pieceTableSize - 4) / 12;
            //System.out.println("tab: "+pos);
            int start = 0;
            for (int x = 0; x < pieces; x++) {
                int filePos = LittleEndian.getInt(tableStream, pos
                        + ((pieces + 1) * 4) + (x * 8) + 2);
                if (log.isTraceEnabled()) {
                    log.trace("POS: 0x"+Integer.toHexString(filePos));
                }
                boolean unicode = false;
                if ((filePos & 0x40000000) == 0) {
                    unicode = true;
                } else {
                    unicode = false;
                    filePos &= ~(0x40000000);//gives me FC in doc stream
                    filePos /= 2;
                }
                int lStart = LittleEndian.getInt(tableStream, pos + (x * 4));
                int lEnd = LittleEndian.getInt(tableStream, pos + (x + 1) * 4);
                int totLength = lEnd - lStart;

                if (log.isTraceEnabled()) {
                    log.trace("Piece: " + (1 + x) + ", start=" + start
                            + ", len=" + totLength + ", phys=" + filePos
                            + ", uni=" + unicode);
                }
                TextPiece piece = new TextPiece(start, totLength, filePos,
                        unicode);
                start = start + totLength;
                text.add(piece);
            }
        }
    }

    /**
     * Method to add a block of characters to a buffer. 
     * 
     * @param start Beginning of the sequence to add (in file offset)
     * @param end End of the sequence to add (in file offset)
     * @param buf Buffer to add the characters to
     */
    private void addText(int start, int end, StringBuffer buf) {
        for (int x = start; x < end; x++) {
            char ch = '?';

            int theCharacter = header[x];

            if (theCharacter < 0) {
                theCharacter += 255;
                if ((theCharacter == 145) || (theCharacter == 146)) {
                    ch = '\'';
                } else if ((theCharacter == 147) || (theCharacter == 148)) {
                    ch = '\"';
                } else {
                    ch = (char) theCharacter;
                }
            } else {
                ch = (char) theCharacter;
            }

            //System.out.println("Adding: "+ch+" from offset: "+x);

            buf.append(ch);
        }
    }

    /**
     * Method to add a block of unicode characters to a buffer. 
     * 
     * @param start Beginning of the sequence to add (in file offset)
     * @param end End of the sequence to add (in file offset)
     * @param buf Buffer to add the characters to
     */
    private void addUnicodeText(final int start, 
                                final int end, 
                                final StringBuffer buf) {
        for (int x = start; x < end; x += 2) {
            char ch = Utils.getUnicodeCharacter(header, x);
            buf.append(ch);
        }
    }
}

