<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request);
    out.println(output.createLogin());
    
    if (((userPackage.User)session.getAttribute("user")) == null 
            && request.getParameter("email") != null
            && request.getParameter("password") != null) {
        inputPackage.Input input = new inputPackage.Input();
        boolean validLogin = input.login(request.getParameter("email"), request.getParameter("password"));

        out.println(request.getParameter("email") + " " + request.getParameter("password"));
        if (validLogin) {
            out.println("<p>Login Successful</p>");
            userPackage.User user = input.getUserDetails(request.getParameter("email"));
            session.setAttribute("user", user);
            session.setAttribute("userType", user.getUserType());
            // redirect to home page
        } else {
            out.println("<p>Try Again. Login Unsuccessful</p>");
        }
    } else {
        // redirect to home page
    }
%>