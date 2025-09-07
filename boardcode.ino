#include <math.h>

volatile int flow_input;

String csv_data = "Timestamp,Conductivity\n";

void setup() {
  Serial.begin(9600);
  Serial.println("Serial begins.");
  pinMode(7, INPUT_PULLUP);
  pinMode(11, OUTPUT);
  digitalWrite(11, HIGH);
}

void loop() {
  float shunt = 10000;
  float cs_dist = 0.18;
  float cs_area = 0.0039;

  float voltage_in = 5;
  float voltage_raw = static_cast<float>(analogRead(A0));
  float voltage = voltage_raw * 5 / 1023;
  float current = voltage/shunt;
  
  float totalResistance = voltage_in/current;
  float resistance = totalResistance - shunt;

  float si_conductivity = cs_dist/(cs_area * resistance);
  float unit_conductivity = 10000 * si_conductivity; // Given in µS/cm
  
  csv_data += String(millis() / 1000) + ',' + String(unit_conductivity) + '\n';
  
  // Code for archiving
  Serial.println(csv_data);
  
  // Wait until button pressed
  delay(5000);
  while (digitalRead(7) == HIGH) {}
}
