<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ModuleRequest" class="userDataPackage.ModuleRequest" scope="session">
    <jsp:setProperty name="ModuleRequest" property="*"/>
</jsp:useBean>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        ModuleRequest.setValues(request, user);
        if (ModuleRequest.isFormLoaded()) {
            ModuleRequest.setCourse((String)request.getParameter("course"));
            ModuleRequest.setModuleName((String)request.getParameter("moduleName"));
            ModuleRequest.setModuleCode((String)request.getParameter("moduleCode"));
        }
%>
        <div class="hidden" name="context" value="addModule" data-userId="<%= user.getUserID() %>"></div> 
        <h1 class="banner">Add Module</h1>
        <hgroup>
        	<h1>Add Module</h1>
        </hgroup>
        <form action="addModule.jsp" method="GET">
            <div>
                <label for="course">Course:</label>
                <select name="course" id="course">
                    <% out.println(output.createCourseList()); %>
                </select>
            </div>
            <div>
                <label for="moduleName">Name:</label>
                <input type="text" name="moduleName" id="moduleName" value="<%= ModuleRequest.getModuleName() %>" required="required"><br>
            </div>
            <div>
                <label for="moduleCode">Code:</label>
                <input type="text" name="moduleCode" id="moduleCode" value="<%= ModuleRequest.getModuleCode() %>" required="required"><br>
            </div>
            <div>
                <label for="submit">Submit:</label>
                <input type="submit" id="submit" value="Next">
            </div>
        </form>
<% 
        if (ModuleRequest.numErrors() > 0) {
            out.println(output.displayErrors(ModuleRequest.numErrors(), ModuleRequest.getErrors()));
        }
        
        if(ModuleRequest.createModule()) {
            response.sendRedirect("index.jsp");
        }
        ModuleRequest.setFormLoaded(true);
        out.println(output.createFooter());
    } else {
        
%>
    <jsp:forward page="/login.jsp" />
<%
        
    }
%>
