package prr.deliveryMethods;

import prr.notifications.Notification;

public interface DeliveryMethod {
    void deliver(Notification notification);
}
