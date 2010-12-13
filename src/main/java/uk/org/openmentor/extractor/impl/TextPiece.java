/*
 ====================================================================
 Copyright 2002-2004   Apache Software Foundation

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ====================================================================
 */

package uk.org.openmentor.extractor.impl;

import uk.org.openmentor.extractor.impl.util.PropertyNode;

/**
 * Comment me
 *
 * @author Ryan Ackley 
 */

public class TextPiece extends PropertyNode implements Comparable {
    /** Marker to say whether this uses Unicode. */
    private boolean usesUnicode;

    /** The physical length. */
    private int physical;

    /**
     * Create a new TextPiece with the given settings
     *
     * @param start         the start of the text span
     * @param length        the length of the text span
     * @param physicalSize  the physical size of the text span
     * @param unicode       set to true if the text is Unicode
     */
    public TextPiece(final int start, 
                     final int length, 
                     final int physicalSize, 
                     final boolean unicode) {
        super(start, start + length, null);
        usesUnicode = unicode;
        physical = physicalSize;
    }

    /**
     * Returns true if this text span uses Unicode.
     * @return              boolean Unicode status
     */
    final public boolean usesUnicode() {
        return usesUnicode;
    }

    /**
     * Returns the physical length of the text span.
     * @return              the length of the text span
     */
    final public int getPhysical() {
        return physical;
    }
}
