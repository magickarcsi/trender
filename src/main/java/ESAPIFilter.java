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


import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.owasp.esapi.User;
import org.owasp.esapi.errors.AuthenticationException;

public abstract class ESAPIFilter{

	private final Logger logger = ESAPI.getLogger("ESAPIFilter");

	private static final String[] obfuscate = { "password" };
        
    public void init(FilterConfig fConfig) throws ServletException {
    }
 
    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }
 
    /**
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        String user = ESAPI.authenticator().getCurrentUser().getAccountName();
            try {
                ESAPI.authenticator().login(req, res);
                chain.doFilter(request, response);
                        
                        } catch (AuthenticationException ex) {
                            
                java.util.logging.Logger.getLogger(ESAPIFilter.class.getName()).log(Level.SEVERE, null, ex);
                RequestDispatcher rd = req.getRequestDispatcher("/login.xhtml");
                rd.forward(request, response);
                return;
            }
         
        
    }
}	