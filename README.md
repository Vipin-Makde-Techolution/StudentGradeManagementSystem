"# SpringBootAssignment" 

swagger url : http://localhost:8080/api/swagger-ui.html

To login using POST call :  "http://localhost:8080/api/user/authenticate" endpoint

Post Body : 
{
    "username" : "test1",
    "password" : "test1"
}

After that copy the value of "jwtToken" from response and authorize in swagger using below format and start using apis

Bearer "jwtToken"