package prr.deliveryMethods;

import prr.notifications.Notification;

public class AppDelivery implements DeliveryMethod {
    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;
    public void deliver(Notification notification) {
        notification.getOrigin().getOwner().receiveNotification(notification);
    }
}
