$(document).ready(function(){
    //put context in the help button
    var context = $("div[name='context']").attr("value");
    $("#help").click(displayHelp(context));
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
    
}); 

function displayHelp(context){
        
	//Create the popup box node
        var helpBox = document.createElement("div");
        helpBox.setAttribute("class","popup");
        helpBox.setAttribute("id","helpPopup");
        
        //Create the button for removing the help dialog
        var closeButton = document.createElement("button");
        closeButton.onclick = function(){
            $("#helpPopup").hide("drop", {direction: "right"}, 300);
            $("#helpPopup").remove();
            //allow help button to be used more than once (after popup is destroyed)
            document.getElementById("help").onclick = function(){
                displayHelp(context);
            };
        };
        closeButton.innerHTML = "Got it!";
        
        //set the help button to close the popup
        document.getElementById("help").onclick = function(){
            $("#helpPopup").hide("drop", {direction: "right"}, 300);
            $("#helpPopup").remove();
            document.getElementById("help").onclick = function(){
                displayHelp(context);
            };
        };
        
        //Switch on the context name, if the context is meeting, get info from the meetong JSON file
        switch(context){
        case "placeholder":
            helpBox.innerHTML = "this is placeholder text";//add ajax stuff to get the context help from JSON files
            break;
        case "addMeeting":
            helpBox.innerHTML = "this is pMEETINGtext";//add ajax stuff to get the context help from JSON files
            break;
        default:
            helpBox.innerHTML = "Error";//add ajax stuff to get the context help from JSON files
        }
        //add the close button to the box
        helpBox.appendChild(closeButton);
        //adds popup to page
        $("body").append(helpBox); 
        $("#helpPopup").hide();
        $("#helpPopup").show("drop", {direction: "left"}, 300);
}
