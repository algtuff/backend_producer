# Sobre a API

Esta API foi construida de acordo com as especificações do teste.

Os dados são armazenados em memória, carregados a partir do arquivo "movielist.csv" 
localizado na pasta src/main/resources. O arquivo é carregado na inicialização da aplicação e 
contém uma lista de filmes indicados ao Golden Raspberry Awards.

A API foi construída utilizando o framework Spring Boot.

## Repositório da API

A API está localizada no repositório:

    https://github.com/algtuff/backend_producer

## Para rodar a API, basta clonar o repositório e executar o comando abaixo:

    mvn spring-boot:run

## Para rodar os testes, execute o comando abaixo:

    mvn test

# REST API

A API disponibiliza um endpoint para obter a lista de produtores com maior intervalo entre dois prêmios consecutivos, e o que
obteve dois prêmios mais rápido.

## Obtendo a lista de produtores com maior intervalo entre dois prêmios consecutivos e os que obtiveram dois prêmios mais rápido

### Request

`GET /producers/winner/min-max-interval`

    curl -i -H 'Accept: application/json' http://localhost:8080/producers/winner/min-max-interval

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sat, 14 Sep 2024 20:34:32 GMT
    
    {"min":[{"producer":"Joel Silver","interval":1,"previousWin":1990,"followingWin":1991}],"max":[{"producer":"Matthew Vaughn","interval":13,"previousWin":2002,"followingWin":2015}]}

## Obtendo a lista de produtores cadastrados

### Request

`GET /producers`

    curl -i -H 'Accept: application/json' http://localhost:8080/producers

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sat, 14 Sep 2024 20:37:07 GMT
    
    [{"id":1,"name":"Allan Carr"},{"id":2,"name":"Jerry Weintraub"},{...}]

## Obtendo um produtor específico

### Request

`GET /producers/{id}`

    curl -i -H 'Accept: application/json' http://localhost:8080/producers/1

### Response

    HTTP/1.1 200 
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sat, 14 Sep 2024 20:38:56 GMT
    
    {"id":1,"name":"Allan Carr"}

## Obtendo a lista de filmes cadastrados

### Request

`GET /movies`

    curl -i -H 'Accept: application/json' http://localhost:8080/movies

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sat, 14 Sep 2024 20:40:13 GMT
    
    [{"movieId":1,"title":"Can't Stop the Music","movieYear":1980,"studio":"Associated Film Distribution","winner":true,"producers":[{"id":1,"name":"Allan Carr"}]},{...}]

## Obtendo um filme específico

### Request

`GET /movies/{id}`

    curl -i -H 'Accept: application/json' http://localhost:8080/movies/1

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sat, 14 Sep 2024 20:41:17 GMT
    
    {"movieId":1,"title":"Can't Stop the Music","movieYear":1980,"studio":"Associated Film Distribution","winner":true,"producers":[{"id":1,"name":"Allan Carr"}]}
