Garage Management System
This project is a simple Garage Management System implemented using Spring Boot. The system allows users to park vehicles,
leave parking slots, and check the status of the garage. 
The project incorporates the Singleton and Factory design patterns for better code organization and reusability.

The Singleton pattern is used in the GarageService class to ensure that only one instance of the service exists throughout
the application's lifecycle. 
This is particularly important for maintaining consistent state in the garage, such as the list of occupied and available slots.

Factory Pattern
The Factory pattern is implemented in the VehicleFactory class.
This pattern is used to create instances of Vehicle based on the type of vehicle (e.g., car, jeep, truck). 
This decouples the creation logic from the main business logic, making the code more maintainable and extendable.

Features
Park a Vehicle: Allows parking a vehicle in the garage based on the availability of slots.
Leave a Slot: Frees up a slot when a vehicle leaves.
Check Garage Status: Provides a status report of all slots in the garage.
Error Handling: Handles various exceptions such as invalid slot number, invalid vehicle type, and garage full scenario.


INSTALLATİON
git clone https://github.com/your-username/garage-management-system.git
cd garage-management-system
mvn clean install
mvn spring-boot:run



USAGE

Park a vehicle:
POST /garage/park
Params: plate, color, type
Response: Success message or error message

Leave a Slot:
POST /garage/leave
Params: slotNumber
Response: Success message or error message

Check Garage Status:
GET /garage/status
Response: Status report of the garage


License
This project is licensed under the MIT License.
