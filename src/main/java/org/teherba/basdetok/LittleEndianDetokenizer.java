/*  Detokenizer for Little Endian oriented architectures, for example the Olivetti M20
    @(#) $Id$
    2012-10-09, Georg Fischer: copied from BigEndianDetokenizer
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
 *  in little endian (network) byte order, that is with the least significant byte first.
 *  The floating point representations can be tested at this
 *	<a href="http://babbage.cs.qc.cuny.edu/IEEE-754/">IEEE-754 site</a>.
 *  For example, 20.1f => 0x41A0CCCD
 *  @author Dr. Georg Fischer
 */
public class LittleEndianDetokenizer extends BaseDetokenizer {
    public final static String CVSID = "@(#) $Id$";

    /** whether to write debugging output */
    protected final static int debug = 0;

    /** No-args Constructor
     */
    public LittleEndianDetokenizer() {
        super();
    } // Constructor(0)

	/** Gets the BASIC line number from 2 bytes, in big or little endian mode
	 *	@param lineNo 2 bytes containing the BASIC line number
	 *	@return readable BASIC line number, and a space
	 */
	protected int getLineNo(byte[] lineNo) {
		return (((lineNo[1] & 0xff) << 8) | (lineNo[0] & 0xff)) & 0xffff;
	} // getLineNo

	/** Gets some integer value from 2 bytes, in big or little endian mode
	 *	@param int2 2 bytes containing the value
	 *	@return a Java integer
	 */
	protected int getInt2(byte[] int2) {
		return (((int2[1] & 0xff) << 8) | (int2[0] & 0xff)) & 0xffff;
	} // getInt2

	/** Shifts the bytes from big endian to litte endian order, that is
	 *  the byte order is reversed.
	 *  The method works in place.
	 *	@param floatn 4 or 8 bytes
	 *  @param len number of bytes in array <em>floatn</em>
	 */
	protected void prepareFloat(byte[] floatn, int len) {
		int isrc = 0;
		int itar = len - 1;
		byte temp = 0;
		while (isrc < itar) {
			temp = floatn[itar + 0];
			floatn[itar + 0] = floatn[isrc + 0];
			floatn[isrc + 0] = temp;
			isrc += 1;
			itar -= 1;
		} // while isrc
	} // prepareFloat

    /** Initializes the tokenizer, especially: sets the token list.
     *  This method will be overridden by the class for the specific BASIC dialect.
     */
    protected void initialize() {
		super.initialize();
    } // initialize

    /** Reads the tokenized (binary) file and generates the ASCII output.
     *  @return whether the transformation was successful
     */
    protected boolean generate() {
        return super.generate();
    } // generate

} // LittleEndianDetokenizer
