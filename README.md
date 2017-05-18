# REST Service
```
./gradlew build
./gradlew bootRun
```

# Admin UI
Based on [ng-admin](https://github.com/marmelab/ng-admin)

## Start
```
cd admin-ui
npm i
npm start
```

It expects the service to be already running on port 8081.

Open [http://localhost:8080](http://localhost:8080) with admin/admin credentials.

The service uses Basic authentication for simplicity, but it does not match well with single page applications. 
In a real application I would use a token based authentication (OAuth and/or JWT), which has not been picked due to a time limit.  

You can use the test channel jar: test-channel/build/libs/test-channel.jar

# User UI

Has not been implemented due to limited time. The logic can be verified with curl:
 
```
curl -X GET \
  'http://localhost:8080/api/price?start=1970-01-01T00%3A00%3A00Z&finish=1970-01-01T23%3A00%3A00Z&duration=PT15M' \
  -H 'authorization: Basic dXNlcjp1c2Vy'
```

User credentials: user/user
