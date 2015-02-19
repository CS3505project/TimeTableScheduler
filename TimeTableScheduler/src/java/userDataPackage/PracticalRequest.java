/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author cdol1
 */
public class PracticalRequest extends userRequest{
    
    public PracticalRequest(HttpServletRequest request, userPackage.User user){
        super(request, user);
    }
    
}
