package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.Notification;
import dev.myodan.oxiom.domain.Product;
import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class NotificationService {

    protected final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void endedProductEvent(Product product) {
        boolean isFailed = product.getHighestBidder() == null;

        if (!isFailed) {
            User highestBidder = product.getHighestBidder();

            Notification notification = Notification.builder()
                    .userId(highestBidder.getId())
                    .content(MessageFormat.format("{0} 경매에 낙찰되었습니다.", product.getName()))
                    .build();

            Notification savedNotification = notificationRepository.save(notification);
            simpMessagingTemplate.convertAndSend("/sub/notify/" + highestBidder.getUsername(), savedNotification);
        }

        Notification notification = Notification.builder()
                .userId(product.getCreatedBy().getId())
                .content(MessageFormat.format("{0} 경매가 {1}되었습니다.", product.getName(), isFailed ? "유찰" : "종료"))
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSend("/sub/notify/" + product.getCreatedBy().getUsername(), savedNotification);
    }

}
