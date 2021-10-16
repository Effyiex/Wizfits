
class WizfitsDomContainer {

  constructor() {
    this.div = document.createElement("div");
    this.div.style.background = "transparent";
    this.div.style.pointerEvents = "none";
    this.div.style.zLevel = -1;
    this.div.style.position = "absolute";
    this.div.style.left = '0';
    this.div.style.top = '0';
    this.div.style.width = "100%";
    this.div.style.height = "100%";
    this.div.style.padding = '0';
    this.div.style.margin = '0';
    this.elements = [];
    this.placeOriginX = 0;
    this.placeOriginY = 0;
  }

  create(width, height) {
    this.displayWidth = width;
    this.displayHeight = height;
    document.body.appendChild(this.div);
  }

  unload() {
    this.setPlaceOrigin(0, 0);
    for(var i = 0; i < this.elements.length; i++)
    this.div.removeChild(this.elements[i]);
    this.elements = [];
  }

  setPlaceOrigin(x, y) {
    this.placeOriginX = x;
    this.placeOriginY = y;
  }

  load(element, x, y, width, height) {
    x -= width * this.placeOriginX;
    y -= height * this.placeOriginY;
    x = new String(100.0 / this.displayWidth * x) + '%';
    y = new String(100.0 / this.displayHeight * y) + '%';
    width = new String(100.0 / this.displayWidth * width) + '%';
    height = new String(100.0 / this.displayHeight * height) + '%';
    console.log("Element-Bounds | x: " + x + ", y: " + y + ", width: " + width + ", height: " + height);
    element.style.position = "absolute";
    element.style.pointerEvents = "auto";
    element.style.left = x;
    element.style.top = y;
    element.style.width = width;
    element.style.height = height;
    this.elements.push(element);
    this.div.appendChild(element);
  }

}

const DOM_CONTAINER = new WizfitsDomContainer();

export default DOM_CONTAINER;
