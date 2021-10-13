
import sys, os, json

DATABASE_DIRECTORY = f"{sys.path[0]}/__databases__"

if not os.path.exists(DATABASE_DIRECTORY):
    os.mkdir(DATABASE_DIRECTORY)

def byte_shift(obj):
    buffer = bytearray()
    for byte in obj:
        byte += 128
        while 256 <= byte:
            byte -= 256
        buffer.append(byte)
    return buffer

class WizfitsDatabase:

    def __init__(self, label):
        self.label = label
        self.local_file = f"{DATABASE_DIRECTORY}/{label}.db"
        self.entries = []
        if os.path.exists(self.local_file):
            self.load_entries()

    def add_entry(self, entry):
        self.entries.append(entry)
        self.save_entries()

    def remove_entry(self, entry):
        self.entries.remove(entry)
        self.save_entries()

    def clear_entries(self):
        self.entries = []
        self.save_entries()

    def save_entries(self):
        string_to_save = '['
        for entry in self.entries:
            string_to_save += json.dumps(entry) + ','
        string_to_save = string_to_save[:len(string_to_save) - 1] + ']'
        bytes_to_save = byte_shift(string_to_save.encode("utf-8"))
        local_writer = open(self.local_file, "wb")
        local_writer.write(bytes_to_save)
        local_writer.close()

    def load_entries(self):
        local_reader = open(self.local_file, "rb")
        saved_bytes = local_reader.read()
        local_reader.close()
        saved_string = byte_shift(saved_bytes).decode("utf-8")
        self.entries = json.loads(saved_string)
