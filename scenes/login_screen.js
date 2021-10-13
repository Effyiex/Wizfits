
import RENDER_UNIT from "../game.js";

class LoginScreen extends Phaser.Scene {

  preload() {

  }

  create() {
    let { width, height } = this.sys.game.canvas;
    this.add.text(width / 2, height / 2, "Awaiting Login-Data...", {
      fontFamily: "Nunito",
      fontSize: RENDER_UNIT * 1.75
    }).setOrigin(0.5, 0.5);
  }

  update() {
    var sessionId = localStorage.getItem("wizfits.session.id");
    if(sessionId != undefined) {
      this.sys.game.scene.stop("login_screen");
      this.sys.game.scene.start("title_screen");
    }
  }

}

export default LoginScreen;
