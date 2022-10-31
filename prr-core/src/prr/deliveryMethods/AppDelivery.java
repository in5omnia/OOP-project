package prr.deliveryMethods;

import prr.notifications.Notification;

public class AppDelivery implements DeliveryMethod {
    public void deliver(Notification notification) {
        //do stuff
        notification.getOrigin().getOwner().receiveNotification(notification);
    }
}
