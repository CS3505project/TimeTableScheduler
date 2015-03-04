<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="CourseRequest" class="userDataPackage.CourseRequest" scope="session" />
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        CourseRequest.setValues(request, user);
        if (CourseRequest.isFormLoaded()) {
            CourseRequest.setCourse((String)request.getParameter("course"));
            CourseRequest.setGroup((String)request.getParameter("group"));
        }
%>
        <div class="hidden" name="context" value="addGroupToCourse" data-userId="<%= user.getUserID() %>"></div>
        <h1 class="banner">Add Group To Course</h1>
        <hgroup>
        	<h1>Add Group To Course</h1>
        </hgroup>
        <form action="addGroupToCourse.jsp" method="GET">
            <div>
                <label for="course">Course:</label>
                <select name="course" id="course">
                    <% out.println(output.createCourseList()); %>
                </select>
            </div>
            <div>
                <label for="group">Groups:</label>
                <select name="group" id="group">
                    <% out.println(output.createAllGroupDropDown()); %>
                </select>
            </div>
            <div>
                <label for="submit">Submit:</label>
                <input type="submit" id="submit" value="Next">
            </div>
        </form>
<% 
        if (CourseRequest.numErrors() > 0) {
            out.println(output.displayErrors(CourseRequest.numErrors(), CourseRequest.getErrors()));
        } else if(CourseRequest.addGroupToCourse()) {
            response.sendRedirect("index.jsp");
        } else {
            out.println(output.displayErrors(1, "Group already added to course"));
        }
        CourseRequest.setFormLoaded(true);
        out.println(output.createFooter());
    } else {
        
%>
    <jsp:forward page="/login.jsp" />
<%
        
    }
%>
