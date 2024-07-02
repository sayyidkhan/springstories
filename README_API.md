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

1. **Add book:** `book/user/add` `[POST]`
    - Authentication:
        - `username`: `regular_user`
        - `password`: `123`
        - `role`: `USER`
        - only require to pass username and password using Basic Auth
   - you may take any of data from the `dummy_data/book/*`
   - recommended to add all of them (for the testing of later API's)
   - app will prevent adding the same object again
   
   **Expected Success output: Book Created Successfully**<br> 
   **Expected Failed output: Book already exists in the system !**<br>
   <br>
2. **Update Book:** `book/user/update` `[PUT]`
    - Authentication:
        - `username`: `regular_user`
        - `password`: `123`
        - `role`: `USER`
        - only require to pass username and password using Basic Auth
   - you may take any of data from the `dummy_data/book/*` to perform the update
   - all of the data can be updated, including the number of authors except IBSN
   - it is recommended to add record using the add API prior to testing the UPDATE API

   **Expected Success output: Book with ISBN 9780141439594 Updated Successfully**<br>
   **Expected Failed output: Failed to update book: !**<br>
   <br>
3. **Find Book:** `localhost:9000/books/user/find?title=Dracula&author=MARY SHELLEY,BRAM STOKER` `[GET]`
    - Authentication:
        - `username`: `regular_user`
        - `password`: `123`
        - `role`: `USER`
        - only require to pass username and password using Basic Auth
    - using `query parameters` to filter through the records. must use either 1 at least or both can be used.
    - search is case in-sensitive but still requires exact match
    - `title` : put the title of the book. eg, Dracula
    - `author`: put the name of the author, can put more than one author delimited by commas. adding more than 1 author is a AND operation.
   - it is recommended to add record using the add API prior to testing the find API

   **Expected Success output: [Object will returned in a list] **<br>
   **Expected Failed output: [] **<br>
   <br>
4. **Delete Book:** `book/user/delete` `[DELETE]`
    - Authentication:
        - `username`: `admin_user`
        - `password`: `123`
        - `role`: `ADMIN`
        - only require to pass username and password using Basic Auth
   - using `query parameters` to filter through the records.
   - `isbn` : the isbn of the book
   - it is recommended to add record using the add API prior to testing the DELETE API
   
   > Note: only users with ADMIN in role, can delete records

   **Expected Success output: Book with ISBN 9780486282114 deleted successfully**<br>
   **Expected Failed output: Failed to update book: !**<br>
   <br>