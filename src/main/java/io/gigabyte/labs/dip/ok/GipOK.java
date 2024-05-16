package io.gigabyte.labs.dip.ok;


import io.gigabyte.labs.TipoNotificacion;
import io.gigabyte.labs.dip.Order;

interface NotificationService {
    void sendNotification(String to, String message);
}

class EmailNotification implements NotificationService {
    public void sendNotification(String to, String message) {
        // Envío de correo electrónico
    }
}

class SMSNotification implements NotificationService {
    @Override
    public void sendNotification(String to, String message) {

    }
}

class PushNotification implements NotificationService {

    @Override
    public void sendNotification(String to, String message) {

    }
}

class OrderProcessor {

    public void processOrder(Order order) {
        // Lógica para procesar el pedido
        NotificationService notifier = getNotificationService(order.getTipoNotificacion());
        notifier.sendNotification(order.getUserEmail(), "Your order has been processed");
    }

    private NotificationService getNotificationService(TipoNotificacion tipoNotificacion) {
        if (tipoNotificacion == TipoNotificacion.SMS) {
            return new SMSNotification();
        } else if (tipoNotificacion == TipoNotificacion.EMAIL) {
            return new EmailNotification();
        } else if (tipoNotificacion == TipoNotificacion.PUSH) {
            return new PushNotification();
        } else {
            return new EmailNotification();
        }
    }
}


public class GipOK {
}
