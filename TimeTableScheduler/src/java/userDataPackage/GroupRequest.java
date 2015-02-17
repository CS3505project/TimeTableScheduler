/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import groupPackage.Group;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author cdol1
 */
public final class GroupRequest extends userRequest{
    Group group;
    
    /**
     *
     */
    public GroupRequest(HttpServletRequest request, userPackage.User user, Group group ){
        super(request, user);
        this.group = group;
    }
}
