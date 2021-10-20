
from database import WizfitsDatabase
from require import require_url
from random import randint
from threading import Thread

import asyncio

pyjsps = require_url("https://effyiex.github.io/PyJsPs/pyjsps.py")

DATABASE = WizfitsDatabase("accounts")
PORT = 3001
UUID_LENGTH = 64

def generate_uuid():
    uuid = None
    while True:
        uuid = str()
        for i in range(UUID_LENGTH):
            uuid += str(randint(0, 9))
        for account in DATABASE.entries:
            if account.get("uuid") == uuid:
                break
        else:
            break
    return uuid

def registration(args):
    if 2 <= len(args):
        already_exists = False
        for account in DATABASE.entries:
            if account.get("username").lower() == args[0].lower():
                already_exists = True
                break
        if 4 <= len(args[0]) and not already_exists:
            if 6 <= len(args[1]):
                uuid = None
                account = {
                    "username": args[0],
                    "password": args[1],
                    "uuid": generate_uuid()
                }
                DATABASE.add_entry(account)
                return pyjsps.JsPacket("SUCCESS", [account.get("uuid")])
    return pyjsps.JsPacket("ERROR", ["registration failed."])

def login(args):
    if 2 <= len(args):
        for account in DATABASE.entries:
            if account.get("username").lower() == args[0].lower():
                if account.get("password") == args[1]:
                    return pyjsps.JsPacket("SUCCESS", [account.get("uuid")])
    return pyjsps.JsPacket("ERROR", ["login failed."])

def get_user_from_uuid(uuid):
    for account in DATABASE.entries:
        if account.get("uuid") == uuid:
            return account
    return None

def connection(packet):
    if packet.label == "REGISTRATION": return registration(packet.args)
    elif packet.label == "LOGIN": return login(packet.args)
    else: return pyjsps.JsPacket("ERROR", ["unknown request."])

SOCKET = pyjsps.JsSocket(PORT, connection)

def socket_thread():
    loop = asyncio.new_event_loop()
    SOCKET.listen_forever(loop)

def start_server():
    Thread(target=socket_thread).start()
