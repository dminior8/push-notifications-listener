# Push Notifications Listener

Spring Boot application that listens to the ```pushQueue``` on RabbitMQ and processes messages for push notifications.
## Technologies Used
- Java 21 (JDK 21)
- Maven 3.8.x or higher
- Docker

### Clone the repository
```bash
git clone git@github.com:dminior8/push-notifications-listener.git
```
```bash
cd push-notifications-listener
```

### Build and Run with Docker
To build and run the project using Docker, follow these steps:

```bash
docker build -t push-notifications-listener .
docker run -p 8082:8080 push-notifications-listener
```
The service will be accessible at http://localhost:8082.

## Related

To correctly run the entire system, make sure you also have the main repository running, which can be found here: [Notifications Service](https://github.com/dminior8/notifications-service).

Make sure both services are up and running to ensure correct message consumption from RabbitMQ.