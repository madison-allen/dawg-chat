# Dawg Chat
This is a project for CS435 Software Engineering. It is intended to be a messaging app for students.

# Render.com
Render is used to host the database and backend service. Everytime a change is pushed to the main branch, those changes are automatically deployed to render. 
To see if the deployment suceed, you can go to [this link](https://dawg-chat.onrender.com/healthcheck) and it should display 200. 
It could take some time to load if the service hasn't be used in a while. This is because the free tier on render powers down the service after a period of inactivity, 
which means it has to turn it on once a request is made.

The database credentials are set as environment variables by render.
They can be access by the following lines of code:
```
System.getenv("INTERNAL_DB_URL");
System.getenv("DB_USER");
System.getenv("DB_PASSWORD");
```
However, this is already done in ```Constants.java```.

*NOTE: This will not work locally. To connect to the database locally, you will need to input the credentials manually.

# Stream
The name of the messagining API we are utilizing is Stream. [Here](https://getstream.io/chat/) is a link to the website. This branch containing our calls to the Stream API is still a work in progress. Therefore, testing of the messaging features will not work on our deployed version in render. Usage of this feature currenlty only works running this branch locally.

# Backend
The backend is under the "src" directory and uses Spring Boot. It is designed to create API endpoints the frontend can communicate with.
This is mostly going to be storing and retrieving information from the database.

# Frontend
The frontend is under the "frontend" directory and is currently empty. The frontend will be written in HTML, CSS, and Javascript.
It will be responsible for calling the backend API endpoints and displaying the response data to users.
