#include <DHT.h>
#include <DHT_U.h>

#define DHTPIN 2          // signal
#define DHTTYPE DHT11     // type: DHT11 or DHT22

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
}

float lasth = 0;
float lastt = 0;

void loop() {
  float t = dht.readTemperature();
  float h = dht.readHumidity();

  if (isnan(t) || isnan(h))
  {
    Serial.println("Error reading temperature/humidity");
  }
  else
  {
    if (lasth != h || lastt != t) {
      lasth = h;
      lastt = t;
      Serial.print("humidity: ");
      Serial.print(h);
      Serial.print(" % ");
      Serial.print("temperature: ");
      Serial.print(t);
      Serial.println(" *C");
    }
  }
}
