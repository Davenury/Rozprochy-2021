import socket

serverIP = "127.0.0.1"
serverPort = 9876
msg_bytes = (300).to_bytes(4, byteorder='little')

client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(msg_bytes, (serverIP, serverPort))

while True:
    buff, address = client.recvfrom(9876)
    print("received ", int.from_bytes(buff, byteorder="little"))
    break
