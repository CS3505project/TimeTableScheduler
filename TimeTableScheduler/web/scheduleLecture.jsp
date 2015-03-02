<%@page import="timeTablePackage.TimeTable"%>
<%@page import="timeTablePackage.EventPriority"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="LectureRequest" class="userDataPackage.LectureRequest" scope="session">
    <jsp:setProperty name="LectureRequest" property="*"/>
</jsp:useBean>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        if (!((String)session.getAttribute("prevRequest")).equals(request.getRequestURI())) {
            session.setAttribute("LectureRequest", new userDataPackage.LectureRequest());
            session.setAttribute("prevRequest", request.getRequestURI());
        }
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        LectureRequest.setValues(request, user);
        if (!LectureRequest.isSetup()) {
            LectureRequest.setup((String)request.getParameter("moduleCode"),
                                   (String)request.getParameter("duration"),
                                   (String)request.getParameter("semester"));
            
        } else {
            LectureRequest.setStartDate((String)request.getParameter("startDate"));
            LectureRequest.setTime((String)request.getParameter("time"));
            LectureRequest.setVenue((String)request.getParameter("venue"));
            LectureRequest.setEndDate((String)request.getParameter("endDate"));
        }
        
        TimeTable timeTable = TimeTable.getPreSetTimeTable();
        timeTable.setDisplayWeek((String)request.getParameter("displayDate")); 
        timeTable.setupTimeSlots();
        timeTable.initialiseTimeTable(LectureRequest.getUsersInvolved());
        
        LectureRequest.setTimeTable(timeTable);
        
        out.println(output.createSuggestedTimeTable(timeTable,
                                                    LectureRequest.getDuration(),
                                                    EventPriority.MEETING.getPriority(),
                                                    true));
        out.println(output.createTimeTableNav(timeTable.getDisplayWeek(), request));
%>
        <div class="hidden" name="context" value="scheduleMeeting"></div>
        <hgroup class="animate">
        	<h1>Add Lecture</h1>
        	<h2>Step 2 of 2</h2>
        </hgroup>

        <form id="createMeetingForm" action="scheduleLecture.jsp" method="GET">
                <label for="startDate">Start Date:</label>
        	<input type="date" name="startDate" id="startDate" value="<%= LectureRequest.getStartDate() %>" required="required"><br>
                <label for="time">Time:</label>
        	<input type="text" name="time" id="time" value="<%= LectureRequest.getTime() %>" required="required"><br>
                <label for="endDate">End Date:</label>
        	<input type="date" name="endDate" id="endDate" value="<%= LectureRequest.getEndDate() %>" required="required"><br>
                <label for="venue">Venue:</label>
        	<input type="text" name="venue" id="venue" value="<%= LectureRequest.getVenue() %>" required="required"><br>
        	<label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next" class="animate">
        </form>
            <%-- print errors and comit valid values to database --%>
<%
        if (LectureRequest.numErrors() > 0) {
            out.println(output.displayErrors(LectureRequest.numErrors(), LectureRequest.getErrors()));
        } else if (LectureRequest.checkConflict()) {
            out.println(output.displayErrors(1, "You are trying to schedule over an existing event"));
        }
        if(LectureRequest.createLecture()) {
            response.sendRedirect("index.jsp");
        }
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>