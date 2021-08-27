// Constants ------------------------------------------------------------------------------
var SERVER_URL = "http://localhost:8080";
var CONTAINER = document.getElementById('container');


// Home Component
var homeComponent = function() {

}

// Login Component ------------------------------------------------------------------------
var loginComponent = function() {
    clearContainer();
    CONTAINER.innerHTML = `
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
    var passwd = document.getElementById('loginPassword').value;

    var apiUrl = SERVER_URL + '/login';

    fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username,
            passwd
        }),
    })
    .then(res => {
        var auth = res.headers.get("Authorization");
        localStorage.setItem('at',auth);
    })
    .then(res => res.json())
    .then(res => {
        if(res) {
            console.log(res);
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
    router('/'); 
};