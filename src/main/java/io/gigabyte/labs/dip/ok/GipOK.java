package io.gigabyte.labs.dip.ok;


import io.gigabyte.labs.dip.Order;

interface NotificationService {
    void sendNotification(String to, String message);
}

class EmailNotification implements NotificationService {
    public void sendNotification(String to, String message) {
        // Envío de correo electrónico
    }
}

class OrderProcessor {
    private NotificationService notifier;

    public OrderProcessor(NotificationService notifier) {
        this.notifier = notifier;
    }

    public void processOrder(Order order) {
        // Lógica para procesar el pedido
        notifier.sendNotification(order.getUserEmail(), "Your order has been processed");
    }
}


public class GipOK {
}
