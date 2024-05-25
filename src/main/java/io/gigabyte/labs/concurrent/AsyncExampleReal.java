package io.gigabyte.labs.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncExampleReal {

    // Simula la recuperación de datos de una fuente externa
    public CompletableFuture<String> fetchDataFromSourceAsync(int sourceId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " - Iniciando fetchDataFromSourceAsync " + sourceId);
            try {
                Thread.sleep(sourceId * 1000); // Simula un retraso que depende del ID de la fuente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " - Datos recuperados de la fuente " + sourceId);
            return "Datos de la fuente " + sourceId;
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
        CompletableFuture<String> fetchFuture1 = fetchDataFromSourceAsync(1);
        CompletableFuture<String> fetchFuture2 = fetchDataFromSourceAsync(2);
        CompletableFuture<String> fetchFuture3 = fetchDataFromSourceAsync(3);
        CompletableFuture<String> fetchFuture4 = fetchDataFromSourceAsync(4);

        return CompletableFuture.allOf(fetchFuture1, fetchFuture2, fetchFuture3)
          .thenRun(() -> {
              try {
                  String data1 = fetchFuture1.get();
                  String data2 = fetchFuture2.get();
                  String data3 = fetchFuture3.get();
                  String data4 = fetchFuture4.get();

                  String processedData1 = processData(data1);
                  String processedData2 = processData(data2);
                  String processedData3 = processData(data3);
                  String processedData4 = processData(data4);

                  sendDataToClient(processedData1);
                  sendDataToClient(processedData2);
                  sendDataToClient(processedData3);
                  sendDataToClient(processedData4);
              } catch (InterruptedException | ExecutionException e) {
                  e.printStackTrace();
              }
          })
          .thenRun(() -> System.out.println(Thread.currentThread().getName() + " - asynchronousMethod completado"));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncExampleReal example = new AsyncExampleReal();
        long startTime = System.currentTimeMillis(); // Marca de tiempo inicial
        CompletableFuture<Void> future = example.asynchronousMethod();
        future.get(); // Espera a que se completen todas las operaciones asincrónicas
        long endTime = System.currentTimeMillis(); // Marca de tiempo final
        System.out.println(Thread.currentThread().getName() + " - Tiempo total de ejecución (asincrónico): " + (endTime - startTime) + " ms");
    }
}
