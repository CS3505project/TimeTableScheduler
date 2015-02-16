package userDataPackage;

import javax.servlet.http.HttpServletRequest;

/** 
 * A generic representation of a user request.
 * The more specific requests will store all the 
 * relevant request variables, validate them etc 
 * and contain methods for executing against the database if the data is valid.
 * @author lam1
 */
public abstract class userRequest {
    HttpServletRequest request;
    userPackage.User user;
    
    public userRequest(HttpServletRequest request, userPackage.User user){
        this.request = request;
        this.user = user;
    }
}
