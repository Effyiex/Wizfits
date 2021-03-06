
import RENDER_UNIT from "../game.js";

class TitleScreen extends Phaser.Scene {

  preload() {
    this.load.image("wizfits_icon", "assets/favicon.png");
    this.load.audio("button", "../assets/button.wav");
  }

  create() {
    let { width, height } = this.sys.game.canvas;
    this.titleLabel = this.add.text(width / 2, height / 2 - RENDER_UNIT * 16, "Wizfits", {
      fontFamily: "Josefin Sans",
      fontSize: RENDER_UNIT * 10
    });
    this.titleLabel.setOrigin(0.5, 0.5);
    this.authorLabel = this.add.text(width - RENDER_UNIT, height - RENDER_UNIT, "A game created by Effyiex", {
      fontFamily: "Nunito",
      fontSize: RENDER_UNIT * 2
    });
    this.authorLabel.setOrigin(1, 1);
    this.gameIcon = this.add.image(width / 2 - RENDER_UNIT * 3.6, height / 3 - RENDER_UNIT * 4, "wizfits_icon");
    this.gameIcon.displayWidth = RENDER_UNIT * 5;
    this.gameIcon.displayHeight = RENDER_UNIT * 5;
    this.logoutButton = this.add.text(width - RENDER_UNIT * 0.75, RENDER_UNIT * 0.25, "Logout", {
      fontFamily: "Nunito",
      fontSize: RENDER_UNIT * 2
    });
    this.logoutButton.setOrigin(1, 0);
    this.logoutButton.setInteractive({
      cursor: "pointer"
    });
    this.logoutButton.on("pointerdown", () => {
      this.sound.play("button", {
        volume: 0.5
      });
      setTimeout(function() {
        localStorage.removeItem("wizfits.account.uuid");
        location.reload();
      }, 512);
    });
  }

  update() {

  }

}

export default TitleScreen;
