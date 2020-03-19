# spring-custom-oauth-response-payload
merupakan project untuk custom response payload (token maupun body) dari oauth spring security


## Prerequisites

> JDK 1.8 Or Later <br>
> MySQL <br>
> Editor or IDE (Eclipse, IntelliJ, etc)

## Result
> 1. default response payload : <br>
>    if you convert acess_token using jwt.io online converter, then the result : 
```    
        {
           "aud": [ 
              "mw/adminapp" 
            ], 
            "user_name": "user1", 
            "scope": [ 
                "read", 
                "write" 
             ], 
             "exp": 1584617246, 
             "authorities": [ 
                "ADMIN" 
             ], 
             "jti": "40db4f99-01ac-45d7-99b2-e77610b06b6a",
             "client_id": "adminapp"
        }
```

> 2. Custom (add payload inside access_token): <br>
>   For this part you only need to change the method GrantedAuthority method that you already override in your User domain. <br>
>   if you convert acess_token using jwt.io online converter, then the result : 
```
        {
           "aud": [ 
              "mw/adminapp" 
            ], 
            "user_name": "user1", 
            "scope": [ 
                "read", 
                "write" 
             ], 
             "exp": 1584617246, 
             "authorities": [ 
                "ADMINISTRATOR",
                "SUPERVISOR"
             ], 
             "jti": "40db4f99-01ac-45d7-99b2-e77610b06b6a",
             "client_id": "adminapp"
        }
```
> 3. Custom (add new field):
>   for this part you need to modify your web security Configuration
>   below new payload of your oauth/token api (there is a "custom_payload" field added):
```
        {
           "aud": [ 
              "mw/adminapp" 
            ], 
            "user_name": "user1", 
            "scope": [ 
                "read", 
                "write" 
             ], 
             "exp": 1584617246, 
             "authorities": [ 
                "ADMINISTRATOR",
                "SUPERVISOR"
             ], 
             "jti": "40db4f99-01ac-45d7-99b2-e77610b06b6a",
             "client_id": "adminapp",
             "custom_payload": {
                "username": "user1",
                "email": "user1@mailinator.com",
                "list_role": [
                    {
                        "role_id": 1,
                        "role_name": "ADMINISTRATOR",
                        "role_description": "ADMINISTRATOR"
                     },
                     {
                        "role_id": 3,
                        "role_name": "SUPERVISOR",
                        "role_description": "SUPERVISOR"
                      }
                ]
            }
        }
```
