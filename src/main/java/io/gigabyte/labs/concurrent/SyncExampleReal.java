package io.gigabyte.labs.concurrent;

public class SyncExampleReal {

    // Simula la recuperación de datos de una fuente externa
    public String fetchDataFromSource(int sourceId) {
        System.out.println(Thread.currentThread().getName() + " - Iniciando fetchDataFromSource " + sourceId);
        try {
            Thread.sleep(sourceId * 1000); // Simula un retraso que depende del ID de la fuente
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " - Datos recuperados de la fuente " + sourceId);
        return "Datos de la fuente " + sourceId;
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
        for (int i = 1; i <= 4; i++) {
            String data = fetchDataFromSource(i); // Bloquea hasta que los datos son recuperados
            String processedData = processData(data); // Procesa los datos recuperados
            sendDataToClient(processedData); // Envía los datos procesados al cliente
        }
        System.out.println(Thread.currentThread().getName() + " - synchronousMethod completado");
    }

    public static void main(String[] args) {
        SyncExampleReal example = new SyncExampleReal();
        long startTime = System.currentTimeMillis(); // Marca de tiempo inicial
        example.synchronousMethod();
        long endTime = System.currentTimeMillis(); // Marca de tiempo final
        System.out.println(Thread.currentThread().getName() + " - Tiempo total de ejecución (sincrónico): " + (endTime - startTime) + " ms");
    }
}

