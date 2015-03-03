<%@page import="userPackage.UserType"%>
<%@page import="timeTablePackage.TimeTable"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {        
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        TimeTable timeTable = TimeTable.getPreSetTimeTable();
        timeTable.setDisplayWeek((String)request.getParameter("displayDate"));
        timeTable.setupTimeSlots();
        timeTable.initialiseTimeTable(user.getUserID());
        
        out.println(output.createUserTimeTable(timeTable, (String)request.getParameter("filter"), false));
        out.println(output.createTimeTableNav(timeTable.getDisplayWeek(), request));
%>
        <div class="hidden" name="context" value="addMeeting" data-userId="<%= user.getUserID() %>"></div> 
        <hgroup>
        	<h1>Add Meeting</h1>
        	<h2>Step 1 of 2</h2>
        </hgroup>
        <form id="createMeetingForm" action="scheduleMeeting.jsp" method="GET">
<% 
            if (user.getUserType().equals(UserType.ADMIN)) {
                out.println(output.createAdminMeetingFormDropdown());
            } else {
                out.println(output.createMeetingFormGroupDropdown(user));
            }
%>
            <div>
                <label for="duration">Duration:</label>
        	<input type="duration" name="duration" id="date" required="required">
            </div>
            <div>
                <label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next" class="animate">
            </div>
        </form>
    <%
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>