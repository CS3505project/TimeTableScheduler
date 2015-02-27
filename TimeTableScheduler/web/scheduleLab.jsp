<%@page import="timeTablePackage.EventPriority"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="PracticalRequest" class="userDataPackage.PracticalRequest" scope="session">
    <jsp:setProperty name="PracticalRequest" property="*"/>
</jsp:useBean>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        PracticalRequest.setValues(request, user);
        if (!PracticalRequest.isSetup()) {
            PracticalRequest.setup((String)request.getParameter("moduleCode"),
                                   (String)request.getParameter("duration"),
                                   (String)request.getParameter("semester"));
            
        } else {
            PracticalRequest.setStartDate((String)request.getParameter("startDate"));
            PracticalRequest.setTime((String)request.getParameter("time"));
            PracticalRequest.setVenue((String)request.getParameter("venue"));
            PracticalRequest.setEndDate((String)request.getParameter("endDate"));
        }
        
        out.println(output.createSuggestedTimeTable(PracticalRequest.getTimeTable(),
                                                    PracticalRequest.getDuration(),
                                                    EventPriority.MEETING.getPriority(),
                                                    true));
%>
        <div class="hidden" name="context" value="scheduleMeeting"></div>
        <hgroup class="animate">
        	<h1>Add Practical</h1>
        	<h2>Step 2 of 2</h2>
        </hgroup>

        <form id="createMeetingForm" action="scheduleMeeting.jsp" method="GET">
                <label for="startDate">Start Date:</label>
        	<input type="date" name="startDate" id="startDate" value="<%= PracticalRequest.getStartDate() %>" required="required"><br>
                <label for="time">Time:</label>
        	<input type="text" name="time" id="time" value="<%= PracticalRequest.getTime() %>" required="required"><br>
                <label for="endDate">End Date:</label>
        	<input type="date" name="endDate" id="endDate" value="<%= PracticalRequest.getEndDate() %>" required="required"><br>
                <label for="venue">Venue:</label>
        	<input type="text" name="venue" id="venue" value="<%= PracticalRequest.getVenue() %>" required="required"><br>
        	<label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next" class="animate">
        </form>
        <p>
            <%-- print errors and comit valid values to database --%>
            <%
                out.println(PracticalRequest.getErrors());
                if(PracticalRequest.createPractical()) {
                    System.out.println("redirect here");
                    response.sendRedirect("index.jsp");
                } else {
                    out.println("Unable to create practical");
                }
            %>
        </p>
    <%
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>