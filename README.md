# Earthquake datahost
https://spring.io/guides/gs/accessing-data-rest
https://spring.io/guides/gs/accessing-data-mysql
https://spring.io/guides/gs/rest-service
https://start.spring.io/

docker-compose -f docker-compose.yaml up --build -d
docker-compose -f docker-compose.yaml down

http://localhost:8085/api/greeting?name=John
http://localhost:8085/api/getAllEarthquakes
curl -X POST http://localhost:8085/api/addNewEarthquake -d "title=M 6.5 - 42 km W of Sola, Vanuatu" -d "magnitude=6.5" -d "datetimeStr=16-08-2023 12:47" -d "latitude=-13.8814" -d "longitude=167.158"

## Dataset
https://www.kaggle.com/datasets/warcoder/earthquake-dataset
earthquake_1995-2023.csv

Datasets contain records of 1000 earthquakes from 5/5/1995 to 1/1/2023. The meaning of all columns is as follows:

title: title name given to the earthquake
magnitude: The magnitude of the earthquake
date_time: date and time
cdi: The maximum reported intensity for the event range
mmi: The maximum estimated instrumental intensity for the event
alert: The alert level - “green”, “yellow”, “orange”, and “red”
tsunami: "1" for events in oceanic regions and "0" otherwise
sig: A number describing how significant the event is. Larger numbers indicate a more significant event. This value is determined on a number of factors, including: magnitude, maximum MMI, felt reports, and estimated impact
net: The ID of a data contributor. Identifies the network considered to be the preferred source of information for this event.
nst: The total number of seismic stations used to determine earthquake location.
dmin: Horizontal distance from the epicenter to the nearest station
gap: The largest azimuthal gap between azimuthally adjacent stations (in degrees). In general, the smaller this number, the more reliable is the calculated horizontal position of the earthquake. Earthquake locations in which the azimuthal gap exceeds 180 degrees typically have large location and depth uncertainties
magType: The method or algorithm used to calculate the preferred magnitude for the event
depth: The depth where the earthquake begins to rupture
latitude / longitude: coordinate system by means of which the position or location of any place on Earth's surface can be determined and described
location: location within the country
continent: continent of the earthquake hit country
country: affected country
