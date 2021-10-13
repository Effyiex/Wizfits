
from database import WizfitsDatabase
from require import require_url

pyjsps = require_url("https://effyiex.github.io/PyJsPs/pyjsps.py")

ACCOUNT_DATABASE = WizfitsDatabase("accounts")

WIZFITS_SERVER_PORT = 9435
WIZFITS_SERVER = pyjsps.JsSocket(WIZFITS_SERVER_PORT, connection)

def connection(packet):

    return pyjsps.JsPacket("ERROR: Unknown request!")
