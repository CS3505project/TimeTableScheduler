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
public class ModuleRequest extends userRequest{
    
    public ModuleRequest(HttpServletRequest request, userPackage.User user){
        super(request, user);
    }
    
}