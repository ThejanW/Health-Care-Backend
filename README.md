# Health-Care-Backend

#Overview
Health Care is an Epidemic data collection system for the Epidemiology department of
Ministry of Health. The System consists of a mobile application built for android and a backend built using spring framework.
This is the backend of the application. This application can be used to collect
epidemiological data across the country. The application will include an embedded
database that could collect data even when the user is not connected to the internet and
capable of uploading data gathered during offline operations when connected.
In addition, the app will also consist of suitable user roles based on the ground
conditions. For instance, the validation procedures and constraints will be different for
medical officers, health officers, and patients.

#Frameworks Used
The web backend(stored in this repository) has been built with Spring framework, which is among the widely used frameworks in the web development. 
The application development has been carried out with minimal use of spring components and using custom components for different services required for the web application. For instance, the authentication layers has been rebuilt with the essential components. 
Unlike the previous versions, the framework release used for the development of the application used pure java in the form of annotations rather than xml based configurations. 

#Database Design
![alt tag](https://raw.githubusercontent.com/ThejanW/Health-Care-Backend/master/DB_Schema.png)

#Backend Software Design
![alt tag](https://raw.githubusercontent.com/ThejanW/Health-Care-Backend/master/Software Design.png)

#Data Access
The application has been designed in a way such that the application layer runs independently from the database layer, i.e.
the application has the capability of supporting other 
database systems such as Oracle, Postgre or even mongoDB. For the application to cope up with such modifications, 
the drivers has to be written at the database layer adhering to the method signatures set by the database adapter interface.

#Security
The security of the back end web application has been managed by the use of jwt token based authentication with a non-expiring token being issued to the authenticated user. However the token issue can be extended easily with the use of expiring tokens being 
issued for a limited time along with refresh tokens. Basic validation for the requests have been implemented in the controllers and is widely available in the controller package of the web application. In addition, each method can specify an access level for the validations to take place for the request and perform actions.
All incoming requests to the web application are intercepted by an authenticator in order to perform the authentication. All the requests paths except for request paths designated as guest routes.

