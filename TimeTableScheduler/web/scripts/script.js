function createHelp(){
	//find context node to find out what page we need to display help about
	var contextNode = document.getElementById("context");
        var context = "placeholder";//get this attribute from the context node
        var helpNode = document.getElementByID("help");
        alert("just a test");
        
        //set the onclick method for the help link to displayHelp for the appropriate context
        var onclickMethod = "displayHelp('" + context + "')";
        helpNode.setAttribute("onclick",onclickMethod);
}

function displayHelp(context){
	//Create the popup box node
        var helpBox = document.createElement("div");
        helpBox.setAttribute("class","popup");
        helpBox.setAttribute("id","helpPopup");
        
        //Create the button for removing the help dialog
        var closeButton = document.createElement("button");
        closeButton.setAttribute("onlick","removeHelp()");
        closeButton.innerHTML = "Got it!";
        
        //Switch on the context name, if the context is meeting, get info from the meetong JSON file
        switch(context){
        case "placeholder":
            helpBox.innerHTML = "blah blah blah";//add ajax stuff to get the context help from JSON files
            break;
        default:
            helpBox.innerHTML = "Error";//add ajax stuff to get the context help from JSON files
        }
        //add the close button to the box
        helpBox.appendChild(closeButton);
        //adds popup to page
        document.body.appendChild(helpBox);     
}
function removeHelp(){
        var helpBox = document.getElementById("helpPopup");
        helpBox.parentNode.removeChild(helpBox);
}
