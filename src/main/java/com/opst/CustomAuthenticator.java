/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opst;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
 
import org.owasp.esapi.Authenticator;
import org.owasp.esapi.User;
import org.owasp.esapi.errors.AuthenticationException;
import org.owasp.esapi.errors.EncryptionException;
import org.owasp.esapi.reference.AbstractAuthenticator;
import org.owasp.esapi.reference.DefaultUser;
import org.owasp.esapi.reference.FileBasedAuthenticator;

//import com.esapi.accesscontrol.AccessControl;
 
public class CustomAuthenticator extends AbstractAuthenticator {
    
     
    private static final CustomAuthenticator authImpl  = new CustomAuthenticator();
     
    private static Map<String, String> userCredentials = new HashMap<String, String>();
     
    static {
        userCredentials.put("mkoran", "mkoran");
    }
     
    public CustomAuthenticator() {
         
    }
     
    public static Authenticator getInstance(){
        return authImpl;
    }
 
 
    @Override
    public boolean verifyPassword(User user, String password) {
        String userid = user.getAccountName();
        String value = userCredentials.get(userid);
        if (userid != null && value != null){
            if (password.equals(value)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
 
    @Override
    public User createUser(String accountName, String password1,
            String password2) throws AuthenticationException {
         
        checkPassword(accountName, password1, password2);
         
        User user = getUser(accountName);
        if (user != null) {
            throw new AuthenticationException("User Exists", 
                    "User " + accountName + " exists.");
        }
         
        DefaultUser newUser = loadUser(accountName);
        newUser.resetCSRFToken();
         
        userCredentials.put(accountName, password1);
         
        return newUser;
    }
 
    private DefaultUser loadUser(String accountName) 
            throws AuthenticationException {
         
        DefaultUser newUser = new DefaultUser(accountName);
        newUser.enable();
         
        Set<String> roles = new HashSet<String>();
        newUser.addRoles(roles);
        newUser.setScreenName("John Smith");
         
        return newUser;
    }
 
    private void checkPassword(String accountName, String password1,
            String password2) throws AuthenticationException {
        if (password1 != null && password2 != null) {
            if (! password1.equals(password2)) {
                throw new AuthenticationException("Password mismatch", 
                    "User " + accountName + " needs a matching password entries.");
            }
        } else {
            throw new AuthenticationException("Password required", 
                "User " + accountName + " needs password information.");
        }
    }
 
    @Override
    public String generateStrongPassword() {
        return FileBasedAuthenticator.getInstance()
                .generateStrongPassword();
    }
 
    @Override
    public String generateStrongPassword(User user, String oldPassword) {
        return FileBasedAuthenticator.getInstance()
                .generateStrongPassword(user, oldPassword);
    }
 
    @Override
    public void changePassword(User user, String currentPassword,
            String newPassword, String newPassword2)
            throws AuthenticationException {
         
        String userId = user.getAccountName();
        this.checkPassword(userId, newPassword, newPassword2);
        if (this.verifyPassword(user, currentPassword)) {
            userCredentials.put(userId, newPassword);
        } else {
            throw new AuthenticationException("Password Invalid", 
                    "Please enter the correct password.");
        }
    }
 
    @Override
    public User getUser(long accountId) {
        return null;
    }
     
    @Override
    public User getUser(String accountName) {
        if (userCredentials.containsKey(accountName)) {
            try {
                DefaultUser user = loadUser(accountName);
                return user;
            } catch(AuthenticationException ex) {
                return null;
            }
        }
        return null;
    }
 
    @Override
    public Set getUserNames() {
        throw new UnsupportedOperationException("The Authenticator " +
                "does not support this operation.");
    }
 
    @Override
    public String hashPassword(String password, String accountName)
            throws EncryptionException {
        return FileBasedAuthenticator.getInstance()
                .hashPassword(password, accountName);
    }
 
    @Override
    public void removeUser(String accountName) throws AuthenticationException {
        userCredentials.remove(accountName);
    }
 
    @Override
    public void verifyAccountNameStrength(String accountName)
            throws AuthenticationException {
        throw new UnsupportedOperationException("The Authenticator " +
                "does not support this operation.");
         
    }
 
    @Override
    public void verifyPasswordStrength(String oldPassword, String newPassword,
            User user) throws AuthenticationException {
        throw new UnsupportedOperationException("The Authenticator " +
                "does not support this operation.");
         
    }
}
