# Exchange Rest API Service

Service construido para processamento e controle de troco. Esta documentacao, prove
todas as informacoes necessarias para uso da API e tratamentos.

## Funcionamento basico.

Quando um pagador valido enviar um pagamento, o mesmo recebera retorno informando
alternativas de troco caso exista, esta alternativas consideram o menor numero de 
moedas possivel dando prioridade para os maiores valores, o pagador tambem tera a
possibilidade de ganhar um desconto apos 2 pagamentos, chance eh de 17% no intervalo.

Pagador pode cosultar de forma paginada todos os pagamentos realizados.

Para modo interativo utilize Swagger: **/swagger-ui.html**
## Developer
Para usar localmente faca o clone do projeto e utilize o docker para subir banco de dados.

`docker-compose up`

Utilize maven para rodar localmente

`mvn spring-boot:run` ou `./mvnw spring-boot:run`

# REST API

Descricao de uso API

## Lista de pagamentos

### Request

`GET /payer/{payer_id}/payments`

Nao paginado
```shell 
curl --request GET 'http://localhost:8080/payers/1/payments'
```

Paginado
```shell 
curl --request GET 'http://localhost:8080/payers/1/payments?page=0'
```  

### Response

    HTTP/1.1 200 OK
    Content-Type: application/json

    {
        "totalPages": 1,
        "totalElements": 1,
        "payments": [
            {
                "paymentId": 1,
                "productsValue": 10,
                "totalReceived": 11,
                "discountAmount": 1
            }
        ]
    }

## Pagamento

### Request

`POST /payer/{payer_id}/payments`

    curl --location --request POST 'http://localhost:8080/payers/1/payments' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "productsValue": 125,
        "totalReceived": 135
    }'

### Response

    HTTP/1.1 201 Created
    Content-Type: application/json
    Location: http://localhost:8080/payers/1/payments/1/payments

    {
            "paymentId": 1,
            "productsValue": 125,
            "totalReceived": 135,
            "discountAmount": 12,
            "exchange": {
                "exchangeValue": 22,
                "options": [
                    {
                        "coinQuantity": 4,
                        "combination": "[8, 8, 3, 3]"
                    },
                    {
                        "coinQuantity": 5,
                        "combination": "[8, 8, 2, 2, 2]"
                    },
                    {
                        "coinQuantity": 5,
                        "combination": "[8, 5, 5, 2, 2]"
                    }
                ]
            }
        }
        
 ### Pagamento para pagor invalido
 
 Quando um pagamento eh realizado para um pagador inexistente ocorre o seguinte
 retorno.
 
 ### Request
 
 `POST /payer/{payer_id}/payments`
 
     curl --location --request POST 'http://localhost:8080/payers/999/payments' \
     --header 'Content-Type: application/json' \
     --data-raw '{
         "productsValue": 10,
         "totalReceived": 10
     }'
     
### Response

    HTTP/1.1 404 NOT FOUND
    Content-Type: application/json
    
    {
        "timestamp": "2020-06-26T13:57:38.454901",
        "message": "uri=/payers/999/payments",
        "details": [
            "Payer (999) not found"
        ]
    }
    
    
 ### Pagamento valores invalidos
 
Quando um pagamento eh enviado e nao possui valores corretos um erro eh informado.
Um pagmento pode ser considerado invalido quando.
- Valor do produto eh inferior ao pagamento
- Valor excede o limite maximo estabelecido (1000). Este limite foi considerado valido,
devido ao impacto do processamento a API, este limitador garante maior estabilidade
porem impede valores altos para o tratamento.
- Valores obrigatorios nao informados, iguais a zero ou nao positivos. 
 
 ### Request
 
 `POST /payer/{payer_id}/payments`
 
     curl --location --request POST 'http://localhost:8080/payers/999/payments' \
     --header 'Content-Type: application/json' \
     --data-raw '{
         "productsValue": 200,
         "totalReceived": 199
     }'
     
### Response

    HTTP/1.1 422 Unprocessable Entity
    Content-Type: application/json
    
## Valor pago inferior ao valor do produto
    {
        "timestamp": "2020-06-26T14:03:32.952727",
        "message": "uri=/payers/1/payments",
        "details": [
            "Invalid payment, the total received must be higher than products value."
        ]
    }
   
## Limite atingido
     HTTP/1.1 422 Unprocessable Entity
     Content-Type: application/json
     
     {
         "timestamp": "2020-06-26T14:04:55.907861",
         "message": "uri=/payers/1/payments",
         "details": [
             "This exchange value is invalid, this value is too large for simple coins."
         ]
     }
     
## Valor informado igual a zero ou nao positivo.
     HTTP/1.1 422 Unprocessable Entity
     Content-Type: application/json
     
     {
         "timestamp": "2020-06-26T14:08:24.329266",
         "message": "Invalid request values.",
         "details": [
             "Total received must be positive and bigger than zero.",
             "Products value must be positive and bigger than zero."
         ]
     }