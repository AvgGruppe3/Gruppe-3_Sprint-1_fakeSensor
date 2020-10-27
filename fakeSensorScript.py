import paho.mqtt.client as mqtt 
from random import randrange, uniform
import time
import uuid

mqttBroker ="localhost"
port = 1883
topic = "hska/avg/temperature2"

client = mqtt.Client(str(uuid.uuid4()))
client.will_set(topic, -273.2, 1, True)
client.connect(mqttBroker, port, 60) 

while True:
    randNumber = uniform(5.0, 55.0)
    roundedNumber = "{:.2f}".format(round(randNumber, 2))
    client.publish(topic , roundedNumber)
    print(f"{topic}: {roundedNumber}")
    time.sleep(5)
