# Excel Demo App

This application allows you to download a Excel template file, upload it once completed, extract its content and then insert the data into the database. 
If the file contains any error (invalid header, invalid extension or formatting errors for example), then it will be returned to you.

## Demo Excel file OK
![excel_ok](https://user-images.githubusercontent.com/21682157/200567252-45d68710-b908-4eee-9473-43f2a824d5ab.gif)

## Demo Excel file NOK
![excel_ko](https://user-images.githubusercontent.com/21682157/200567241-66260944-dd56-48a1-83f1-49ed1eb9938d.gif)

## How to test?

First you need to clone this project.

### Client
1. Go to client folder and run `npm install`
2. Run the app in development mode with `npm start`
3. Go to http://localhost:3000/
4. You can use the resources in `demo-resources` to quickly see how it is working

### Server
1. Go to server folder and run `mvn install`
2. Update application.properties to add your MongoDB personal values
3. Start server http://localhost:8080/
5. If you need to, check the swagger http://localhost:8080/swagger-ui/index.html
