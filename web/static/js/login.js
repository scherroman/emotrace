/**
 * Created by nashahzad on 4/17/17.
 */
function sign_in(){
    debugger;
    console.log(window.location.pathname);
    $.ajax({
        type: "POST",
        url: "/login",
        // The key needs to match your method's input parameter (case-sensitive).
        data: JSON.stringify({"login": "yes", "logout": "no", "url": window.location.pathname}),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){
            debugger;
            console.log(data.url);
            window.location.href = data.url;
            },
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
}

function sign_out(){
    $.ajax({
        type: "POST",
        url: "/login",
        // The key needs to match your method's input parameter (case-sensitive).
        data: JSON.stringify({"login": "no", "logout": "yes", "url": window.location.pathname}),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){
            debugger;
            console.log(data.url);

            window.location.href = data.url;
        },
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
}

function mychannel(){
    debugger;
    $.ajax({
        type: "POST",
        url: "/user/mychannel",
        // The key needs to match your method's input parameter (case-sensitive).
        data: JSON.stringify(""),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){
            debugger;
            console.log(data.user_id);

            window.location.href = "/user/" + data.user_id;
        },
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
}

function getCurrentUser() {
    auth = gapi.auth2.init();
    if(auth.isSignedIn.get()){
        return auth;
    } else
        return null;
}
