/*  Detokenizer for Olivetti M20 BASIC 
    @(#) $Id$
    2012-09-29, Georg Fischer
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
import  org.teherba.basdetok.BigEndianDetokenizer;

/** Converts a BASIC file from the Olivetti M20 (unter PCOS, pre-PC) 
 *	which was stored in compressed (tokenized) format into readable ASCII.
 *	The BASIC interpreter is named <em>basic_abs</em>,
 *  @author Dr. Georg Fischer
 */
public class M20Detokenizer extends BigEndianDetokenizer {
    public final static String CVSID = "@(#) $Id$";

    /** No-args Constructor, does no heavy work
     */
    public M20Detokenizer() {
        super();
        setDialect("m20");
        setDescription("Olivetti M20 with PCOS");
	} // Constructor(0)

    /** Initializes the tokenizer, especially: sets the token list.
     *  The constants here were generated by <code>extract_tokens.pl</code>.
     */
    protected void initialize() {
    	// the following assignments were generated by etc/extract_tokens.pl
        tokenStrings[0xf2] = "AND";
        tokenStrings[0x06] = "ABS";
        tokenStrings[0x0e] = "ATN";
        tokenStrings[0x15] = "ASC";
        tokenStrings[0xa9] = "AUTO";
        tokenStrings[0xc1] = "CLOSE";
        tokenStrings[0x9a] = "CONT";
        tokenStrings[0x92] = "CLEAR";
        tokenStrings[0x1c] = "CINT";
        tokenStrings[0x1d] = "CSNG";
        tokenStrings[0x1e] = "CDBL";
        tokenStrings[0x20] = "CVI";
        tokenStrings[0x21] = "CVS";
        tokenStrings[0x22] = "CVD";
        tokenStrings[0x0c] = "COS";
        tokenStrings[0x16] = "CHR$";
        tokenStrings[0xb4] = "CALL";
        tokenStrings[0xb6] = "COMMON";
        tokenStrings[0xb7] = "CHAIN";
        tokenStrings[0xcf] = "CIRCLE";
        tokenStrings[0xd0] = "CLS";
        tokenStrings[0xd1] = "CURSOR";
        tokenStrings[0xcb] = "COLOR";
        tokenStrings[0x84] = "DATA";
        tokenStrings[0x86] = "DIM";
        tokenStrings[0xab] = "DEFSTR";
        tokenStrings[0xac] = "DEFINT";
        tokenStrings[0xad] = "DEFSNG";
        tokenStrings[0xae] = "DEFDBL";
        tokenStrings[0x98] = "DEF";
        tokenStrings[0xa8] = "DELETE";
        tokenStrings[0xd8] = "DATE$";
        tokenStrings[0xb0] = "DRAW";
        tokenStrings[0x81] = "END";
        tokenStrings[0xa0] = "ELSE";
        tokenStrings[0xa4] = "ERASE";
        tokenStrings[0xa5] = "EDIT";
        tokenStrings[0xa6] = "ERROR";
        tokenStrings[0xe0] = "ERL";
        tokenStrings[0xe1] = "ERR";
        tokenStrings[0xc5] = "EXEC";
        tokenStrings[0x0b] = "EXP";
        tokenStrings[0x23] = "EOF";
        tokenStrings[0xf5] = "EQV";
        tokenStrings[0x82] = "FOR";
        tokenStrings[0xbe] = "FIELD";
        tokenStrings[0xc4] = "FILES";
        tokenStrings[0xdd] = "FN";
        tokenStrings[0x0f] = "FRE";
        tokenStrings[0x1f] = "FIX";
        tokenStrings[0x89] = "GOTO";
        tokenStrings[0x89] = "GO TO";
        tokenStrings[0x8d] = "GOSUB";
        tokenStrings[0xbf] = "GET";
        tokenStrings[0x1a] = "HEX$";
        tokenStrings[0x85] = "INPUT";
        tokenStrings[0x8b] = "IF";
        tokenStrings[0xda] = "INKEY$";
        tokenStrings[0xe4] = "INSTR";
        tokenStrings[0x05] = "INT";
        tokenStrings[0x10] = "INP";
        tokenStrings[0xf6] = "IMP";
        tokenStrings[0x9b] = "ISET";
        tokenStrings[0xbc] = "IRESET";
        tokenStrings[0x2b] = "IEEE";
        tokenStrings[0xc7] = "KILL";
        tokenStrings[0x88] = "LET";
        tokenStrings[0xaf] = "LINE";
        tokenStrings[0xc2] = "LOAD";
        tokenStrings[0xc8] = "LSET";
        tokenStrings[0x9c] = "LPRINT";
        tokenStrings[0x9d] = "LLIST";
        tokenStrings[0x1b] = "LPOS";
        tokenStrings[0x93] = "LIST";
        tokenStrings[0x0a] = "LOG";
        tokenStrings[0x24] = "LOC";
        tokenStrings[0x12] = "LEN";
        tokenStrings[0x01] = "LEFT$";
        tokenStrings[0x25] = "LOF";
        tokenStrings[0xc3] = "MERGE";
        tokenStrings[0xf7] = "MOD";
        tokenStrings[0x26] = "MKI$";
        tokenStrings[0x27] = "MKS$";
        tokenStrings[0x28] = "MKD$";
        tokenStrings[0x03] = "MID$";
        tokenStrings[0x83] = "NEXT";
        tokenStrings[0x96] = "NULL";
        tokenStrings[0xc6] = "NAME";
        tokenStrings[0x94] = "NEW";
        tokenStrings[0xdf] = "NOT";
        tokenStrings[0x95] = "ON";
        tokenStrings[0xbd] = "OPEN";
        tokenStrings[0xf3] = "OR";
        tokenStrings[0x19] = "OCT$";
        tokenStrings[0xb8] = "OPTION";
        tokenStrings[0xc0] = "PUT";
        tokenStrings[0x99] = "POKE";
        tokenStrings[0x91] = "PRINT";
        tokenStrings[0x11] = "POS";
        tokenStrings[0x17] = "PEEK";
        tokenStrings[0xcc] = "PAINT";
        tokenStrings[0xcd] = "PSET";
        tokenStrings[0xce] = "PRESET";
        tokenStrings[0xe6] = "POINT";
        tokenStrings[0x97] = "POLL";
        tokenStrings[0x87] = "READ";
        tokenStrings[0x8a] = "RUN";
        tokenStrings[0x8c] = "RESTORE";
        tokenStrings[0x8e] = "RETURN";
        tokenStrings[0x8f] = "REM";
        tokenStrings[0xa7] = "RESUME";
        tokenStrings[0xc9] = "RSET";
        tokenStrings[0x02] = "RIGHT$";
        tokenStrings[0x08] = "RND";
        tokenStrings[0xaa] = "RENUM";
        tokenStrings[0xb9] = "RANDOMIZE";
        tokenStrings[0x9e] = "RBYTE";
        tokenStrings[0x90] = "STOP";
        tokenStrings[0xa3] = "SWAP";
        tokenStrings[0xca] = "SAVE";
        tokenStrings[0xde] = "SPC(";
        tokenStrings[0xd7] = "STEP";
        tokenStrings[0x04] = "SGN";
        tokenStrings[0x07] = "SQR";
        tokenStrings[0x09] = "SIN";
        tokenStrings[0x13] = "STR$";
        tokenStrings[0xe2] = "STRING$";
        tokenStrings[0x18] = "SPACE$";
        tokenStrings[0xbb] = "SYSTEM";
        tokenStrings[0x29] = "SCALEX";
        tokenStrings[0x2a] = "SCALEY";
        tokenStrings[0xd2] = "SCALE";
        tokenStrings[0xdb] = "SRQ";
        tokenStrings[0xa1] = "TRON";
        tokenStrings[0xa2] = "TROFF";
        tokenStrings[0xd6] = "TAB(";
        tokenStrings[0xd4] = "TO";
        tokenStrings[0xd5] = "THEN";
        tokenStrings[0x0d] = "TAN";
        tokenStrings[0xd9] = "TIME$";
        tokenStrings[0xe3] = "USING";
        tokenStrings[0xdc] = "USR";
        tokenStrings[0x14] = "VAL";
        tokenStrings[0xe7] = "VARPTR";
        tokenStrings[0x9f] = "WIDTH";
        tokenStrings[0xb2] = "WHILE";
        tokenStrings[0xb3] = "WEND";
        tokenStrings[0xb5] = "WRITE";
        tokenStrings[0xba] = "WINDOW";
        tokenStrings[0xb1] = "WBYTE";
        tokenStrings[0xf4] = "XOR";
        tokenStrings[0xed] = "+";
        tokenStrings[0xee] = "-";
        tokenStrings[0xef] = "*";
        tokenStrings[0xf0] = "/";
        tokenStrings[0xf1] = "^";
        tokenStrings[0xf8] = "\\";
        tokenStrings[0xe5] = "'";
        tokenStrings[0xea] = ">";
        tokenStrings[0xeb] = "=";
        tokenStrings[0xec] = "<";
    } // initialize

/* IEE754 floating point examples (words order already reversed)
{F 11110000,10001100,11010111,10100000}	.01		
{F 11110100,11001100,11001100,11001101} .1		
{F 11101000,10000011,10010000,11011110} .001	
{F 10000000,11110000,00000000,00000000} 7.5		
{F 11101100,10100011,11010111,10100000} .005	
{F 11110100,10011000,11001100,11001101} .05		
{F 11111100,00000000,00000000,00000000} .5		
*/        

	/** Prepares 4 or 8 bytes before being interpreted as a 32 or 64 bit, 
	 *	big-endian, IEEE 754 floating point number.
	 *	The bytes are shifted in place.
	 *  For the M20, individual words are 16 bit big-endian, 
	 *	but the 2 or 4 words must still be swapped.
	 *  The bit pattern is IEEE 754: sign in bit 31, exponent +127 in
	 *  bits 23-30, mantissa in bits 0-22 plus imaginary bit 23 = 1.
	 *  For example:
	 *	<pre>
	 *  Native M20        00 00 40 f0
	 *  words swapped     40 f0 00 00, sign postive, exponent 0x81 = 2 + 127, mantissa (1).111
	 *  binary value      111.1
	 *  decimal           7.5
	 *	</pre>
	 *	@param floatn 4 or 8 bytes containing the value
	 *  @param len number of bytes in <em>floatn</em>, 4 or 8
	 */
	protected void prepareFloat(byte[] floatn, int len) {
		int isrc = 0;
		int itar = len - 2;
		byte temp = 0;
		while (isrc < itar) {
			temp = floatn[itar + 0];
			floatn[itar + 0] = floatn[isrc + 0];
			floatn[isrc + 0] = temp;
			temp = floatn[itar + 1];
			floatn[itar + 1] = floatn[isrc + 1];
			floatn[isrc + 1] = temp;
			isrc += 2;
			itar -= 2;
		} // while isrc
	} // prepareFloat
	
} // M20Detokenizer
