<%@page import="userPackage.UserType"%>
<%@page import="inputPackage.Input"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="SignUpAdminRequest" class="userDataPackage.SignUpAdminRequest" scope="session">
    <jsp:setProperty name="SignUpAdminRequest" property="*"/>
</jsp:useBean>
<%
    if (((userPackage.User)session.getAttribute("user")) != null 
            && ((userPackage.UserType)session.getAttribute("userType")).equals(userPackage.UserType.ADMIN)
            && ((userPackage.User)session.getAttribute("user")).getUserType().equals(userPackage.UserType.ADMIN)) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());

        SignUpAdminRequest.setValues(request, null);

        if (SignUpAdminRequest.isFormLoaded()) {
            SignUpAdminRequest.setId((String)request.getParameter("id"));
            SignUpAdminRequest.setFirstName((String)request.getParameter("firstName"));
            SignUpAdminRequest.setSurname((String)request.getParameter("surname"));
            SignUpAdminRequest.setEmail((String)request.getParameter("email"));
        }
%>
        <h1 class="banner">Add Administrator</h1>
        <hgroup>
        	<h1>Add Administrator</h1>
        </hgroup>
        <form id="signup" action="signUpAdmin.jsp" method="POST">
            <div>
                <label for="id">Admin ID:</label> 
                <input type="text" id="id" name="id" value="<%= SignUpAdminRequest.getId() %>" required="required"><br>
            </div>
            <div>
                <label for="firstName">First Name:</label> 
                <input type="text" id="firstName" name="firstName" value="<%= SignUpAdminRequest.getFirstName() %>" required="required"><br>
            </div>
            <div>
                <label for="surname">Surname:</label> 
                <input type="text" id="surname" name="surname" value="<%= SignUpAdminRequest.getSurname() %>" required="required"><br>
            </div>
            <div>
                <label for="email">eMail:</label>
                <input type="text" id="email" name="email" value="<%= SignUpAdminRequest.getEmail() %>" required="required"><br>
            </div>
            <div>
                <label for="password">Password:</label> 
                <input type="password" id="password" name="password" required="required"><br>
            </div>
            <div>
                <label for="rePassword">Re-enter Password:</label> 
                <input type="password" id="rePassword"name="rePassword" required="required"><br>
            </div>
            <div>
                <input type="submit" value="Signup">
            </div>
        </form>
<%
         SignUpAdminRequest.setFormLoaded(true);
         if (SignUpAdminRequest.numErrors() > 0) {
             out.println(output.displayErrors(SignUpAdminRequest.numErrors(), SignUpAdminRequest.getErrors()));
         }        
         if (SignUpAdminRequest.signUpAdmin()) {
             out.println("Admin created.");
         } 
         out.println(output.createFooter());
    } else {
        
%>
        <jsp:forward page="/login.jsp" />
<%
        
    }
%>