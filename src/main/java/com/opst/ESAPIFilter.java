/**
 * OWASP Enterprise Security API (ESAPI)
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * Enterprise Security API (ESAPI) project. For details, please see
 * <a href="http://www.owasp.org/index.php/ESAPI">http://www.owasp.org/index.php/ESAPI</a>.
 *
 * Copyright (c) 2007 - The OWASP Foundation
 * 
 * The ESAPI is published by OWASP under the BSD license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author karci
 * @created 2015
 */
package com.opst;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.owasp.esapi.errors.AuthenticationException;

public class ESAPIFilter implements Filter {

	private final Logger logger = ESAPI.getLogger("ESAPIFilter");

	private static final String[] obfuscate = { "password" };

	/**
	 * Called by the web container to indicate to a filter that it is being
	 * placed into service. The servlet container calls the init method exactly
	 * once after instantiating the filter. The init method must complete
	 * successfully before the filter is asked to do any filtering work.
	 * 
	 * @param filterConfig
	 *            configuration object
	 */

	/**
	 * The doFilter method of the Filter is called by the container each time a
	 * request/response pair is passed through the chain due to a client request
	 * for a resource at the end of the chain. The FilterChain passed in to this
	 * method allows the Filter to pass on the request and response to the next
	 * entity in the chain.
	 * 
	 * @param req
	 *            Request object to be processed
	 * @param resp
	 *            Response object
	 * @param chain
	 *            current FilterChain
	 * @exception IOException
	 *                if any occurs
     * @throws javax.servlet.ServletException
	 */
        @Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		ESAPI.httpUtilities().setCurrentHTTP(request, response);

        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        try {
			// figure out who the current user is
			try {
				ESAPI.authenticator().login(request, response);
			} catch( AuthenticationException e ) {
				ESAPI.authenticator().logout();
				request.setAttribute("message", "Authentication failed");
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.xhtml");
				dispatcher.forward(request, response);
				return;
			}

			// log this request, obfuscating any parameter named password
			ESAPI.httpUtilities().logHTTPRequest(request, logger, Arrays.asList(obfuscate));

			// check access to this URL
			if ( !ESAPI.accessController().isAuthorizedForURL(request.getRequestURI().toString()) ) {
				request.setAttribute("message", "Unauthorized" );
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.xhtml");
				dispatcher.forward(request, response);
				return;
			}
			
			
		} finally {
			// VERY IMPORTANT
			// clear out the ThreadLocal variables in the authenticator
			// some containers could possibly reuse this thread without clearing the User
			ESAPI.authenticator().clearCurrent();
			ESAPI.httpUtilities().setCurrentHTTP(null, null);
		}
        }

	/**
	 * Called by the web container to indicate to a filter that it is being
	 * taken out of service. This method is only called once all threads within
	 * the filter's doFilter method have exited or after a timeout period has
	 * passed. After the web container calls this method, it will not call the
	 * doFilter method again on this instance of the filter.
	 */
	public void destroy() {
		// finalize
	}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}