
import RENDER_UNIT from "../game.js";
import ACCOUNTS_SOCKET from "../engine/client_sockets.js";
import DOM_CONTAINER from "../engine/dom_elements.js";

class LoginScreen extends Phaser.Scene {

  preload() {
    this.load.audio("button", "../assets/button.wav");
  }

  create() {
    let { width, height } = this.sys.game.canvas;
    this.usernameInput = document.createElement("input");
    this.usernameInput.placeholder = "Username here...";
    this.passwordInput = document.createElement("input");
    this.passwordInput.placeholder = "Password here...";
    this.passwordInput.type = "password";
    DOM_CONTAINER.set_place_origin(0.5, 0);
    DOM_CONTAINER.load(this.usernameInput, width / 2, height / 2 - RENDER_UNIT * 4.5, RENDER_UNIT * 40, RENDER_UNIT * 3);
    DOM_CONTAINER.load(this.passwordInput, width / 2, height / 2, RENDER_UNIT * 40, RENDER_UNIT * 3);
    this.add.rectangle(width / 2, height / 2, RENDER_UNIT * 64, RENDER_UNIT * 48, 0x111111);
    this.add.text(width / 2, height / 2 - RENDER_UNIT * 12, "Wizfits Login-Form", {
      fontFamily: "Nunito",
      fontSize: RENDER_UNIT * 6
    }).setOrigin(0.5, 0.5);
    this.loginButton = this.add.text(width / 2 + RENDER_UNIT * 12, height / 2 + RENDER_UNIT * 10, "Login", {
      fontFamily: "Nunito",
      fontSize: RENDER_UNIT * 2.25
    });
    this.loginButton.setOrigin(0.5);
    this.loginButton.setInteractive({
      cursor: "pointer"
    });
    this.loginButton.on("pointerdown", () => {
      ACCOUNTS_SOCKET.send(new PyPacket("LOGIN", [this.usernameInput.value, this.passwordInput.value]), function(packet) {
        if(packet.label.trim() == "SUCCESS")
        localStorage.setItem("wizfits.account.uuid", packet.args[0]);
        console.log("Login | " + packet.label);
      });
      this.sound.play("button", {
        volume: 0.5
      });
    });
    this.registerButton = this.add.text(width / 2 - RENDER_UNIT * 12, height / 2 + RENDER_UNIT * 10, "Register", {
      fontFamily: "Nunito",
      fontSize: RENDER_UNIT * 2.25
    });
    this.registerButton.setOrigin(0.5);
    this.registerButton.setInteractive({
      cursor: "pointer"
    });
    this.registerButton.on("pointerdown", () => {
      ACCOUNTS_SOCKET.send(new PyPacket("REGISTRATION", [this.usernameInput.value, this.passwordInput.value]), function(packet) {
        if(packet.label.trim() == "SUCCESS")
        localStorage.setItem("wizfits.account.uuid", packet.args[0]);
        console.log("Registration | " + packet.label);
      });
      this.sound.play("button", {
        volume: 0.5
      });
    });
  }

  update() {
    var uuid = localStorage.getItem("wizfits.account.uuid");
    if(uuid != undefined) {
      DOM_CONTAINER.unload();
      this.sys.game.scene.stop("login_screen");
      this.sys.game.scene.start("title_screen");
    }
  }

}

export default LoginScreen;
