package io.gigabyte.labs.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncExample {

    // Simula la recuperación de datos de una base de datos
    public CompletableFuture<String> fetchDataFromDatabaseAsync() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " - Iniciando fetchDataFromDatabaseAsync");
            try {
                Thread.sleep(2000); // Simula un retraso de 2 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " - Datos recuperados de la base de datos");
            return "Datos de la base de datos";
        });
    }

    // Simula el procesamiento de datos
    public String processData(String data) {
        System.out.println(Thread.currentThread().getName() + " - Iniciando processData");
        return data + " - procesados";
    }

    // Simula el envío de datos al cliente
    public void sendDataToClient(String data) {
        System.out.println(Thread.currentThread().getName() + " - Enviando datos al cliente: " + data);
    }

    public CompletableFuture<Void> asynchronousMethod() {
        System.out.println(Thread.currentThread().getName() + " - Iniciando asynchronousMethod");
        return fetchDataFromDatabaseAsync()
          .thenApply(data -> {
              System.out.println(Thread.currentThread().getName() + " - Procesando datos");
              String processedData = processData(data);
              return processedData;
          })
          .thenAccept(processedData -> {
              System.out.println(Thread.currentThread().getName() + " - Enviando datos procesados");
              sendDataToClient(processedData);
          })
          .thenRun(() -> System.out.println(Thread.currentThread().getName() + " - asynchronousMethod completado"));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncExample example = new AsyncExample();
        long startTime = System.currentTimeMillis(); // Marca de tiempo inicial
        CompletableFuture<Void> future = example.asynchronousMethod();
        future.get(); // Espera a que se completen todas las operaciones asincrónicas
        long endTime = System.currentTimeMillis(); // Marca de tiempo final
        System.out.println(Thread.currentThread().getName() + " - Tiempo total de ejecución (asincrónico): " + (endTime - startTime) + " ms");
    }
}
