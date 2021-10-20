
from database import WizfitsDatabase
from require import require_url
from threading import Thread

import asyncio
import accounts

pyjsps = require_url("https://effyiex.github.io/PyJsPs/pyjsps.py")

DATABASE = WizfitsDatabase("clans")
PORT = 3002

def create_clan(packet):
    if len(packet.args) < 3 or len(packet.args[1]) > 4:
        return pyjsps.JsPacket("ERROR", ["False syntax."])
    for clan in DATABASE.entries:
        if clan.get("name") == packet.args[0]:
            return pyjsps.JsPacket("ERROR", ["Clan-Name already exists."])
        if clan.get("tag") == packet.args[1]:
            return pyjsps.JsPacket("ERROR", ["Clan-Tag already exists."])
    user = accounts.get_user_from_uuid(packet.args[3])
    if "clan" in user:
        return pyjsps.JsPacket("ERROR", ["User is already in a clan."])
    clan = {
        "name": packet.args[0],
        "tag": packet.args[1],
        "members": {
            user.get("uuid"): "leader"
        }
    }
    user["clan"] = clan.get("name")
    DATABASE.add_entry(clan)
    return pyjsps.JsPacket("SUCCESS", ["Successfully created a clan."])

def change_clan_tag(packet):
    if len(packet.args) < 2:
        return pyjsps.JsPacket("ERROR", ["False syntax."])
    user = accounts.get_user_from_uuid(packet.args[0])
    if not "clan" in user:
        return pyjsps.JsPacket("ERROR", ["User has no clan."])
    users_clan = user.get("clan")
    clan_user = users_clan.get("members").get(user.get("uuid"))
    if clan_user != "leader":
        return pyjsps.JsPacket("ERROR", ["Not enough permissions."])
    for clan in DATABASE.entries:
        if clan.get("tag") == packet.args[1]:
            return pyjsps.JsPacket("ERROR", ["Clan-Tag already exists."])
    users_clan["tag"] = packet.args[1]
    return pyjsps.JsPacket("SUCCESS", ["Successfully changed the clan's tag."])

def invite_member(packet):
    if len(packet.args) < 2:
        return pyjsps.JsPacket("ERROR", ["False syntax."])
    user = accounts.get_user_from_uuid(packet.args[0])
    if not "clan" in user:
        return pyjsps.JsPacket("ERROR", ["User has no clan."])
    users_clan = user.get("clan")
    clan_user = users_clan.get("members").get(user.get("uuid"))
    if clan_user != "leader":
        return pyjsps.JsPacket("ERROR", ["Not enough permissions."])
    for account in accounts.DATABASE.entries:
        if account.get("username").lower() == packet.args[1].lower():
            account["clan-invitation"] = users_clan.get("name")
            return pyjsps.JsPacket("SUCCESS", ["User has been invited."])
    return pyjsps.JsPacket("ERROR", ["User not found."])

def connection(packet):
    if packet.label == "CREATE_CLAN": create_clan(packet)
    elif packet.label == "INVITE_MEMBER": invite_member(packet)
    elif packet.label == "CHANGE_TAG": change_clan_tag(packet)
    else: return pyjsps.JsPacket("ERROR", ["Unknown request."])

SOCKET = pyjsps.JsSocket(PORT, connection)

def socket_thread():
    loop = asyncio.new_event_loop()
    SOCKET.listen_forever(loop)

def start_server():
    Thread(target=socket_thread).start()
