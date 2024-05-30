Entiendo, eliminaremos la autenticación y autorización con Amazon Cognito y mantendremos la complejidad añadida con el almacenamiento de archivos adjuntos en S3 y las notificaciones con SNS. Aquí tienes la versión ajustada de la guía:

### Objetivo

Crear una API RESTful para manejar operaciones CRUD de una aplicación de notas, con almacenamiento de archivos adjuntos en S3 y notificaciones con SNS.

### Paso a Paso

#### 1. Crear una Tabla en DynamoDB

1. **Accede a la consola de AWS** y abre **DynamoDB**.
2. **Crea una tabla**:
    - **Nombre de la tabla**: `Notes`
    - **Clave de partición**: `id` (Tipo: String)
    - **Configura la capacidad**: Usa los valores por defecto.
3. **Crear la tabla**.

#### 2. Crear Funciones Lambda

1. **Accede a la consola de AWS** y abre **Lambda**.
2. **Crear funciones** para cada operación (Create, Read, Update, Delete) con el código ajustado para incluir almacenamiento en S3 y notificaciones con SNS.

**Código de las funciones en `app.py`**:

```python
import json
import boto3
import os
from botocore.exceptions import ClientError

dynamodb = boto3.resource('dynamodb')
s3 = boto3.client('s3')
sns = boto3.client('sns')

table = dynamodb.Table(os.environ['TABLE_NAME'])
bucket_name = os.environ['BUCKET_NAME']
sns_topic_arn = os.environ['SNS_TOPIC_ARN']

def create_note(event, context):
    body = json.loads(event['body'])
    note_id = body['id']
    content = body['content']
    attachment = body.get('attachment')  # URL del archivo adjunto

    try:
        table.put_item(Item={'id': note_id, 'content': content, 'attachment': attachment})
        response = {
            "statusCode": 200,
            "body": json.dumps({"message": "Note created successfully!"})
        }
        
        # Publicar un mensaje SNS
        sns.publish(
            TopicArn=sns_topic_arn,
            Message=f'Note {note_id} created',
            Subject='New Note Created'
        )
    except ClientError as e:
        response = {
            "statusCode": 500,
            "body": json.dumps({"message": str(e)})
        }

    return response

def get_note(event, context):
    note_id = event['pathParameters']['id']

    try:
        result = table.get_item(Key={'id': note_id})
        note = result.get('Item')
        if note:
            response = {
                "statusCode": 200,
                "body": json.dumps(note)
            }
        else:
            response = {
                "statusCode": 404,
                "body": json.dumps({"message": "Note not found"})
            }
    except ClientError as e:
        response = {
            "statusCode": 500,
            "body": json.dumps({"message": str(e)})
        }

    return response

def update_note(event, context):
    note_id = event['pathParameters']['id']
    body = json.loads(event['body'])
    content = body['content']
    attachment = body.get('attachment')  # URL del archivo adjunto

    try:
        update_expression = "set content=:c"
        expression_attribute_values = {':c': content}
        
        if attachment:
            update_expression += ", attachment=:a"
            expression_attribute_values[':a'] = attachment
        
        table.update_item(
            Key={'id': note_id},
            UpdateExpression=update_expression,
            ExpressionAttributeValues=expression_attribute_values,
            ReturnValues="UPDATED_NEW"
        )
        response = {
            "statusCode": 200,
            "body": json.dumps({"message": "Note updated successfully!"})
        }
    except ClientError as e:
        response = {
            "statusCode": 500,
            "body": json.dumps({"message": str(e)})
        }

    return response

def delete_note(event, context):
    note_id = event['pathParameters']['id']

    try:
        table.delete_item(Key={'id': note_id})
        response = {
            "statusCode": 200,
            "body": json.dumps({"message": "Note deleted successfully!"})
        }
    except ClientError as e:
        response = {
            "statusCode": 500,
            "body": json.dumps({"message": str(e)})
        }

    return response

def upload_attachment(event, context):
    body = json.loads(event['body'])
    file_content = body['file_content']
    file_name = body['file_name']

    try:
        s3.put_object(Bucket=bucket_name, Key=file_name, Body=file_content)
        attachment_url = f"https://{bucket_name}.s3.amazonaws.com/{file_name}"
        
        response = {
            "statusCode": 200,
            "body": json.dumps({"attachment_url": attachment_url})
        }
    except ClientError as e:
        response = {
            "statusCode": 500,
            "body": json.dumps({"message": str(e)})
        }

    return response
```

3. **Configura las variables de entorno** para cada función Lambda:
    - `TABLE_NAME`: `Notes`
    - `BUCKET_NAME`: Nombre del bucket S3.
    - `SNS_TOPIC_ARN`: ARN del tópico SNS.

#### 3. Configurar API Gateway

1. **Accede a la consola de AWS** y abre **API Gateway**.
2. **Crear una API REST**:
    - **Nombre de la API**: `NotesAPI`
    - **Tipo de endpoint**: Regional

3. **Crear métodos** para cada operación:
    - **POST /notes**:
        - **Integración**: Lambda Function
        - **Función Lambda**: `CreateNoteFunction`
    - **GET /notes/{id}**:
        - **Integración**: Lambda Function
        - **Función Lambda**: `GetNoteFunction`
    - **PUT /notes/{id}**:
        - **Integración**: Lambda Function
        - **Función Lambda**: `UpdateNoteFunction`
    - **DELETE /notes/{id}**:
        - **Integración**: Lambda Function
        - **Función Lambda**: `DeleteNoteFunction`
    - **POST /upload**:
        - **Integración**: Lambda Function
        - **Función Lambda**: `UploadAttachmentFunction`

4. **Deploy API**:
    - **Crear un despliegue** en una nueva etapa (por ejemplo, `prod`).
    - **Anota el URL de la API**.

#### 4. Configurar Amazon SNS

1. **Accede a la consola de AWS** y abre **SNS**.
2. **Crear un nuevo tópico**:
    - **Nombre del tópico**: `NotesTopic`
3. **Anotar el ARN del tópico**.
4. **Suscribirse al tópico**:
    - **Protocolo**: Email
    - **Endpoint**: Tu dirección de correo electrónico.

#### 5. Probar la API

Utiliza herramientas como Postman o curl para interactuar con los endpoints expuestos por API Gateway.

- **Crear una nota con archivo adjunto**:
  ```sh
  curl -X POST https://<api-id>.execute-api.<region>.amazonaws.com/prod/notes \
  -H 'Content-Type: application/json' \
  -d '{"id": "1", "content": "This is a sample note.", "attachment": "https://<bucket-name>.s3.amazonaws.com/<file_name>"}'
  ```

- **Subir un archivo adjunto**:
  ```sh
  curl -X POST https://<api-id>.execute-api.<region>.amazonaws.com/prod/upload \
  -H 'Content-Type: application/json' \
  -d '{"file_content": "base64_encoded_content", "file_name": "example.txt"}'
  ```

- **Obtener una nota**:
  ```sh
  curl https://<api-id>.execute-api.<region>.amazonaws.com/prod/notes/1
  ```

- **Actualizar una nota**:
  ```sh
  curl -X PUT https://<api-id>.execute-api.<region>.amazonaws.com/prod/notes/1 \
  -H 'Content-Type: application/json' \
  -d '{"content": "Updated content", "attachment": "https://<bucket-name>.s3.amazonaws.com/<new_file_name>"}'
  ```

- **Eliminar una nota**:
  ```sh
  curl -X DELETE https://<api-id>.execute-api.<region>.amazonaws.com/prod/notes/1
  ```

### Conclusión

Esta versión mejorada de la demo incluye almacenamiento de archivos adjuntos en S3 y manejo de notificaciones mediante SNS, sin incluir autenticación y autorización. Estas características adicionales muestran cómo utilizar varios servicios de AWS para construir una aplicación sin servidor más completa y con funcionalidades avanzadas.