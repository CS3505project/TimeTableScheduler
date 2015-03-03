<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
    out.println(output.createLogin());
    
    if (((userPackage.User)session.getAttribute("user")) == null) {
       if (request.getParameter("email") != null 
               && request.getParameter("password") != null) {
            inputPackage.Input input = new inputPackage.Input();
            boolean validLogin = input.login(request.getParameter("email"), request.getParameter("password"));

            if (validLogin) {
                userPackage.User user = input.getUserDetails(request.getParameter("email"));
                if (user != null) {
                    out.println("<p>Login Successful</p>");
                    session.setAttribute("user", user);
                    session.setAttribute("userType", user.getUserType());
%>
                    <jsp:forward page="/index.jsp" />
<%
                }
            } else {
                out.println("<p>Try Again. Login Unsuccessful</p>");
            }
       }
    } else {
%>
        <jsp:forward page="/index.jsp" />
<%
    }
%>