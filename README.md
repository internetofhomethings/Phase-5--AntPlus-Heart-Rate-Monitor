<h2><strong>Android-App-for-Mobile-Sensors</strong></h2>

This project is an Android App developed using standard Web technologies:

    -HTML5
    -CSS
    -JAVASCRIPT

This project was built using Cordova Android Platform

Version: 3.6.4

and Eclipse

Version: 4.2.1.v20130118-173121-9MF7GHYdG0B5kx4E_SkfZV-1mNjVATf67ZAb7
Build id: M20130204-1200

The App is designed to interface with an ESP8266 using the following Github code:

https://github.com/internetofhomethings/Phase-1-ESP8266-Web-Server-for-Mobile-Sensors

The Smartphone hosting this App needs to be connected to the ESP8266 via Wifi. Once the ESP8266
is running, the Smartphone should connect to the ESP8266 using the following SSID:

ESP8266N4

For the initial release, a GUI will be displayed upon App launch with the following 13 parameters:

1. Baro Temperature
2. Baro Pressure
3. Humidity
4. System Time
5. System Heap
6. System Iterations
7. Latitude
8. Longitude
9. Heart Rate
10. Speed
11. Altitude
12. Heading
13. GPS Timestamp

Revision History:

Initial Release:         July 13, 2015. 

Phase 2: Data Recorder:  August 16, 2015 
Change: Data Recorder added to save data points to a file on an Android device SDCard. The default file saved is:

<SDCARD>/mydata/dsa.csv

Each time the app is run, the default data file is overwritten. The file name should be renamed from the prior
run if it is to be preserved.

Data is saved in CSV format every time a new GPS data set is received. The following fields are saved to the CSV file:

Field                          Units
1. UTC                       - Milliseconds
2. Latitude                  - Degrees
3. Longitude                 - Degrees
4. Speed                     - Mph
5. Altitude                  - Feet
6. Temperature (Baro Sensor) - Degrees F
7. Tempreature (Humid Sensr) - Degrees F
8. Barometric Pressure       - inches Hg
9. Humidity                  - Percent
10.ESP8266 System time       - Seconds
11.ESP8266 Free Heap         - Bytes
12.ESP8266 loop() iteration  - Loops

Phase 4: Mobile App Gadgets:  August 23, 2015 
Changes: 
1. Three real-time data visualization screens added.
---> 1. Speedometer
---> 2. Clock
---> 3. Compass

2. Button added to top navigation bar to use/not use ESP8266 data in log file.


Phase 5: Mobile App Gadgets:  August 31, 2015 
Change:
Added Ant+ Heart Rate Monitor Sensor

