/*  Detokenize BASIC programs
    @(#) $Id: NumwordCommand.java 820 2011-11-07 21:59:07Z gfis $
    2023-01-04: option -d
    2016-10-12: less imports
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
import  org.teherba.basdetok.BaseDetokenizer;
import  org.teherba.basdetok.DetokenizerFactory;
import  java.util.Iterator;
import  org.apache.logging.log4j.Logger;
import  org.apache.logging.log4j.LogManager;

/** Converts a file in some BASIC dialect from "tokenizes" (binary) format
 *  into readable ASCII format.
 *  This class is the commandline interface to {@link BaseDetokenizer}.
 *  @author Dr. Georg Fischer
 */
final public class Command {
    public final static String CVSID = "@(#) $Id: NumwordCommand.java 820 2011-11-07 21:59:07Z gfis $";

    /** log4j logger (category) */
    public Logger log;
    /** Newline string (CR/LF or LF only) */
    private String nl;

    /** No-args Constructor
     */
    public Command() {
        log = LogManager.getLogger(Command.class.getName());
        nl = System.getProperty("line.separator");
    } // Constructor(0)

    /** Convenience overlay method with a single string argument instead
     *  of an array of strings.
     *  @param commandLine all parameters of the commandline in one string
     *  @return output of the call depending on the function: a digit sequence,
     *  a number word, a month name etc.
     */
    public String process(String commandLine) {
        return process(commandLine.split("\\s+"));
    } // process(String)

    /** Evaluates the arguments of the command line, and processes them.
     *  @param args Arguments; if missing, print a usage hint.
     *  @return output of the call depending on the dialect
     */
    public String process(String args[]) {
        /** internal buffer for the string to be output */
        DetokenizerFactory factory = new DetokenizerFactory();
        String dialect = "m20"; // default
        int    debug = 0; // default: none
        try {
            int iarg = 0; // index for command line arguments
            if (iarg >= args.length) { // usage, with known BASIC dialects
                System.err.println("usage:\tjava org.teherba.basdetok.Command [-d mode] [-copy|-m20|-msx|-pc] input.bas > output.asc");
                System.err.println("    -d  "            + "        mode: 0 = none, 1 = some, 2 = more, 3 = all");
                Iterator<BaseDetokenizer> iter = factory.getIterator();
                while (iter.hasNext()) {
                    BaseDetokenizer detokenizer = (BaseDetokenizer) iter.next();
                    dialect = detokenizer.getDialect();
                    // keep the 8 spaces below, do not replace them by tabs
                    System.err.println("    -" + dialect + "        ".substring(0, 8 - dialect.length())
                            + detokenizer.getDescription());
                } // while iter
            } else { // >= 1 argument
                // get all options
                while (iarg < args.length && args[iarg].startsWith("-")) {
                    String option = args[iarg ++].substring(1); // remove "-"
                    if (false) {
                    } else if (option.equals("d")) {
                        debug = Integer.parseInt(args[iarg ++]);
                    } else if (option              .equals("copy")) {
                        dialect = option;
                    } else if (option.toLowerCase().equals("m20" )) {
                        dialect = option.toLowerCase();
                    } else if (option.toLowerCase().equals("msx" )) {
                        dialect = option.toLowerCase();
                    } else if (option.toLowerCase().equals("pc"  )) {
                        dialect = option.toLowerCase();
                    } else {
                        System.err.println("invalid option \"-" + option + "\"");
                    }
                } // while options

                BaseDetokenizer detokenizer = factory.getDetokenizer(dialect);
                if (detokenizer != null) { // dialect code was found
                    String fileName = null; // no filename => read from STDIN
                    if (iarg < args.length) { // with filename argument
                        fileName = args[iarg ++];
                    }
                    detokenizer.setDebug(debug);
                    detokenizer.openFile(0, fileName); // binary
                    detokenizer.openFile(1, null); // character, write to STDOUT at the moment
                    detokenizer.generate();
                    detokenizer.closeAll(); // close both files
                } else {
                    System.err.println("invalid BASIC dialect code \"" + dialect + "\"");
                }
            } // args.length >= 1
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } // try
        return "";
    } // process

    /** Commandline interface for BASIC detokenizing
     *  @param args elements of the commandline separated by whitespace:
     *	<p>[-dialect] input_filename</p>
     */
    public static void main(String args[]) {
        Command cli = new Command();
        System.out.print(cli.process(args));
    } // main

} // Command
