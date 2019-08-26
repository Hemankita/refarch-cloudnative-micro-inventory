## Run MySQL

```
$ oc new-app -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=password -e MYSQL_DATABASE=inventorydb mysql:5.7 -n cloudnative
```

## Run the App

```
$ oc create -f inventory_data.yaml -n cloudnative

$ oc create -f load_data.yaml -n cloudnative

$ oc create -f deployment.yaml -n cloudnative

$ oc create -f service.yaml -n cloudnative

$ oc expose svc micro-inventory-service -n cloudnative
```
