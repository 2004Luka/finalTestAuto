#Group Members

Luka Chakhunashvili, Aleksandre Jikia

Luka - UI Tests

- Test Case 1: Register User

- Test Case 2: Login User with correct email and password

- Test Case 3: Login User with incorrect email and password

- Test Case 4: Logout User

- Test Case 5: Register User with existing email

- Test Case 6: Contact Us Form

- Test Case 7: Verify Test Cases Page

- Test Case 8: Verify All Products and product detail page

- Test Case 9: Search Product

- Test Case 12: Add Products in Cart

Aleksandre - API tests

- API 1: Get All Products List

- API 2: POST To All Products List (Negative Case)

- API 3: Get All Brands List

- API 4: PUT To All Brands List (Negative Case)

- API 5: POST To Search Product

- API 6: POST To Search Product without search_product parameter (Negative Case)

- API 7: POST To Verify Login with valid details

- API 8: POST To Verify Login without email parameter (Negative Case)

- API 11: POST To Create/Register User Account

- API 12: DELETE METHOD To Delete User Account

## run tests
  
  All:
  ```mvn test```
  
  UI tests:
  ``` mvn test -Dtest=name of file (mvn test -Dtest=UserRegistrationTest)```

  All API tests:
 ``` mvn -Dtest="*ApiTest" test```

  
  
