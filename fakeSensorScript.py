import paho.mqtt.client as mqtt 
from random import randrange, uniform
import time
import uuid

mqttBroker ="localhost"
port = 1883

client = mqtt.Client(str(uuid.uuid4()))
client.will_set("hska/avg/temperature2", str(0.00), 0,True)
client.connect(mqttBroker, port, 60)

while True:
    randNumber = uniform(5.0, 55.0)
    roundedNumber = "{:.2f}".format(round(randNumber, 2))
    client.publish("hska/avg/temperature2", roundedNumber, 1)
    print("hska/avg/temperature2: " + roundedNumber)
    time.sleep(5)
