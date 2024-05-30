package io.gigabyte.labs.dip;

class OrderProcessor {
    private EmailNotification emailNotifier = new EmailNotification();

    public void processOrder(Order order) {
        // Lógica para procesar el pedido
        emailNotifier.sendEmail(order.getUserEmail(), "Your order has been processed");
    }
}

class EmailNotification {
    public void sendEmail(String email, String message) {
        // Envío de correo electrónico
    }
}
public class DIPViolation {
}