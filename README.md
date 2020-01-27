CRUD Service
============

Got: all frontend part of app and "config" package.

Task: developed a server part of app.

# Curl commands:
##### Get by id:

`curl --request GET 'http://localhost:8080/cosmoport/rest/ships/{id}' | json_pp`

##### Get by pageNumber, pageSize(1, 3, 5, 10, 20), order(ID, SPEED, DATE, RATING):

`curl --request GET 'http://localhost:8080/cosmoport/rest/ships?pageNumber={pageNumber}&pageSize={pageSize}&order={order}'`

##### Get by name, planet, between (prod date, crew size, max speed, rating), ship type, user(true, false):
`curl --request GET 'http://localhost:8080/cosmoport/rest/ships?pageNumber={pageNumber}&pageSize={pageSize}&name={name}&planet={planet}&shipType={type}&after={after}&before={before}&isUsed={used}&minSpeed={speed}&maxSpeed={speed}&minCrewSize={size}&maxCrewSize={size}&minRating={rating}&maxRating={rating}&order={order}'`

##### Create:
`curl --header "Content-Type: application/json" --request POST --data '{"name":"name", "planet":"planet", "shipType":"TRANSPORT", "prodDate":"2020-01-07", "isUsed":"true", "speed":"0.32", "crewSize":"123"}' http://localhost:8080/cosmoport/rest/ships/ `

##### Update by id:
`curl --header "Content-Type: application/json" --request POST --data '{"name":"name", "planet":"planet", "shipType":"TRANSPORT", "prodDate":"2020-01-07", "isUsed":"true", "speed":"0.32", "crewSize":"12"}' http://localhost:8080/cosmoport/rest/ships/{id}`

##### Delete by id:
`curl --request DELETE 'http://localhost:8080/cosmoport/rest/ships/{id}'`

# Stack:
* Spring Boot
* Spring Data JPA (Hibernate ORM)
* MySQL
* Maven
