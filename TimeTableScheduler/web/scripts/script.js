$(document).ready(function(){
    //fix form for meeting
    $("#individualSelectDiv").hide();
    
    $('#createMeetingForm input').on('change', function() {
        var checked = $('input[name="withType"]:checked', '#createMeetingForm').val();
        switch (checked){
            case 'personal':
                $("#individualSelect").prop('disabled', true);
                $("#groupSelect").prop('disabled', true);

                $("#individualSelectDiv").hide("drop", {direction: "vertical"}, 300);
                $("#groupSelectDiv").hide("drop", {direction: "vertical"}, 300);
                break;
            case 'group':
                $("#individualSelect").prop('disabled', true);
                $("#groupSelect").prop('disabled', false);

                $("#individualSelectDiv").hide("drop", {direction: "vertical"}, 300);
                $("#groupSelectDiv").show("drop", {direction: "vertical"}, 300);
                break;
            case 'individual':
                $("#individualSelect").prop('disabled', false);
                $("#groupSelect").prop('disabled', true);

                $("#individualSelectDiv").show("drop", {direction: "vertical"}, 300);
                $("#groupSelectDiv").hide("drop", {direction: "vertical"}, 300);
                break;
        }
    });
    //////////////////////////////
    //Create the popup box node //
    var helpBox = document.createElement("div");
    helpBox.setAttribute("class","popup");
    helpBox.setAttribute("id","helpPopup");
    var closeButton = document.createElement("button");
    closeButton.setAttribute("class","animate");
    closeButton.onclick = function(){
        $("#helpPopup").hide("drop", {direction: "left"}, 250);
        //reset help button to show rather than hide popup
        document.getElementById("help").onclick = function(){
            displayHelp(context);
            };
        };
    $(closeButton).html("Close");
    $(helpBox).append("<div><p></p></div>");
    $(helpBox).append(closeButton);
    $("body").append(helpBox); 
    $("#helpPopup").hide();
    //put context in the help button
    var context = $("div[name='context']").attr("value");
    $("#help").click(displayHelp(context));
    
    //set flashing animation for clicked animated class nodes
    $(".animate").click(function() {
        $(this).effect("highlight", {color:"#eeeeff"}, 250 );
    });
    //get info from selectable nodes in timetable
    $(".selectable").click(function() {
        var infoNode = $("div.hidden", this);
        var date = infoNode.attr("data-date");
        var time = infoNode.attr("data-time");
        //fill out the date and time in the form:
    });
}); 

function displayHelp(context){
    //show the popup, it already exists in the DOM but is hidden
    $("#helpPopup").show("drop", {direction: "left"}, 250);
    //set the help button to close the popup
    document.getElementById("help").onclick = function(){
        $("#helpPopup").hide("drop", {direction: "left"}, 250);
        document.getElementById("help").onclick = function(){
            displayHelp(context);
        };
    };
    //Switch on the context name, if the context is meeting, get info from the meetong JSON file
    switch(context){
    case "placeholder":
         $("#helpPopup div p").html("this is placeholder text");//add ajax stuff to get the context help from JSON files
        break;
    case "addMeeting":
        $("#helpPopup div p").html("Meeting help asdasd adg a dg adg a adg a a dg adg adg  adgf adg  gdag dag adg   gddgdgdgdggd<br> asdasdasd");//add ajax stuff to get the context help from JSON files
        break;
    default:
        $("#helpPopup div p").html("No help file for given context");//add ajax stuff to get the context help from JSON files
    }
}
