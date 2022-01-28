/*  Abstract class for all BASIC detokenizers
    @(#) $Id: BaseDetokenizer.java 852 2012-01-06 08:07:08Z gfis $
    2017-05-29: javadoc 1.8
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
import  java.io.BufferedInputStream;
import  java.io.FileInputStream;
import  java.io.FileOutputStream;
import  java.io.InputStream;
import  java.io.PrintWriter;
import  java.nio.channels.Channels;
import  java.nio.channels.WritableByteChannel;
import  java.util.Properties;
import  org.apache.logging.log4j.Logger;
import  org.apache.logging.log4j.LogManager;

/** Base class for BASIC detokenizers defining common properties and methods.
 *  @author Dr. Georg Fischer
 */
public abstract class BaseDetokenizer {
    public final static String CVSID = "@(#) $Id: BaseDetokenizer.java 852 2012-01-06 08:07:08Z gfis $";

    /** whether to write debugging output (iff &gt; 0) */
    protected final static int debug = 0;

    /** log4j logger (category) */
    private Logger log;

    //--------------------------------------------------
    // Buffer for the binary input, and related methods
    //--------------------------------------------------
    /** Internal buffer for the record */
    private byte[] buffer;

    /** Filled length of the record buffer as read from a file */
    private int bufferLength;
    /** Gets the filled record length read from a file
     *  @return number of bytes / chararcters read into the reocrd
     */
    public int getBufferLength() {
        return bufferLength;
    }  // getBufferLength

   /** Current read/write pointer (position in the internal {@link #buffer}).
    *  Is incremented by almost all access methods, and is sometimes
    *  returned by such methods.
    */
    private int bufferPos = 0;

    /** Reads a record from an open stream into the record buffer,
     *  and sets the pointer to the start of the buffer.
     */
    private void readChunk() {
        bufferPos = 0;
        bufferLength = -1; // on error assume EOF
        try {
            bufferLength = byteReader.read(buffer, 0, buffer.length); // -1 at EOF
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // readChunk

    /** current position relative to file start */
    private int filePos;

    /** Gets the next byte from the internal {@link #buffer}
     *  @return that byte
     */
    public byte get1() {
        if (bufferPos >= bufferLength) {
            readChunk();
        }
        filePos ++;
        return buffer[bufferPos ++];
    } // get1

    //-----------------------
    // Other bean properties
    //-----------------------
    /** description of the BASIC dialect */
    protected String description;
    /** Sets the description of the BASIC dialect
     *  @param description some short text
     */
    protected void setDescription(String description) {
        this.description = description;
    } // setDescription
    /** Gets the description of the BASIC dialect
     *  @return some short text
     */
    protected String getDescription() {
        return description;
    } // getDescription

    /** list of codes for the BASIC dialect */
    protected String dialect;
    /** Sets the codes for the BASIC dialect
     *  @param codes list of codes separated by commas
     */
    protected void setDialect(String codes) {
        this.dialect = codes;
    } // setDialect
    /** Gets the codes for the BASIC dialect
     *  @return list of codes separated by commas
     */
    protected String getDialect() {
        return dialect;
    } // getDialect

    /** output file encoding, empty for binary (byte) data */
    protected String resultEncoding;
    /** Sets the output file encoding
     *  @param encoding name of the encoding (UTF-8, ISO-8859-1),
     *  or empty for binary data
     */
    public void setResultEncoding(String encoding) {
        resultEncoding = encoding;
    } // setResultEncoding
    /** Gets the output file encoding
     *  @return encoding name of the result encoding (UTF-8, ISO-8859-1),
     *  or empty for binary data
     */
    public String getResultEncoding() {
        return resultEncoding;
    } // getResultEncoding

    /** List of options for a transformation */
    private   Properties options;
    /** Gets the value of a (String) option, or the specified default.
     *  Option names are used by internal methods which always specify them in lower case.
     *  @param name name of the option
     *  @param def default value if option is not set
     *  @return option string
     */
    protected String getOption(String name, String def) {
        String result = def;
        if (options != null) {
            result = options.getProperty(name);
        }
        return result != null ? result : def;
    } // getOption

    //--------------------------------
    // Constructor
    //--------------------------------
    /** Constructor with no arguments, no heavy-weight operations.
     */
    public BaseDetokenizer() {
        log = LogManager.getLogger(BaseDetokenizer.class.getName());
        setDialect("ident");
        setDescription("transparent copy");
        buffer = new byte[4096];
        bufferLength = 0; // will force an immediate read
        bufferPos    = 0;
        filePos      = 0;
        setDialect("ident");
        setDescription("transparent copy");
    } // Constructor(0)

    //-----------------
    // File Processing
    //-----------------
    /** reader for binary files */
    protected InputStream   byteReader;
    /** writer for text   files */
    protected PrintWriter   charWriter;

    /** Opens some named (ordinary) input or output file
     *  @param ifile 0 for source file, 1 for result file
     *  @param fileName name of the (ordinary) file to be opened, or null for STDIN/STDOUT
     *  @return whether the operation was successful
     */
    public boolean openFile(int ifile, String fileName) {
        boolean result = true;
        setResultEncoding(getOption("enc", "ISO-8859-1"));
        try {
            switch (ifile) {
                case 0: // open input  from file
                    { // byte mode
                        if (byteReader != null) {
                            byteReader.close();
                        }
                        if (fileName == null) {
                            byteReader = new BufferedInputStream(System.in);
                        }
                        else {
                            byteReader = new BufferedInputStream(new FileInputStream(fileName));
                        }
                    } // byte input file
                    break;
                case 1:
                default: // open output into file
                    { // character mode
                        if (fileName == null) { // stdout
                            if (charWriter == null) {
                                charWriter = new PrintWriter(Channels.newWriter(Channels.newChannel(System.out), resultEncoding));
                            } // else leave stdout open, close it with main program
                        } else { // not stdout
                            if (charWriter != null) {
                                charWriter.close();
                            }
                            WritableByteChannel channel = (new FileOutputStream (fileName, false)).getChannel();
                            charWriter = new PrintWriter(Channels.newWriter(channel, resultEncoding));
                        } // not stdout
                    } // character output
                    break;
            } // switch ifile
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            result = false;
        }
        return result;
    } // openFile

    /** Opens some input or output stream
     *  @param ifile 0 for source, 1 for result
     *  @param stream object for stream input or output, respectively (may not be null)
     *  @return whether the operation was successful
     */
    public boolean openStream(int ifile, Object stream) {
        boolean result = true;
        try {
            byteReader = new BufferedInputStream ((InputStream) stream);
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            result = false;
        }
        return result;
    } // openStream

    /** Closes any open input and output files
     */
    public void closeAll() {
        try {
            if (charWriter != null) {
                charWriter.flush();
                charWriter.close();
            }
            if (byteReader != null) {
                byteReader.close();
            }
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // closeAll

    //---------------------------------------------------------------
    // Methods and properties which can be overwritten by subclasses
    //---------------------------------------------------------------
    /** BASIC keywords for the specific dialect.
     *  The array is indexed with the binary token codes.
     */
    protected String[] tokenStrings = new String[0x200];
    /** Gets the token string for a code.
     *  @param tokenCode binary code (&lt; 0x200) of the token
     *  @return token string representation, BASIC keyword or operator
     */
    protected String getToken(int tokenCode) {
        String result = tokenStrings[tokenCode];
        if (result == null) {
            result = "{B " + Integer.toHexString(tokenCode) + "}";
        }
        return result;
    } // getToken

    /** Initializes the tokenizer, especially: sets the token list.
     *  Pseudo-abstract; this method will be overridden
     *  by the class for the specific BASIC dialect.
     */
    protected void initialize() {
    } // initialize

    /** Gets the BASIC line number from 2 bytes, in big or little endian mode
     *  @param lineNo 2 bytes containing the BASIC line number
     *  @return BASIC line number as a Java integer
     */
    protected abstract int getLineNo(byte[] lineNo);

    /** Gets some integer value from 2 bytes, in big or little endian mode
     *  @param int2 2 bytes containing the value
     *  @return a Java integer
     */
    protected abstract int getInt2(byte[] int2);

    /** Prepares 4 or 8 bytes before being interpreted as a 32 or 64 bit,
     *  big-endian, IEEE 754 floating point number.
     *  The bytes are shifted in place.
     *  This method is typically overwritten in subclasses.
     *  @param floatn 4 or 8 bytes containing the value
     *  @param len number of bytes in <em>floatn</em>, 4 or 8
     */
    protected void prepareFloat(byte[] floatn, int len) {
        // default: do nothing
    } // prepareFloat

    /** Gets some floating point value from 4 or 8 bytes, in big or little endian mode
     *  @param floatn 4 or 8 bytes containing the value
     *  @param len number of bytes in floatn
     *  @return ASCII string representation of the value
     */
    protected String getFloat(byte[] floatn, int len) {
        prepareFloat(floatn, len);
        StringBuffer result = new StringBuffer(16);
        String digs = null;
        int ind = 0;
        if (debug > 0) {
            String sep = "{F ";
            int bits = 0;
            while (ind < len) {
                result.append(sep);
                sep = "";
                digs = Integer.toHexString(floatn[ind] & 0xff);
                digs = "00000000".substring(0, 2 - digs.length()) + digs;
                result.append(digs);
                bits = (bits << 8) | (floatn[ind] & 0xff);
                ind ++;
            } // while ind
            if (false) {
                result.append(",");
                digs = Integer.toHexString(bits);
                digs = "00000000".substring(0, 8 - digs.length()) + digs;
                result.append(digs);
            }
            result.append("=");
            result.append(String.valueOf(Float.intBitsToFloat(bits)));
            result.append("}");
        } else { // no debug
            int bits = 0;
            while (ind < len) {
                bits = (bits << 8) | (floatn[ind] & 0xff);
                ind ++;
            } // while ind
            String sfloat = String.valueOf(Float.intBitsToFloat(bits));
            result.append(sfloat.startsWith("0.") ? sfloat.substring(1) : sfloat);
        } // no debug
        return result.toString();
    } // getFloat


    //----------
    // generate
    //----------
    /** States of the finite automaton which reads successive source lines,
     *  to be used in {@link #generate}.
     */
    private enum State
            { INIT      // 0xff at the very beginning
            , OFFSET    // offset before a line
            , OFFSET2   // 2nd byte of offset
            , LINE_NO   // BASIC line number at the beginning of a line
            , LINE_NO2  // 2nd byte of line number
            , TEXT      // text content of the line
            , COLON     // behind a ":" in the text content
            };

    /** Reads the tokenized (binary) file and generates the ASCII output.
     *  @return whether the transformation was successful
     */
    protected boolean generate() {
        boolean result = true;
        byte[] lineOf  = new byte[2]; // offset to next line, unimportant
        byte[] lineNo  = new byte[2]; // BASIC line number for this line
        byte[] int2    = new byte[2]; // some integer value in big or little endian
        byte[] floatn  = new byte[8]; // some (double) floating point value, 4 or 8 bytes in big or little endian
        StringBuffer line = new StringBuffer(128);
        String token = null;
        State state = State.INIT;
        boolean readOff = true;
        byte bt = get1();
        int  ch = bt & 0xff; // holds always

        while (getBufferLength() >= 0) { // -1 = EOF
            readOff = true;
            switch (state) {
                case INIT:
                    // ignore 0xff
                    state = State.OFFSET;
                    break;
                case OFFSET:
                    lineOf[0] = bt;
                    state = State.OFFSET2;
                    break;
                case OFFSET2:
                    lineOf[1] = bt;
                    if (debug > 0) {
                        line.append("@" + Integer.toHexString(filePos) + ": ");
                    }
                    state = State.LINE_NO;
                    break;
                case LINE_NO:
                    lineNo[0] = bt;
                    state = State.LINE_NO2;
                    break;
                case LINE_NO2:
                    lineNo[1] = bt;
                    line.append(String.valueOf(getLineNo(lineNo)) + " ");
                    state = State.TEXT;
                    break;

                case TEXT:
                    ch = bt & 0xff;
                    if (false) {
                    } else if (ch == 0xff) {
                        ch = (get1() & 0xff) - 0x80;
                        token = getToken(ch);
                        if (false) {
                        } else if (token.equals("WHILE")) {
                            bt = get1();
                            ch = bt & 0xff;
                            if (! getToken(ch).equals("+")) {
                                readOff = false;
                            } // else skip a superfluous "+"
                        } else if (token.equals("ELSE")) {
                            bt = get1();
                            ch = bt & 0xff;
                            if (ch != ':') {
                                readOff = false;
                            } // else skip a superfluous ":"
                        }
                        line.append(token);
                    } else if (ch >  0x7e) {
                        token = getToken(ch);
                        if (false) {
                        } else if (token.equals("WHILE")) {
                            bt = get1();
                            ch = bt & 0xff;
                            if (! getToken(ch).equals("+")) {
                                readOff = false;
                            } // else skip a superfluous "+"
                        } else if (token.equals("ELSE")) {
                            bt = get1();
                            ch = bt & 0xff;
                            if (ch != ':') {
                                readOff = false;
                            } // else skip a superfluous ":"
                        }
                        line.append(token);
                    } else if (ch == 0x3a) { // ":" has some exceptions
                        state = State.COLON;
                    } else if (ch >= 0x20) {
                        line.append((char) ch);
                    } else { // < 0x20
                        switch (ch) {
                            case 0x00: // end of line
                                charWriter.println(line.toString());
                                line.setLength(0);
                                state = state.OFFSET;
                                break;
                            case 0x0b: // octal constant
                                int2[0] = get1();
                                int2[1] = get1();
                                line.append("&O" + Integer.toOctalString(getInt2(int2)));
                                break;
                            case 0x0c: // hexadecimal constant
                                int2[0] = get1();
                                int2[1] = get1();
                                line.append("&O" + Integer.toHexString(getInt2(int2)));
                                break;
                            case 0x0d: // line pointer, after use
                            case 0x0e: // line pointer, before use
                                int2[0] = get1();
                                int2[1] = get1();
                                line.append(Integer.toString(getInt2(int2)));
                                break;
                            case 0x0f: // one-byte integer
                                bt = get1();
                                ch = bt & 0xff;
                                line.append(Integer.toString(ch));
                                break;
                            case 0x10: // ignore this
                                break;
                            case 0x11: //  0
                            case 0x12: //  1
                            case 0x13: //  2
                            case 0x14: //  3
                            case 0x15: //  4
                            case 0x16: //  5
                            case 0x17: //  6
                            case 0x18: //  7
                            case 0x19: //  8
                            case 0x1a: //  9
                            case 0x1b: // 10
                                line.append(Integer.toString(ch - 0x11));
                                break;
                            case 0x1c: // 2 byte integer constant
                                int2[0]    = get1();
                                int2[1]    = get1();
                                line.append(Integer.toString(getInt2(int2)));
                                break;
                            case 0x1d: // 4 byte floating point constant
                                floatn[0]  = get1();
                                floatn[1]  = get1();
                                floatn[2]  = get1();
                                floatn[3]  = get1();
                                line.append(getFloat(floatn, 4));
                                break;
                            case 0x1e: // ignore
                                break;
                            case 0x1f: // 8 byte double constant
                                floatn[0] = get1();
                                floatn[1] = get1();
                                floatn[2] = get1();
                                floatn[3] = get1();
                                floatn[4] = get1();
                                floatn[5] = get1();
                                floatn[6] = get1();
                                floatn[7] = get1();
                                line.append(getFloat(floatn, 8));
                                break;
                            default:
                                line.append("{0x" + Integer.toHexString(ch) + "}");
                                break;
                        } // switch (ch)
                    } // < 0x20
                    break;

                case COLON:
                    if (false) {
                    } else if (ch >  0x7e) {
                        token = getToken(ch);
                        if (false) {
                        } else if (token.equals("ELSE")) {
                            line.append(token); // "ELSE" instead of ":ELSE"
                        } else if (token.equals("REM")) {
                            line.append("\'"); // apostrophe is replacement for ":REM"
                        } else {
                            line.append(":");
                            readOff = false;
                        }
                    } else {
                        line.append(":"); // normal colon, no special treatment
                        readOff = false; // try current byte again
                    }
                    state = State.TEXT;
                    break;

                default:
                    log.warn("state " + state.toString() + " not implemented");
                    break;
            } // switch state

            if (readOff) {
                bt = get1();
                ch = bt & 0xff;
            }
        } // while not EOF
        return result;
    } // generate

} // BaseDetokenizer
