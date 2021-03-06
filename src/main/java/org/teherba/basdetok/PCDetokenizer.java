/*  Detokenizer for MS-DOS PCs
    @(#) $Id$
    2012-10-09, Georg Fischer: copied from M20Detokenizer
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
import  org.teherba.basdetok.LittleEndianDetokenizer;

/** Converts a PC BASIC file
 *  which was stored by GW-BASIC
 *  from compressed (tokenized) format into readable ASCII.
 *  @author Dr. Georg Fischer
 */
public class PCDetokenizer extends LittleEndianDetokenizer {
    public final static String CVSID = "@(#) $Id$";

    /** No-args Constructor, does no heavy work
     */
    public PCDetokenizer() {
        super();
        setDialect("pc");
        setDescription("PC with MS-DOS");
    } // Constructor(0)

    /** Initializes the tokenizer, especially: sets the token list.
     *  The constants here were generated by <code>extract_tokens.pl</code>.
     */
    protected void initialize() {
        // the following assignments were generated by etc/extract_tokens.pl
        tokenStrings[0xaa] = "AUTO";
        tokenStrings[0xee] = "AND";
        tokenStrings[0x06] = "ABS";
        tokenStrings[0x0e] = "ATN";
        tokenStrings[0x15] = "ASC";
        tokenStrings[0xc2] = "BSAVE";
        tokenStrings[0xc3] = "BLOAD";
        tokenStrings[0xc5] = "BEEP";
        tokenStrings[0xbf] = "COLOR";
        tokenStrings[0xbb] = "CLOSE";
        tokenStrings[0x99] = "CONT";
        tokenStrings[0x92] = "CLEAR";
        tokenStrings[0xdb] = "CSRLIN";
        tokenStrings[0x1c] = "CINT";
        tokenStrings[0x1d] = "CSNG";
        tokenStrings[0x1e] = "CDBL";
        tokenStrings[0x0c] = "COS";
        tokenStrings[0x16] = "CHR$";
        tokenStrings[0xb3] = "CALL";
        tokenStrings[0xc0] = "CLS";
        tokenStrings[0xa9] = "DELETE";
        tokenStrings[0x84] = "DATA";
        tokenStrings[0x86] = "DIM";
        tokenStrings[0xac] = "DEFSTR";
        tokenStrings[0xad] = "DEFINT";
        tokenStrings[0xae] = "DEFSNG";
        tokenStrings[0xaf] = "DEFDBL";
        tokenStrings[0x97] = "DEF";
        tokenStrings[0xa1] = "ELSE";
        tokenStrings[0x81] = "END";
        tokenStrings[0xa5] = "ERASE";
        tokenStrings[0xa6] = "EDIT";
        tokenStrings[0xa7] = "ERROR";
        tokenStrings[0xd4] = "ERL";
        tokenStrings[0xd5] = "ERR";
        tokenStrings[0x0b] = "EXP";
        tokenStrings[0x23] = "EOF";
        tokenStrings[0xf1] = "EQV";
        tokenStrings[0x82] = "FOR";
        tokenStrings[0xd1] = "FN";
        tokenStrings[0x0f] = "FRE";
        tokenStrings[0x1f] = "FIX";
        tokenStrings[0x89] = "GOTO";
        tokenStrings[0x89] = "GO TO";
        tokenStrings[0x8d] = "GOSUB";
        tokenStrings[0x1a] = "HEX$";
        tokenStrings[0x85] = "INPUT";
        tokenStrings[0x8b] = "IF";
        tokenStrings[0xd8] = "INSTR";
        tokenStrings[0x05] = "INT";
        tokenStrings[0x10] = "INP";
        tokenStrings[0xf2] = "IMP";
        tokenStrings[0xde] = "INKEY$";
        tokenStrings[0xc9] = "KEY";
        tokenStrings[0xca] = "LOCATE";
        tokenStrings[0x9d] = "LPRINT";
        tokenStrings[0x9e] = "LLIST";
        tokenStrings[0x1b] = "LPOS";
        tokenStrings[0x88] = "LET";
        tokenStrings[0xb0] = "LINE";
        tokenStrings[0xbc] = "LOAD";
        tokenStrings[0x93] = "LIST";
        tokenStrings[0x0a] = "LOG";
        tokenStrings[0x24] = "LOC";
        tokenStrings[0x12] = "LEN";
        tokenStrings[0x01] = "LEFT$";
        tokenStrings[0x25] = "LOF";
        tokenStrings[0xc1] = "MOTOR";
        tokenStrings[0xbd] = "MERGE";
        tokenStrings[0xf3] = "MOD";
        tokenStrings[0x03] = "MID$";
        tokenStrings[0x83] = "NEXT";
        tokenStrings[0x94] = "NEW";
        tokenStrings[0xd3] = "NOT";
        tokenStrings[0xba] = "OPEN";
        tokenStrings[0x9c] = "OUT";
        tokenStrings[0x95] = "ON";
        tokenStrings[0xef] = "OR";
        tokenStrings[0x19] = "OCT$";
        tokenStrings[0xb8] = "OPTION";
        tokenStrings[0xdd] = "OFF";
        tokenStrings[0x91] = "PRINT";
        tokenStrings[0x98] = "POKE";
        tokenStrings[0x11] = "POS";
        tokenStrings[0x17] = "PEEK";
        tokenStrings[0xc6] = "PSET";
        tokenStrings[0xc7] = "PRESET";
        tokenStrings[0xdc] = "POINT";
        tokenStrings[0x20] = "PEN";
        tokenStrings[0x8a] = "RUN";
        tokenStrings[0x8e] = "RETURN";
        tokenStrings[0x87] = "READ";
        tokenStrings[0x8c] = "RESTORE";
        tokenStrings[0x8f] = "REM";
        tokenStrings[0xa8] = "RESUME";
        tokenStrings[0x02] = "RIGHT$";
        tokenStrings[0x08] = "RND";
        tokenStrings[0xab] = "RENUM";
        tokenStrings[0xb9] = "RANDOMIZE";
        tokenStrings[0xc8] = "SCREEN";
        tokenStrings[0x90] = "STOP";
        tokenStrings[0xa4] = "SWAP";
        tokenStrings[0xbe] = "SAVE";
        tokenStrings[0xd2] = "SPC(";
        tokenStrings[0xcf] = "STEP";
        tokenStrings[0x04] = "SGN";
        tokenStrings[0x07] = "SQR";
        tokenStrings[0x09] = "SIN";
        tokenStrings[0x13] = "STR$";
        tokenStrings[0xd6] = "STRING$";
        tokenStrings[0x18] = "SPACE$";
        tokenStrings[0xc4] = "SOUND";
        tokenStrings[0x21] = "STICK";
        tokenStrings[0x22] = "STRIG";
        tokenStrings[0xcd] = "THEN";
        tokenStrings[0xa2] = "TRON";
        tokenStrings[0xa3] = "TROFF";
        tokenStrings[0xce] = "TAB(";
        tokenStrings[0xcc] = "TO";
        tokenStrings[0x0d] = "TAN";
        tokenStrings[0xd7] = "USING";
        tokenStrings[0xd0] = "USR";
        tokenStrings[0x14] = "VAL";
        tokenStrings[0xda] = "VARPTR";
        tokenStrings[0xa0] = "WIDTH";
        tokenStrings[0x96] = "WAIT";
        tokenStrings[0xb1] = "WHILE";
        tokenStrings[0xb2] = "WEND";
        tokenStrings[0xb7] = "WRITE";
        tokenStrings[0xf0] = "XOR";
        tokenStrings[0xe9] = "+";
        tokenStrings[0xea] = "-";
        tokenStrings[0xeb] = "*";
        tokenStrings[0xec] = "/";
        tokenStrings[0xed] = "^";
        tokenStrings[0xf4] = "\\";
        tokenStrings[0xd9] = "'";
        tokenStrings[0xe6] = ">";
        tokenStrings[0xe7] = "=";
        tokenStrings[0xe8] = "<";
    } // initialize

    /** Shifts the bytes from big endian to litte endian order (the byte order is reversed),
     *  and the floating point exponent is adjusted for IEEE 754.
     *  The method works in place.
     *  @param floatn 4 or 8 bytes
     *  @param len number of bytes in array <em>floatn</em>
     *  GW-BASIC has a floating point representation which differs
     *  to some extent from IEEE 754, <a href="http://www.chebucto.ns.ca/~af380/GW-BASIC-tokens.html">www.chebucto.ns.ca</a>.
     *  Let the reversed (big-endian) bytes be aa bb cc dd ... Then:
     *  <ul>
     *  <li>The value is 0 iff a == 0, otherwise:</li>
     *  <li>aa = exponent to base 2, plus <em>128</em> added (IEEE 754 adds 127).</li>
     *  <li>The highest bit of bb is the sign (1 = negative; IEEE 754 has the sign in the highest bit of aa.
     *  The rest of bb is the most significant part of the mantissa, with an imaginary 1 bit prefixed
     *  <em>behind</em> the dot (IEEE prefixes imagines that 1 <em>before</em> the dot).
     *  <li>cc, dd ... are the remaining bits of the mantissa.</li>
     *  </ul>
     *  For example:
     *  <pre>
     *  native GW-BASIC   00 00 70 83
     *  bytes swapped     83 70 00 00 positive, exponent = 3 + 128, mantissa .(1)111 (base 2)
     *  binary value      111.1
     *  decimal value     7.5
     *  </pre>

     */
    protected void prepareFloat(byte[] floatn, int len) {
        super.prepareFloat(floatn, len); // reverse the byte order
        if (floatn[0] == 0) { // zero is simple
            int isrc = 1;
            while (isrc < len) { // clear all bytes
                floatn[isrc] = 0;
                isrc ++;
            } // while isrc
        } else { // non-zero first byte
            byte signBit = (byte) (floatn[1] & 0x80);
            int exponent = (floatn[0] & 0xff) - 2;
            byte carry   = (byte) ((exponent & 1) > 0 ? 0x80 : 0);
            floatn[0]    = (byte) ((exponent    >> 1) | signBit );
            floatn[1]    = (byte) ((floatn[1] & 0x7f) | carry   );
        } // non-zero
    } // prepareFloat

} // PCDetokenizer
