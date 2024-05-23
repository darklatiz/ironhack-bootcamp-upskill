# ENTRYPOINT VS CMD

## Resumen de ENTRYPOINT vs CMD

1. **ENTRYPOINT**:
    - **Propósito**: Especifica el comando que siempre se ejecutará cuando se inicie el contenedor.
    - **Uso**: Se utiliza para definir el ejecutable principal del contenedor.
    - **Inmutable**: No puede ser fácilmente sobrescrito por los argumentos del comando `docker run` (a menos que se use `--entrypoint`).

2. **CMD**:
    - **Propósito**: Proporciona los argumentos por defecto para el comando `ENTRYPOINT` o define el comando por defecto que se ejecutará si no se establece `ENTRYPOINT`.
    - **Mutable**: Puede ser sobrescrito fácilmente por los argumentos del comando `docker run`.

### Buenas Prácticas

Cuando escribas un Dockerfile, es una buena práctica establecer tanto `ENTRYPOINT` como `CMD`. Sigue estas reglas para decidir qué valores asignar:

1. **ENTRYPOINT** debe ser la ruta al proceso que se ejecutará dentro del contenedor.
2. **CMD** debe ser los argumentos por defecto que se pasarán a ese comando (si los hay).

### Ejemplo de Uso Correcto

```dockerfile
FROM ubuntu:latest

# Definir el ENTRYPOINT al ejecutable principal
ENTRYPOINT ["/usr/bin/myapp"]

# Proporcionar argumentos por defecto para el ENTRYPOINT
CMD ["--default-arg1", "--default-arg2"]
```

### Error Común

Un error común es configurar incorrectamente `CMD` en lugar de `ENTRYPOINT`. Aunque tu imagen usualmente seguirá funcionando porque Docker usa por defecto `/bin/sh -c` como `ENTRYPOINT`, los usuarios finales no podrán pasar directamente argumentos a tu binario usando `docker run`. En su lugar, necesitarán pasar la ruta completa al binario, ya que el proceso `ENTRYPOINT` del contenedor será `/bin/sh -c` en lugar de tu propia aplicación.

### Significado de `/bin/sh -c`

- **`/bin/sh`**: Este es el intérprete de comandos predeterminado en muchos sistemas Unix y Linux. Es una shell que se utiliza para ejecutar comandos.
- **`-c`**: Este es un argumento que se pasa a la shell (`sh`). Indica que el siguiente argumento debe ser tratado como un comando que la shell ejecutará.

### Ejemplos - demo

- Creamos Dockerfile 
```dockerfile
FROM alpine:latest
ENTRYPOINT ["ls"]
```

- Creamos Imagen
```shell
docker build -t entrypoint-demo:latest .
```

- Creamos y corremos el contenedor
```shell
docker run entrypoint-demo:latest
```

La instrucción `ENTRYPOINT` significa que Docker ejecuta el comando `ls` cuando se inicia el contenedor. Como no se ha configurado ningún `CMD`, el comando se llama sin argumentos.

Podemos pasar argumentos directamente al comando añadiéndolos a tu declaración `docker run`:
```dockerfile
docker run entrypoint-demo:latest -alh
```

Todo lo que viene después del nombre de la imagen se pasa al `ENTRYPOINT` como sus argumentos, lo que resulta en que `ls -alh` se ejecute en el contenedor. No tuviste que escribir `ls` como parte de tu comando `docker run`, demostrando que `ENTRYPOINT` significa que los usuarios no necesitan saber cuál es el binario del contenedor ni dónde está almacenado.

### Ejemplo 2 - Usando Docker CMD
Cambiemos el Dockerfile con el siguiente contenido

```dockerfile
FROM alpine:latest
CMD ["ls"]
```

Construimos la imagen
```shell
docker build -t cmd-demo:latest .
```

Ejecutamos el contenedor
```shell
docker run cmd-demo:latest
```

La salida es la misma que antes. Debido a que no se establece un `ENTRYPOINT`, el `CMD` de `ls` se agrega al `ENTRYPOINT` predeterminado, lo que resulta en que se ejecute `/bin/sh -c "ls"`—el comando `ls` todavía se ejecuta, pero como un subproceso de la shell.

Ahora observa qué sucede si intentas pasar argumentos al comando `ls` a través de `docker run`:

```sh
docker run cmd-demo:latest -alh
```

Esto no funciona porque `ls` no es el `ENTRYPOINT`. Los argumentos `-alh` se pasan como argumentos al `ENTRYPOINT` predeterminado `/bin/sh -c`, lo que resulta en que la shell del contenedor intente evaluar `-alh` como un comando.

### Ejemplo usando `ENTRYPOINT` y `CMD`

Actualicemos Dockerfile
```dockerfile
FROM alpine:latest
ENTRYPOINT ["ls"]
CMD ["-alh"]
```

Construyamos y ejecutemos la imagen
```shell
docker build -t entrypoint-cmd-demo:latest .
```

```shell
docker run entrypoint-cmd-demo:latest
```

Esta vez, el comando `ls` (ENTRYPOINT) se llama automáticamente con el argumento `-alh` (CMD).

Aún puedes proporcionar argumentos personalizados sobrescribiendo el `CMD` con `docker run`:

```shell
docker run entrypoint-cmd-demo:latest -p --full-time
```
Esta vez, el comando `ls` (ENTRYPOINT) se llama automáticamente pero los argumentos definidos en CMD son sobreescritos por los argumentos declarados en el comando `docker run` dando como resultado `ls -p --full-time`

## DEMO CI / CD

```shell
ssh -i ~/.ssh/id_rsa.pem azureuser@52.138.102.222
```

[Jenkins Console](http://52.138.102.222:8080)
user: ironhak

## Pull Image created
```shell
docker login laboratorios2gigabyte.azurecr.io
```
user: laboratorios2gigabyte

Obtenemos imagen del repositorio
```shell
docker pull laboratorios2gigabyte.azurecr.io/node-express-app:5
```

Ejecutamos contenedor
```shell
docker run -d -p 3000:3333 laboratorios2gigabyte.azurecr.io/node-express-app:5
```

