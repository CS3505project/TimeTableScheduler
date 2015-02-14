<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request);
    out.println(output.createLogin());
    
    inputPackage.Input input = new inputPackage.Input();
    boolean validLogin = input.login((request.getParameter("email"), request.getParameter("password"));

    if (validLogin) {
        out.println("<p>Login Successful</p>");
        User user = input.getUserDetails(request.getParameter("email");
        session.setAttribute("user", user));
        session.setAttribute("userType", user.toString())
    } else {
        out.println("<p>Try Again. Login Unsuccessful</p>");
    }
%>