package notification;

public class NotificationService {

    private EmailService email = new EmailService();
    private SmsService sms = new SmsService();

    public void notifyUser(String message) {
        email.sendEmail(message);
        sms.sendSms(message);
    }

}