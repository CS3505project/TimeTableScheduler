<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="CourseRequest" class="userDataPackage.CourseRequest" scope="session">
    <jsp:setProperty name="CourseRequest" property="*"/>
</jsp:useBean>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        CourseRequest.setValues(request, user);
        CourseRequest.setGroup((String)request.getParameter("group"));
        CourseRequest.setModuleName((String)request.getParameter("moduleName"));
        CourseRequest.setModuleCode((String)request.getParameter("moduleCode"));
%>
        
        <hgroup>
        	<h1>Add Course</h1>
        </hgroup>
        <form action="addCourse.jsp" method="GET">
            <label for="group">Group:</label>
            <select name="group" id="group">
                <% out.println(output.createYearGroupList()); %>
            </select>
            <label for="moduleName">Module Name:</label>
            <input type="text" name="moduleName" id="moduleName" value="<%= CourseRequest.getModuleName() %>" required="required"><br>
            <label for="moduleCode">Module Code:</label>
            <input type="text" name="moduleCode" id="moduleCode" value="<%= CourseRequest.getModuleCode() %>" required="required"><br>
            <label for="submit">Submit:</label>
            <input type="submit" id="submit" value="Next">
        </form>
        <% if (CourseRequest.isFormLoaded()) { %>
            <div class="errors">
                <h1><span><% out.println(CourseRequest.numErrors()); %></span></h1>
                <p>
                    <% out.println(CourseRequest.getErrors()); %>
                </p>
            </div>
<%
        }
        
        if(CourseRequest.createModule()) {
            response.sendRedirect("index.jsp");
        }
        CourseRequest.setFormLoaded(true);
        out.println(output.createFooter());
    } else {
        
%>
    <jsp:forward page="/login.jsp" />
<%
        
    }
%>

