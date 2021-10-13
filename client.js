
const SOCKET = new PySocket("127.0.0.1", 785);
SOCKET.debug = false;

var form;
var formHeader;
var usernameInput;
var passwordInput;
var loginButton;
var registerButton;

var sessionId;

var submit = function(packet) {
  if(packet.args[0] != "success") return;
  localStorage.setItem("wizfits.session.id", packet.args[1]);
  document.body.removeChild(form);
}

var crypt = async function(message) {
  const msgUint8 = new TextEncoder().encode(message);
  const hashBuffer = await crypto.subtle.digest('SHA-256', msgUint8);
  const hashArray = Array.from(new Uint8Array(hashBuffer));
  const hashHex = new String(hashArray.map(b => b.toString(16).padStart(2, '0')).join(''));
  console.log("Crypt: " + hashHex);
  return hashHex;
}

window.addEventListener("load", function() {

  sessionId = localStorage.getItem("wizfits.session.id");

  if(sessionId != undefined)
  return;

  form = document.createElement("div");
  form.style.position = "absolute";
  form.style.left = "50%";
  form.style.top = "50%";
  form.style.transform = "translate(-50%, -50%)";
  form.style.background = "#111";
  form.style.width = "24em";
  form.style.height = "13.5em";
  form.style.boxShadow = "0 0 16px #222";

  formHeader = document.createElement("div");
  formHeader.innerHTML = "Wizfits-Login";
  formHeader.style.color = "#FFF";
  formHeader.style.textAlign = "center";
  formHeader.style.position = "absolute";
  formHeader.style.left = "0.65em";
  formHeader.style.top = "0.65em";
  formHeader.style.width = "calc(100% - 1.3em)"
  formHeader.style.height = "2em";
  formHeader.style.lineHeight = "2em";
  formHeader.style.background = "#555559";

  usernameInput = document.createElement("input");
  usernameInput.style.position = "absolute";
  usernameInput.style.left = "50%";
  usernameInput.style.top = "5em";
  usernameInput.placeholder = "Username here...";
  usernameInput.style.width = "75%";
  usernameInput.style.background = "#555559";
  usernameInput.style.padding = "0.5em";
  usernameInput.style.border = "none";
  usernameInput.style.textIndent = "0.15em";
  usernameInput.style.color = "#FFF";
  usernameInput.style.transform = "translateX(-50%)";
  usernameInput.style.outline = "none";

  passwordInput = document.createElement("input");
  passwordInput.type = "password";
  passwordInput.style.position = "absolute";
  passwordInput.style.left = "50%";
  passwordInput.style.top = "9em";
  passwordInput.placeholder = "Password here...";
  passwordInput.style.width = "75%";
  passwordInput.style.background = "#555559";
  passwordInput.style.padding = "0.5em";
  passwordInput.style.border = "none";
  passwordInput.style.textIndent = "0.15em";
  passwordInput.style.color = "#FFF";
  passwordInput.style.transform = "translateX(-50%)";
  passwordInput.style.outline = "none";

  loginButton = document.createElement("button");
  loginButton.innerHTML = "Login";
  loginButton.style.position = "absolute";
  loginButton.style.top = "13em";
  loginButton.style.left = "72.5%";
  loginButton.style.width = "37.5%";
  loginButton.style.height = "2em";
  loginButton.style.transform = "translateX(-50%)";
  loginButton.style.background = "#555559";
  loginButton.style.border = "none";
  loginButton.style.color = "#DDD";
  loginButton.style.cursor = "pointer";

  registerButton = document.createElement("button");
  registerButton.innerHTML = "Register";
  registerButton.style.position = "absolute";
  registerButton.style.top = "13em";
  registerButton.style.left = "27.5%";
  registerButton.style.width = "37.5%";
  registerButton.style.height = "2em";
  registerButton.style.transform = "translateX(-50%)";
  registerButton.style.background = "#555559";
  registerButton.style.border = "none";
  registerButton.style.color = "#DDD";
  registerButton.style.cursor = "pointer";

  registerButton.addEventListener("click", function(event) {
    SOCKET.send(new PyPacket("REGISTER_ACCOUNT", [usernameInput.value, crypt(passwordInput.value)]), submit);
  });

  loginButton.addEventListener("click", function(event) {
    SOCKET.send(new PyPacket("LOGIN_ACCOUNT", [usernameInput.value, crypt(passwordInput.value)]), submit);
  });

  form.appendChild(registerButton);
  form.appendChild(loginButton);
  form.appendChild(passwordInput);
  form.appendChild(usernameInput);
  form.appendChild(formHeader);
  document.body.appendChild(form);

});
