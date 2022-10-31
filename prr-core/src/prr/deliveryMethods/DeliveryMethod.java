package prr.deliveryMethods;

import prr.notifications.Notification;

import java.io.Serializable;

public interface DeliveryMethod extends Serializable {

    void deliver(Notification notification);
}
