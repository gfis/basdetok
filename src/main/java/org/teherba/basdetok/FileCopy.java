/*  Pseudo Detokenizer which copies the input file transparently to the output
    @(#) $Id: PolSpeller.java 521 2010-07-26 07:06:10Z gfis $
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

/** Copies the input file transparently to the output.
 *  @author Dr. Georg Fischer
 */
public class FileCopy extends BigEndianDetokenizer {
    public final static String CVSID = "@(#) $Id: PolSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /** No-args Constructor
     */
    public FileCopy() {
        super();
        setDialect("copy");
        setDescription("File Copy");
    } // Constructor(0)

    /** Reads the tokenized (binary) file and generates the ASCII output.
     *  @return whether the transformation was successful
     */
    protected boolean generate() {
        boolean result = true;
        while (getBufferLength() >= 0) { // -1 = EOF
            charWriter.write(get1());
        } // while not EOF
        return result;
    } // generate

} // FileCopy
