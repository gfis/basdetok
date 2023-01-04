/*  Detokenizer for Big Endian oriented architectures, for example the Olivetti M20
    @(#) $Id: PolSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2017-05-29: javadoc 1.8
    2012-09-29, Georg Fischer: from iCalendar; UTF-8 "pięć"
*/
/*
 * Copyright 2012 Dr. Georg Fischer <punctum at punctum dot kom>
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
 */

package org.teherba.basdetok;
import  org.teherba.basdetok.BaseDetokenizer;

/** Superclass for alle detokenizers which store line numbers and other data
 *  in big endian (network) byte order, that is with the most significant byte first.
 *  @author Dr. Georg Fischer
 */
public class BigEndianDetokenizer extends BaseDetokenizer {
    public final static String CVSID = "@(#) $Id: PolSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /** whether to write debugging output (iff &gt; 0) */
    protected final static int debug = 0;

    /** No-args Constructor
     */
    public BigEndianDetokenizer() {
        super();
    } // Constructor(0)

    /** Gets the BASIC line number from 2 bytes, in big or little endian mode
     *  @param lineNo 2 bytes containing the BASIC line number
     *  @return readable BASIC line number, and a space
     */
    protected int getLineNo(byte[] lineNo) {
        return (((lineNo[0] & 0xff) << 8) | (lineNo[1] & 0xff)) & 0xffff;
    } // getLineNo

    /** Gets some integer value from 2 bytes, in big or little endian mode
     *  @param int2 2 bytes containing the value
     *  @return a Java integer
     */
    protected int getInt2(byte[] int2) {
        return (((int2[0] & 0xff) << 8) | (int2[1] & 0xff)) & 0xffff;
    } // getInt2

} // BigEndianDetokenizer
