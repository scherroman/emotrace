/**
 * Created by nashahzad on 4/17/17.
 */
var user;
function onSuccess(googleUser) {
    user = googleUser;
    var profile = googleUser.getBasicProfile();
    console.log('Logged in as: ' + profile.getName() + ', now constructing POST form');
    $.ajax({
        type: "POST",
        url: "/login",
        // The key needs to match your method's input parameter (case-sensitive).
        data: JSON.stringify({"name": profile.getName(),
            "googleAccount": profile.getEmail()}),
//            data: profile.getName() + ' ' + profile.getEmail(),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){alert(data);},
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
    $('#my-signin2 span').text("Welcome " + profile.getGivenName());
}
function onFailure(error) {
    console.log(error);
}
function renderButton() {
    gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 240,
        'longtitle': true,
        'color': '#8BC34A',
        'theme': 'none',
        'onsuccess': onSuccess,
        'onfailure': onFailure
    });
}

function signOut() {
    debugger;
    auth = gapi.auth2.init();
    if(auth.isSignedIn.get()){
        var profile = auth.currentUser.get().getBasicProfile();
        console.log('ID: ' + profile.getId());
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());
    }
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect();
    $('#my-signin2 span').text("Sign in with Google");
}

function getCurrentUser() {
    auth = gapi.auth2.init();
    if(auth.isSignedIn.get()){
        return auth;
    } else
        return null;
}
