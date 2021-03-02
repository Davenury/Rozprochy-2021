import socket

serverIP = "127.0.0.1"
serverPort = 9876
msg = "Python"

client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'utf-8'), (serverIP, serverPort))

while True:
    buff, address = client.recvfrom(9876)
    print("received ", buff.decode("utf-8"))
    break
