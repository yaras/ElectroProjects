import RPi.GPIO as GPIO
import time
from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import parse_qs, urlparse

pinz = [8, 10, 12, 16, 18, 22]

def turn_off():
	for p in pinz:
		GPIO.output(p, GPIO.LOW)

def turn(n):
	GPIO.output(pinz[n], GPIO.HIGH)

class S(BaseHTTPRequestHandler):
	def do_GET(self):
		print('get received')
		n = parse_qs(urlparse(self.path).query).get('n', None)

		if n and len(n) > 0:
			n = n[0]
			
			turn_off()
	
			for i in range(0, int(n)):
				turn(i)

		self.send_response(200)
		self.send_header('Content-type', 'text/html')
		self.end_headers()

		self.wfile.write("{}".format(n).encode('utf-8'))

def run(server_class=HTTPServer, handler_class=S, port=80):
	server_address = ('', port)
	httpd = server_class(server_address, handler_class)
	print('Starting httpd...')
	httpd.serve_forever()

if __name__ == "__main__":

	print('Starting...')

	GPIO.cleanup()
	GPIO.setmode(GPIO.BOARD)

	for p in pinz:
		GPIO.setup(p, GPIO.OUT)

	run()

	GPIO.cleanup()

