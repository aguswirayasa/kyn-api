## Installation and Setup

To run the Know Your Neighborhood API project locally, please follow these steps:

1. Import Existing Project into Visual Studio Code or other IDE that supports Java.
2. Create a MySQL database:

    ```
    mysql> create database kny
    ```

3. Setup `application.yml`:

    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/kny
    spring.datasource.username=<YOUR_DB_USERNAME>
    spring.datasource.password=<YOUR_DB_PASSWORD>   
    ```

4. Run the Java application and open http://localhost:8080.
