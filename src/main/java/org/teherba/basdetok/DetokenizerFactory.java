/*  Selects the applicable detokenizer
    @(#) $Id: DetokenizerFactory.java 657 2011-03-17 07:56:38Z gfis $
<<<<<<< HEAD
    2017-05-29: javadoc 1.8
=======
    2016-10-12: less imports
>>>>>>> 241a6ad90f306e70a8e1d3d28d5a35997156673f
    2012-10-09: PC GWBASIC
    2012-09-29: for M20
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
 * See the License for the specific dialect language permissions and
 * limitations under the License.
 */
package org.teherba.basdetok;
import  org.teherba.basdetok.BaseDetokenizer;
import  java.util.ArrayList; // asList
import  java.util.Iterator;
import  java.util.StringTokenizer;
import  org.apache.log4j.Logger;

/** Selects a specific detokenizer, and iterates over the descriptions
 *  of all detokenizers and their codes.
 *  Initially, a list of the available detokenizers is built, and classes
 *  which cannot be instantiated are <em>silently</em> ignored.
 *  @author Dr. Georg Fischer
 */
public class DetokenizerFactory {
    public final static String CVSID = "@(#) $Id: DetokenizerFactory.java 657 2011-03-17 07:56:38Z gfis $";
    /** log4j logger (category) */
    private Logger log;

    /** Array of detokenizers for different BASIC dialects */
    private ArrayList<BaseDetokenizer> detokenizers;

    /** Attempts to instantiate the detokenizer for some BASIC dialect
     *  @param dialect a lowercase word identifying the specific BASIC dialect
     *  @param baseName the name of the applicable Java class, without the package name
     */
    private void addDetokenizer(String dialect, String baseName) {
        try {
            detokenizers.add((BaseDetokenizer) Class.forName("org.teherba.basdetok." + baseName).newInstance());
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            // ignore any error silently - this BASIC dialect will not be known
        }
    } // addDetokenizer

    /** No-args Constructor.
     *  The order of the BASIC dialects here defines the order in the user interfaces.
     */
    public DetokenizerFactory() {
        log = Logger.getLogger(DetokenizerFactory.class.getName());
        try {
            detokenizers = new ArrayList<BaseDetokenizer>(64);
            addDetokenizer("copy"    , "FileCopy"             );  // input = output
            addDetokenizer("m20"     , "M20Detokenizer"       );  // Olivetti M20 with PCOS
            addDetokenizer("msx"     , "MSXDetokenizer"       );  // MicroSoft eXtended BASIC
            addDetokenizer("pc"      , "PCDetokenizer"        );  // MS-DOS PCs with GWBASIC.EXE
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // Constructor(0)

    /** Gets an iterator over all implemented detokenizers.
     *  @return iterator over <em>allDetokenizers</em>
     */
    public Iterator<BaseDetokenizer> getIterator() {
        Iterator<BaseDetokenizer> result = detokenizers.iterator();
        return result;
    } // getIterator

    /** Gets the number of available detokenizers
     *  @return number of BASIC dialects which can be spelled
     */
    public int getCount() {
        return detokenizers.size();
    } // getCount

    /** Determines whether the BASIC dialect code denotes this detokenizer class.
     *  @param detokenizer the detokenizer to be tested
     *  @param dialect code for the desired BASIC dialect
     *  @return whether the class can hanlde this dialcct
     */
    public boolean isApplicable(BaseDetokenizer detokenizer, String dialect) {
        boolean result = false;
        StringTokenizer tokenizer = new StringTokenizer(detokenizer.getDialect(), ",");
        while (! result && tokenizer.hasMoreTokens()) { // try all tokens
            if (dialect.equals(tokenizer.nextToken())) {
                result = true;
            }
        } // while all tokens
        return result;
    } // isApplicable

    /** Gets the applicable detokenizer for a specified dialect code.
     *  @param dialect code for the BASIC dialect
     *  @return the detokenizer for that dialect, or <em>null</em> if the
     *  dialect was not found
     */
    public BaseDetokenizer getDetokenizer(String dialect) {
        BaseDetokenizer result = null;
        // determine the applicable detokenizer for 'dialect'
        Iterator<BaseDetokenizer> diter = getIterator();
        boolean notFound = true;
        while (notFound && diter.hasNext()) {
            BaseDetokenizer detokenizer = diter.next();
            if (isApplicable(detokenizer, dialect)) {
                result = detokenizer;
                result.initialize();
                notFound = false;
            } // applicable
        } // while not found
        return result;
    } // getDetokenizer

} // DetokenizerFactory
