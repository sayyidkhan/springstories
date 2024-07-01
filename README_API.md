## API Readme

## Prior to testing API, do load user data first.

1. open an app like POSTMAN so that we load the user data into the database
2. navigate to the `dummy_data/user/user.json` and copy the entire object in the file
3. in POSTMAN, set as POST request: `localhost:9000/auth/multi_register`
   - 3a. no authentication is required to be set since we are trying to populate data in user tables
   - 3b. select body to be of JSON data, and paste the entire object copied earlier
   - 3c. send the post request and now you can check the data in the database
4. you can use a database viewer (DBVisualiser) of your choice to see the updated data
> Note: You may run this once. running it again will not result in
> duplicate data as there will be validation checks for duplicate entry.


## Available API Endpoint(s)

- `book/user/add` `POST`
- `book/user/update` `PUT`
- `book/user/find` `GET`
- `book/admin/delete` `DELETE`

### Usage

1. `book/user/add` `[POST]`
   - Authentication:
     - username: regular_user
     - password: 123
     - role: user
   - you may take any of data from the `dummy_data/book/*`
   - recommended to add all of them (for the testing of later API's)
   - app will prevent adding the same object again
   
   **Expected Success output: Book Created Successfully**<br> 
   **Expected Failed output: Book already exists in the system !**<br>
2. 

