/* ====================================================================
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
 ==================================================================== */

package uk.org.openmentor.extractor.impl;

/**
 * Utility class for the Microsoft Word text extraction code.
 * This was originally derived from the POI implementation by
 * Ryan Ackley, but has been significantly revised and modified
 * as part of the Open Mentor implementation effort.
 *
 * @author Stuart Watt
 */

public class Utils {

    public static short convertBytesToShort(byte firstByte, byte secondByte) {
        return (short) convertBytesToInt((byte) 0, (byte) 0, firstByte,
                secondByte);
    }

    public static int convertBytesToInt(byte firstByte, byte secondByte,
            byte thirdByte, byte fourthByte) {
        int firstInt = 0xff & firstByte;
        int secondInt = 0xff & secondByte;
        int thirdInt = 0xff & thirdByte;
        int fourthInt = 0xff & fourthByte;

        return (firstInt << 24) | (secondInt << 16) | (thirdInt << 8)
                | fourthInt;
    }

    public static short convertBytesToShort(byte[] array, int offset) {
        return convertBytesToShort(array[offset + 1], array[offset]);
    }

    public static int convertBytesToInt(byte[] array, int offset) {
        return convertBytesToInt(array[offset + 3], array[offset + 2],
                array[offset + 1], array[offset]);
    }

    public static int convertUnsignedByteToInt(byte b) {
        return (0xff & b);
    }

    public static char getUnicodeCharacter(byte[] array, int offset) {
        return (char) convertBytesToShort(array, offset);
    }
}