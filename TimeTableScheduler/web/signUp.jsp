<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="SignUpRequest" class="userDataPackage.SignUpRequest" scope="session">
    <jsp:setProperty name="SignUpRequest" property="*"/>
</jsp:useBean>
<%
    SignUpRequest.setValues(request, null);
%>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Timetable Scheduling</title>
        <link rel="stylesheet" title="default" href="styles/styles.css">
        <script type="text/javascript" src="scripts/script.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    </head>
    <body>

<%
    switch (userPackage.UserType.getUserType((String)request.getParameter("type"))){
        case ADMIN:
            if (((userPackage.User)session.getAttribute("user")) != null 
                && ((userPackage.UserType)session.getAttribute("userType")).equals(userPackage.UserType.ADMIN)
                && ((userPackage.User)session.getAttribute("user")).getUserType().equals(userPackage.UserType.ADMIN)) {
                    SignUpRequest.setUserType(userPackage.UserType.ADMIN);
%>
            <div>
                <form id="signup" action="signUp.jsp" method="POST">
                    <label for="id">Admin ID:</label> 
                    <input type="text" id="id" name="id" value="<%= SignUpRequest.getId() %>" required="required"><br>
                    <label for="firstName">First Name:</label> 
                    <input type="text" id="firstName" name="firstName" value="<%= SignUpRequest.getFirstName() %>" required="required"><br>
                    <label for="surname">Surname:</label> 
                    <input type="text" id="surname" name="surname" value="<%= SignUpRequest.getSurname() %>" required="required"><br>
                    <label for="email">eMail:</label>
                    <input type="text" id="email" name="email" value="<%= SignUpRequest.getEmail() %>" required="required"><br>
                    <label for="password">Password:</label> 
                    <input type="password" id="password" name="password" required="required"><br>
                    <label for="rePassword">Re-enter Password:</label> 
                    <input type="password" id="rePassword"name="rePassword" required="required"><br>
                    <input type="submit" value="Signup">
                </form>
            </div>
<%
        } else { 
            //You must be an administrator to access this page.
            response.sendRedirect("login.jsp");
        }
        break;
        case LECTURER:
            SignUpRequest.setUserType(userPackage.UserType.LECTURER);
%>
            <ul id="signUpMenu">
                <li><a href="signUp.jsp?type=student">Student</a></li>
                <li><a href="signUp.jsp?type=lecturer">Lecturer</a></li>
            </ul>
            <div>
                <form id="signup" action="signUp.jsp" method="POST">
                    <label for="id">Lecturer ID:</label> 
                    <input type="text" id="id" name="id" value="<%= SignUpRequest.getId() %>" required="required"><br>
                    <label for="title">Title:</label> 
                    <input type="text" id="title" name="title" value="<%= SignUpRequest.getTitle() %>" required="required"><br>
                    <label for="firstName">First Name:</label> 
                    <input type="text" id="firstName" name="firstName" value="<%= SignUpRequest.getFirstName() %>" required="required"><br>
                    <label for="surname">Surname:</label> 
                    <input type="text" id="surname" name="surname" value="<%= SignUpRequest.getSurname() %>" required="required"><br>
                    <label for="email">eMail:</label> 
                    <input type="text" id="email" name="email" value="<%= SignUpRequest.getEmail() %>" required="required"><br>
                    <label for="password">Password:</label> 
                    <input type="password" id="password" name="password" required="required"><br>
                    <label for="rePassword">Re-enter:</label> 
                    <input type="password" id="rePassword" name="rePassword" required="required"><br>
                    <input type="submit" value="Sign Up">
                </form>
            </div>
<%
        break;
        default:
            SignUpRequest.setUserType(userPackage.UserType.STUDENT);
%>
            <ul id="signUpMenu">
                <li><a href="signUp.jsp?type=student">Student</a></li>
                <li><a href="signUp.jsp?type=lecturer">Lecturer</a></li>
            </ul>
            <div>
                <form id="signup" action="signUp.jsp" method="POST">
                    <label for="id">Student ID:</label> 
                    <input type="text" id="id" name="id" value="<%= SignUpRequest.getId() %>" required="required"><br>
                    <label for="firstName">First Name:</label> 
                    <input type="text" id="firstName" name="firstName" value="<%= SignUpRequest.getFirstName() %>" required="required"><br>
                    <label for="surname">Surname:</label> 
                    <input type="text" id="surname" name="surname" value="<%= SignUpRequest.getSurname() %>" required="required"><br>
                    <label for="email">eMail:</label> 
                    <input type="text" id="email" name="email" value="<%= SignUpRequest.getEmail() %>" required="required"><br>
                    <label for="password">Password:</label> 
                    <input type="password" id="password" name="password" required="required"><br>
                    <label for="rePassword">Re-enter:</label> 
                    <input type="password" id="rePassword" name="rePassword" required="required"><br>
                    <input type="submit" value="Signup">
                </form>
            </div>
<%
        break;
    } 
    
    // if the form has been loaded then errors are displayed if they occur
    if (SignUpRequest.isFormLoaded()) { 
%>
        <div class="errors">
            <h1><span><% out.println(SignUpRequest.numErrors()); %></span></h1>
            <p>
                <%-- print errors and comit valid values to database --%>
                <%= SignUpRequest.getErrors()%>
            </p>
        </div>
<%
    }
    if (SignUpRequest.signUp()) {
        session.setAttribute("user", SignUpRequest.getUserObject());
        session.setAttribute("userType", SignUpRequest.getUserType());
        response.sendRedirect("index.jsp");
    }
    SignUpRequest.setFormLoaded(true);
%>
        <footer class="loginFooter">
                <small>&copy; Team 11 2015</small>
                <ul>
                    <li><a>about</a></li>
                    <li><a>contact</a></li>
                    <li><a>ding</a></li>
                    <li><a>dong</a></li>
                </ul>
       </footer>
    </body>
</html>