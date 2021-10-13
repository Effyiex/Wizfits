
import TitleScreen from "./scenes/title_screen.js";
import LoginScreen from "./scenes/login_screen.js";

const GAME_CONFIG = {
  width: 1920,
  height: 1080,
  type: Phaser.AUTO,
  backgroundColor: '#222'
}

const RENDER_UNIT = (GAME_CONFIG.width + GAME_CONFIG.height) / 256;

const GAME_INSTANCE = new Phaser.Game(GAME_CONFIG);

GAME_INSTANCE.scene.add("title_screen", TitleScreen);
GAME_INSTANCE.scene.add("login_screen", LoginScreen);
GAME_INSTANCE.scene.start("login_screen");

export default RENDER_UNIT;
