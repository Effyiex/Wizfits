
const HOST = "127.0.0.1" // LOCALHOST for debugging-purposes

const ACCOUNTS_SOCKET = new PySocket(HOST, 3001);
ACCOUNTS_SOCKET.debug = false;

export default ACCOUNTS_SOCKET;
