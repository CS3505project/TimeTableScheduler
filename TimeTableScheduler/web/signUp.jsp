<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="SignUpRequest" class="userDataPackage.SignUpRequest" scope="session"/>
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
    <jsp:setProperty name="MeetingRequest" property="*"/>

<%
    switch (userPackage.UserType.getUserType((String)request.getParameter("type"))){
        case ADMIN:
            if (((userPackage.User)session.getAttribute("user")) != null 
                && ((userPackage.UserType)session.getAttribute("userType")).equals(userPackage.UserType.ADMIN)
                && ((userPackage.User)session.getAttribute("user")).getUserType().equals(userPackage.UserType.ADMIN)) {
                    SignUpRequest.setUserType(userPackage.UserType.ADMIN);
%>
            <div class="signup">
                <form action="signUp.jsp">
                    <label for="id">Admin ID:</label> 
                    <input type="text" id="id" name="id" value="<%= SignUpRequest.getId() %>" required="required"><br>
                    <label for="firstName">First Name:</label> 
                    <input type="text" id="firstName" name="firstName" value="<%= SignUpRequest.getFirstName() %>" required="required"><br>
                    <label for="surname">Surname:</label> 
                    <input type="text" id="surname" name="surname" value="<%= SignUpRequest.getSurname() %>" required="required"><br>
                    <label for="email">eMail:</label>
                    <input type="text" id="email" name="email" value="<%= SignUpRequest.getEmail() %>" required="required"><br>
                    <label for="password">Password:</label> 
                    <input type="password" id="password" name="password" value="<%= SignUpRequest.getPassword() %>" required="required"><br>
                    <label for="rePassword">Re-enter Password:</label> 
                    <input type="password" id="rePassword"name="rePassword" value="<%= SignUpRequest.getRePassword() %>" required="required"><br>
                    <input type="submit" value="Signup">
                </form>
                <p>
                    <%-- print errors and comit valid values to database --%>
                    <%= SignUpRequest.getErrors()%>
                    <%= SignUpRequest.signUp("admin") %>
                </p>
            </div>
<%
        } else { 
%>

            <p>
                You must be Administrator to access this page.
            </p>

<%
        }
        break;
        case LECTURER:
            SignUpRequest.setUserType(userPackage.UserType.LECTURER);
%>
            <div class="signup">
                <form action="signUp.jsp">
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
                    <input type="password" id="password" name="password" value="<%= SignUpRequest.getPassword() %>" required="required"><br>
                    <label for="rePassword">Re-enter Password:</label> 
                    <input type="password" id="rePassword" name="rePassword" value="<%= SignUpRequest.getRePassword() %>" required="required"><br>
                    <input type="submit" value="Sign Up">
                </form>
                <p>
                    <%-- print errors and comit valid values to database --%>
                    <%= SignUpRequest.getErrors()%>
                    <%= SignUpRequest.signUp("lecturer") %>
                </p>
            </div>
<%
        break;
        default:
            SignUpRequest.setUserType(userPackage.UserType.STUDENT);
%>
            <div class="signup">
                <form action="signUp.jsp">
                    <label for="id">Student ID:</label> 
                    <input type="text" id="id" name="id" value="<%= SignUpRequest.getId() %>" required="required"><br>
                    <label for="firstName">First Name:</label> 
                    <input type="text" id="firstName" name="firstName" value="<%= SignUpRequest.getFirstName() %>" required="required"><br>
                    <label for="surname">Surname:</label> 
                    <input type="text" id="surname" name="surname" value="<%= SignUpRequest.getSurname() %>" required="required"><br>
                    <label for="email">eMail:</label> 
                    <input type="text" id="email" name="email" value="<%= SignUpRequest.getEmail() %>" required="required"><br>
                    <label for="password">Password:</label> 
                    <input type="password" id="password" name="password" value="<%= SignUpRequest.getPassword() %>" required="required"><br>
                    <label for="rePassword">Re-enter Password:</label>
                    <input type="password" id="rePassword" name="rePassword" value="<%= SignUpRequest.getRePassword() %>" required="required"><br>
                    <input type="submit" value="Signup">
                </form>
                <p>
                    <%-- print errors and comit valid values to database --%>
                    <%= SignUpRequest.getErrors()%>
                    <%= SignUpRequest.signUp("student") %>
                </p>
            </div>
<%
        break;
    }
    //session.setAttribute("user", SignUpRequest.getUserObject());
    //session.setAttribute("userType", SignUpRequest.getUserType());
%>
        <footer>
                <div><small>&copy; Team 11 2015</small></div>
        </footer>
    </body>
</html>