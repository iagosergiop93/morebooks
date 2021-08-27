// Constants ------------------------------------------------------------------------------
var SERVER_URL = "http://localhost:8080";

// Login Component ------------------------------------------------------------------------
var loginComponent = function() {
    var el = document.getElementById('container');
    el.innerHTML = `
        <img class="logo" src="images/logo.png">
        <form onsubmit="event.preventDefault();">
            <div class="form-field">
                <input id="loginUsername" placeholder="Username">
            </div>
            <div class="form-field">
                <input id="loginPassword" placeholder="Password" type="password">
            </div>
            <button class="submit-btn" onclick="submitLogin()">Login</button>
        </form>
    `;
}

var submitLogin = () => {
    var username = document.getElementById('loginUsername').value;
    var password = document.getElementById('loginPassword').value;

    var apiUrl = SERVER_URL + '/login';

    fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username,
            password
        }),
    }).then(res => {
        if(res.status === 200) {

        }
        else {
            console.log('res', res.body);
            alert('Api call failed');
        }
    });
}

// Main Component ------------------------------------------------------------------------
function _removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

var clearContainer = function() {
    var el = document.getElementById('container');
    _removeAllChildNodes(el);    
}

var router = function(route) {
    if(route == null || route == undefined) {
        console.log("Empty route");
        return;
    }

    if(route.includes('/login')) {
        loginComponent();
    }

    if(route === '/') {
        loginComponent();
    }

}

window.onload = () => {
    clearContainer();
    router('/'); 
};