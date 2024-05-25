package io.gigabyte.labs.concurrent;

public class SyncExample {

    // Simula la recuperación de datos de una base de datos
    public String fetchDataFromDatabase() {
        System.out.println(Thread.currentThread().getName() + " - Iniciando fetchDataFromDatabase");
        try {
            Thread.sleep(2000); // Simula un retraso de 2 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " - Datos recuperados de la base de datos");
        return "Datos de la base de datos";
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

    public void synchronousMethod() {
        System.out.println(Thread.currentThread().getName() + " - Iniciando synchronousMethod");
        String data = fetchDataFromDatabase(); // Bloquea hasta que los datos son recuperados
        String processedData = processData(data); // Procesa los datos recuperados
        sendDataToClient(processedData); // Envía los datos procesados al cliente
        System.out.println(Thread.currentThread().getName() + " - synchronousMethod completado");
    }

    public static void main(String[] args) {
        SyncExample example = new SyncExample();
        long startTime = System.currentTimeMillis(); // Marca de tiempo inicial
        example.synchronousMethod();
        long endTime = System.currentTimeMillis(); // Marca de tiempo final
        System.out.println(Thread.currentThread().getName() + " - Tiempo total de ejecución (sincrónico): " + (endTime - startTime) + " ms");
    }
}
