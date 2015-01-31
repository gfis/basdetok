/*  Detokenize BASIC programs
    @(#) $Id: NumwordServlet.java 820 2011-11-07 21:59:07Z gfis $
    2012-09-29, Dr. Georg Fischer 
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
import  org.teherba.basdetok.Command;
import  org.teherba.basdetok.DetokenizerFactory;
import  java.io.IOException;
import  javax.servlet.RequestDispatcher;
import  javax.servlet.ServletConfig;
import  javax.servlet.ServletContext;
import  javax.servlet.ServletException;
import  javax.servlet.http.HttpServlet;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  javax.servlet.http.HttpSession;
import  org.apache.log4j.Logger;

/** Spell numbers in some language.
 *  This class is the servlet interface to {@link BaseDetokenizer},
 *  and ressembles the functionality of the commandline interface in
 *  {@link Command}.
 *  @author Dr. Georg Fischer
 */
public class Servlet extends HttpServlet {
    public final static String CVSID = "@(#) $Id: NumwordServlet.java 820 2011-11-07 21:59:07Z gfis $";
    // public final static long serialVersionUID = 19470629004L;
    
    /** log4j logger (category) */
    private Logger log;
    /** instance of the number converter */
    private Command command;
    
    /** Delivers <em>Transformer</em>s */
    private DetokenizerFactory factory;

    /** Called by the servlet container to indicate to a servlet
     *  that the servlet is being placed into service.
     *  @param config object containing the servlet's configuration and initialization parameters
     *  @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // ???
        log = Logger.getLogger(Servlet.class.getName());
        command = new Command();
    } // init

    /** Creates the response for a HTTP GET request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    } // doGet

    /** Creates the response for a HTTP POST request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException
     */
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    } // doPost

    /** Gets the value of an HTML input field, maybe as empty string
     *  @param request request for the HTML form
     *  @param name name of the input field
     *  @return non-null (but possibly empty) string value of the input field
     */
    private String getInputField(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null) {
            value = "";
        }
        return value;
    } // getInputField

    /** Creates the response for a HTTP GET or POST request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException
     */
    public void generateResponse(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            // NumwordCommand command = new NumwordCommand();
            String view     = getInputField(request, "view");
            String language = getInputField(request, "language");
            if (language.equals("")) {
                language = "de";
            }
            String function = getInputField(request, "function");
            String digits   = getInputField(request, "digits"  );
            session.setAttribute("command"  , command);
            session.setAttribute("language" , language);
            session.setAttribute("digits"   , digits  );
            Object obj = session.getAttribute("factory");
            if (obj == null) {
                factory = new DetokenizerFactory();
                session.setAttribute("factory", factory);
            } else {
                factory = (DetokenizerFactory) obj;
            }
            BaseDetokenizer detokenizer = factory.getDetokenizer(language);

            String newPage = "index";
            if (false) {
            } else if (detokenizer == null               ) {
                newPage = "message";
                session.setAttribute("messno", "003"); // invalid language code
            } else if (function.equals("c")    ) {
                session.setAttribute("function", function);
            } else { // invalid function
                newPage = "message";
                session.setAttribute("messno"  , "001");
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/" + newPage + ".jsp");
            dispatcher.forward(request, response);
        } catch (Exception exc) {
            response.getWriter().write(exc.getMessage());
            System.out.println(exc.getMessage());
            throw new IOException(exc.getMessage());
        }
    } // generateResponse

} // Servlet
