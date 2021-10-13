
import os

commands = [

    "exit",
    "stop",
    "quit",
    "help",
    "?"

]

instructions = [

    "Stops Server-System",
    "Stops Server-System",
    "Stops Server-System",
    "Shows all commands",
    "Shows all commands"

]

def listen_to_user():
    while True:
        user_input = input().strip().split(' ')
        executed = commands.index(user_input[0])
        if executed < 0:
            print("[Wizfits-Servers]: ERROR: Unknown command!")
        elif executed <= 2:
            print("[Wizfits-Servers]: Quitting the Server-System...")
            os._exit(0)
        elif executed <= 4:
            print("[Wizfits-Servers]: Here is a list of all commands: ")
            for i in range(len(commands)):
                print(f" - \"{commands[i]}\", {instructions[i]}")

if __name__ == "__main__":
    print("[Wizfits-Servers]: Starting all Server-Systems...")
    import accounts
    import frontend
    accounts.start_server()
    frontend.start_server()
    print("[Wizfits-Servers]: Finished starting all servers.")
    print("[Wizfits-Servers]: Use '?' or \"help\" for help.")
    listen_to_user()
