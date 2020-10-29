# sucursal
# Getting Started

### Implementation 

- El proyecto utiliza:

|  URI       | Version       |
|------------|---------------|
|Spring Boot | 2.3.4.RELEASE |
|Postgres    | 13.0          |
|Lombok      | 1.18.12       |
|KD Tree     | 1.0.0         |

- Utilizo una implementacion del tipo abstracto de datos kd tree, para la busqueda del nodo mas cercado
ya que dicha estructura de datos obtiene en orden n.log(n) dicha funcionalidad.
Tambien se puede implementar mediante utilidades geoespaciales a nivel de base de datos para mas facilidad.

- No implemente a mano el tipo abstracto de datos kd-tree para la busqueda del nodo mas cercano u otra abstraccion para 
para tal caso, porque me parece mas pertinente la solcucion a nivel de orden de ejecucion y la implementacion que utilizo cumple 
con dicha funcionalidad.
  
- Se implementa el dominio mediante herencia en una sola tabla. Tambien se podria haber implementado
una sola entidad y discriminar por un enum que diferencie el tipo.

- Se pueden implementar mejoras como:
    - El uso del kd-tree como cache primario, cargandose al principio de cada ejcucion
    y actualizandose en las llamadas al crud, ya que el universo de nodos no seria muy grande
    y reduciendo de esa forma los tiempos de respuesta para el nodo mas cercado.
    - Implementar un rate limiter a modo de throtling.
    - Implementar un circuit braker para los casos extremos como lentitud en la base, etc.
---
### Compile & Test:
- El proyecto se compila con el siguiente comando:
```
./mvnw compile
```
- Para ejecutar los casos de test:
```
./mvnw test
```
- Para ejecutar desde maven (previamente se tiene que tener ejecutando la base de datos).
```
./mvnw spring-boot:run
```
---
### Compile & Run (Dockerfile):
- Para generar la imagen de docker:
```
docker build . -t demo/sucursal
```
- Para ejecutar la instancia de docker (previamente se tiene que tener ejecutando la base de datos).
```
docker run -p 8080:8080 demo/sucursal
```
---
### Compile & Run (docker-compose):
- Para generar el build de docker-compose:
```
docker-compose build
```
- Para ejecutar la solucion (levanta la base y el servicio).
```
docker-compose up
```
----
### Swagger (OAS3)
La url de la documentacion via swagger es:
```
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
```
### EP Documentation
La documentacion de los EP es la siguiente:

- **HealthCheck**
    - `GET` /actuator/health 
    
- **Node Controller**
    - `GET` /api/node/{id}
    
        *Ejemplo*: 
        ```shell script
        curl -X GET "http://localhost:8080/api/node/8" -H  "accept: */*"
        ```
        *Response*:
        ```json5
        Error Code: 404
        ```
        o
        ```json5
        Success Code: 200
        Body:
        {
          "id": 8,
          "lat": -34.649456,
          "lng": -58.5195477,
          "address": "Av. Rivadavia 11626, C1408 CABA",
          "customerServiceHour": {
            "startService": "09:00:00",
            "endService": "18:00:00"
          }
        }
        ```
        
    - `DELETE` /api/node/{id}
        *Ejemplo*: 
        ```shell script
        curl -X DELETE "http://localhost:8080/api/node/8" -H  "accept: */*"
        ```
        *Response*:
        ```json5
        Code: 204 
        ```
      
    - `POST` /api/node/branchOffice 
    
        *Ejemplo*: 
        ```shell script
        curl -X POST "http://localhost:8080/api/node/branchOffice" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"lat\":-34.6883711,\"lng\":-58.5022494,\"address\":\"Av. Saenz 997, C1437 CABA\",\"customerServiceHour\":{\"startService\":\"09:00:00\",\"endService\":\"18:00:00\"}}"
        ```
        *Response*:
        ```json5
        Error Code: 500
        ```
        o
        ```json5
        Success Code: 201
        Body:
        {
          "id": 9,
          "lat": -34.6883711,
          "lng": -58.5022494,
          "address": "Av. Saenz 997, C1437 CABA",
          "customerServiceHour": {
            "startService": "09:00:00",
            "endService": "18:00:00"
          }
        } 
        ```
      
    - `POST` /api/node/withdrawalPoint
    
        *Ejemplo*: 
        ```shell script
        curl -X POST "http://localhost:8080/api/node/withdrawalPoint" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"lat\":-34.6439358,\"lng\":-58.4398426,\"capacity\":200}"
        ```
        *Response*:
        ```json5
        Error Code: 500
        ```
        o
        ```json5
        Success Code: 201
        Body:
        {
          "id": 10,
          "lat": -34.6439358,
          "lng": -58.4398426,
          "capacity": 200
        }
        ```
    
    - PUT /api/nodo/branchOffice/{id}
        *Response*:
        ```json5
        Error Code: 404
        ```
        o
        ```json5
        Success Code: 200
        ```
    
    - PUT /api/nodo/withdrawalPoint/{id}
        *Response*:
        ```json5
        Error Code: 404
        ```
        o
        ```json5
        Success Code: 200
        ```
    
    - GET /api/nearestNode?lat=&lng=
    
        *Ejemplo*: 
        ```shell script
        curl -X GET "http://localhost:8080/api/nearestNode?lat=-34.6883211&lng=-58.5322454" -H  "accept: */*"
        ```
        *Response*:
        ```json5
        Error Code: 404
        ``` 
        o
        ```json5
        Success Code: 200
        Body:
        {
            "id": 9,
            "lat": -34.6883711,
            "lng": -58.5022494,
            "address": "Av. Sáenz 997, C1437 CABA",
            "customerServiceHour": {
              "startService": "09:00:00",
              "endService": "18:00:00"
            }
        }
        ```
    

### Reference Documentation
For further reference, please consider the following sections:
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)

