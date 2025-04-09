package pl.dminior8.push_notifications_listener.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pl.dminior8.push_notifications_listener.model.EStatus;
import pl.dminior8.push_notifications_listener.model.Notification;
import pl.dminior8.push_notifications_listener.service.NotificationService;

import java.util.Random;

import static pl.dminior8.push_notifications_listener.model.EStatus.DELIVERED;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    public static final String PUSH_QUEUE = "pushQueue";

    @RabbitListener(queues = PUSH_QUEUE)
    public void receivePushNotification(String json) throws JsonProcessingException {
        Notification notification = objectMapper.readValue(json, Notification.class);
        if (random.nextBoolean()) {
            notification.setStatus(DELIVERED);
            notificationService.updateNotification(notification);
            log.info("SUCCES | Push notification delivered successfully for user {}: \n{}", notification.getRecipient(), formatNotification(notification));
        } else {
            log.warn("FAILED | Push notification delivery failed for user {}: \n{}", notification.getRecipient(), notification.getId());
        }
    }

    private String formatNotification(Notification n) {
        return String.format("""
                ID: %s
                Content: %s
                Channel: %s
                Priority: %s
                Scheduled Time: %s
                Timezone: %s
                Status: %s
                """,
                n.getId(),
                n.getContent(),
                n.getChannel(),
                n.getPriority(),
                n.getScheduledTime(),
                n.getTimezone(),
                n.getStatus()
        );
    }
}
