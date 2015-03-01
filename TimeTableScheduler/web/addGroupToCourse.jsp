<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="CourseRequest" class="userDataPackage.CourseRequest" scope="session" />
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        CourseRequest.setValues(request, user);
        CourseRequest.setCourse((String)request.getParameter("course"));
        CourseRequest.setGroup((String)request.getParameter("group"));
%>
        
        <hgroup>
        	<h1>Add Group To Course</h1>
        </hgroup>
        <form action="addGroupToCourse.jsp" method="GET">
            <label for="course">Course:</label>
            <select name="course" id="course">
                <% out.println(output.createCourseList()); %>
            </select>
            <label for="group">Groups:</label>
            <select name="group" id="group">
                <% out.println(output.createAllGroupList()); %>
            </select>
            <label for="submit">Submit:</label>
            <input type="submit" id="submit" value="Next">
        </form>
        <% if (CourseRequest.numErrors() > 0) { %>
            <div class="errors">
                <h1><span><% out.println(CourseRequest.numErrors()); %></span></h1>
                <p>
                    <% out.println(CourseRequest.getErrors()); %>
                </p>
            </div>
<%
        }
        
        if(CourseRequest.addGroupToCourse()) {
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
