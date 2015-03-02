<%@page import="timeTablePackage.TimeTable"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        session.setAttribute("prevRequest", request.getRequestURI());
        
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        TimeTable timeTable = TimeTable.getPreSetTimeTable();
        timeTable.setDisplayWeek((String)request.getParameter("displayDate"));
        timeTable.setupTimeSlots();
        timeTable.initialiseTimeTable(user.getUserID());
        
        
        out.println(output.createUserTimeTable(timeTable, (String)request.getParameter("filter")));
        out.println(output.createTimeTableNav(timeTable.getDisplayWeek(), request));
        //out.println(output.createDummyTable());
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
