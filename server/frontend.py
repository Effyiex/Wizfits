
from socket import *

from threading import Thread

# TODO: HTTPS-Support
import os, ssl

DIR = os.getcwd().replace('\\', '/')
if not DIR.endswith('/'): DIR += '/'
DIR += "game/"

PORT = 3000
SOCKET = socket()

def receive():
    while True:
        client, address = SOCKET.accept()
        Thread(target=connection, args=(client, address)).start()

def compress_headers(headers):
    bytes = bytearray()
    for header in headers:
        bytes += (header + "\r\n").encode("utf-8")
    bytes += b"\r\n"
    return bytes

def connection(client, address):
    bytes = b"ERROR-404: Page / File not found."
    request = client.recv(1024).decode("utf-8").split(' ')
    headers = ["HTTP/1.0 200 OK"]
    if len(request) > 2 and request[0] == "GET":
        get_request = request[1][1:]
        if len(get_request) <= 0:
            get_request = "index.html"
        if ".js" in get_request:
            headers.append("Content-Type: application/javascript; charset=utf-8")
        file = open(f"{DIR}/{get_request}", "rb")
        bytes = file.read()
        file.close()
    client.send(compress_headers(headers) + bytes)
    client.close()

def start_server():
    try: SOCKET.bind(("127.0.0.1", PORT))
    except: print("ERROR: Couldn't bind the Webserver.")
    SOCKET.listen(5)
    Thread(target=receive).start()
