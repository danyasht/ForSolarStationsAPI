# ForSolarStationsAPI
This API was created by Danylo Shtohryn, the student of the group IoT-15. This API was created for managing information 
about solar panels, solar stations and clients. Also, this API allows you to create some CSV file with this object's
information.
Every class has the same methods as others do, so to run my programme and take a look on the all methods you need to:
    1.Open an IDE
    2.Paste my code
    3.Open Postman and you should know how to create requests
    4.Here is an example how to write a body in JSON format:
        {
            "name": "John",
            "surname": "Wick",
            "solarStationId": 1
        }
    5.Select POST
    6.In the URL write 'http://localhost:8080/ClientController/Create' the same you should write when you are creating 
    other objects but when you are creating solar panel you need to write this part of code:
    'http://localhost:8080/SolarPanelController/Create'
        {
            "type": "Monocrystalline",
            "power": 200,
            "batteryCapacity": 500,
            "timeOfBatteryUsage": 8,
            "timeOfPanelUsage": 10
        }
    'http://localhost:8080/SolarStationController/Create'
    and for solar station is:
        {
            "solarPanelId": 1,
            "powerOfStation": 100,
            "addressOfInstallation": "123 Main St"
        }
Other methods you can use with postman, but you should edit URL (for example if you need method "get all", choose 
GET and then press SEND (don't forget to create some clients/stations/panels before)).
If you need to update these objects you can copy a "body" from the "create method" and paste into "update method's" 
body.
If you need to create some files don't forget to create a path where you want to store files.
I hope this README file will give you some knowledge how to use this application.
