<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="AddLecturerRequest" class="userDataPackage.AddLecturerRequest" scope="session">
    <jsp:setProperty name="AddLecturerRequest" property="*"/>
</jsp:useBean>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        AddLecturerRequest.setValues(request, user);
        if (AddLecturerRequest.isFormLoaded()) {
            AddLecturerRequest.setLecturer((String)request.getParameter("lecturer"));
            AddLecturerRequest.setModuleCode((String)request.getParameter("moduleCode"));
        }
%>
        <div class="hidden" name="context" value="addModule" data-userId="<%= user.getUserID() %>"></div> 
        <h1 class="banner">Add Lecturer to Module</h1>
        <hgroup>
        	<h1>Add Lecturer to Module</h1>
        </hgroup>
        <form action="addModule.jsp" method="GET">
            <div>
                <label for="moduleCode">Module:</label>
                    <% out.println(output.createModuleDropDown()); %>
            </div>
            <div>
                <label for="lecturer">Lecturer:</label>
                <% out.println(output.createLecturerDropDown()); %>
            </div>
            <div>
                <label for="submit">Submit:</label>
                <input type="submit" id="submit" value="Next">
            </div>
        </form>
<% 
        AddLecturerRequest.setFormLoaded(true);
        if (AddLecturerRequest.numErrors() > 0) {
            out.println(output.displayErrors(AddLecturerRequest.numErrors(), AddLecturerRequest.getErrors()));
        }
        if(AddLecturerRequest.addLecturer()) {
            out.println("Lecturer added successfully.");
        }
        
        out.println(output.createFooter());
    } else {
        
%>
    <jsp:forward page="/login.jsp" />
<%
        
    }
%>
