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
            CourseRequest.setName((String)request.getParameter("name"));
            CourseRequest.setDepartment((String)request.getParameter("department"));
            CourseRequest.setYear((String)request.getParameter("year"));
        }
%>
        <div class="hidden" name="context" value="addCourse" data-userId="<%= user.getUserID() %>"></div> 
        <h1 class="banner">Add Course</h1>
        <hgroup>
        	<h1>Add Course</h1>
        </hgroup>
        <form action="addCourse.jsp" method="GET">
            <div>
                <label for="course">Code:</label>
                <input type="text" name="course" id="course" value="<%= CourseRequest.getCourse() %>" required="required"><br>
            </div>
            <div>
                <label for="name">Name:</label>
                <input type="text" name="name" id="name" value="<%= CourseRequest.getName() %>" required="required"><br>
            </div>
            <div>
                <label for="department">Department:</label>
                <input type="text" name="department" id="department" value="<%= CourseRequest.getDepartment() %>" required="required"><br>
            </div>
            <div>
                <label for="year">Year:</label>
                <input type="text" name="year" id="year" value="<%= CourseRequest.getYear() %>" required="required"><br>
            </div>
            <div>
                <label for="submit">Submit:</label>
                <input type="submit" id="submit" value="Next">
            </div>
        </form>
<%
        if (CourseRequest.numErrors() > 0) {
            out.println(output.displayErrors(CourseRequest.numErrors(), CourseRequest.getErrors()));
        }
        CourseRequest.setFormLoaded(true);
        if(CourseRequest.createCourse()) {
            out.println("Course created successfully.");
        }
        out.println(output.createFooter());
    } else {
%>
    <jsp:forward page="/login.jsp" />
<%
    }
%>

