package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.Notification;
import dev.myodan.oxiom.domain.Product;
import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    protected final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void endedProductEvent(Product product) {
        log.info("endedProductEvent: {}", product.getId());
        boolean isFailed = product.getHighestBidder() == null;

        if (!isFailed) {
            User highestBidder = product.getHighestBidder();

            Notification notification = Notification.builder()
                    .userId(highestBidder.getId())
                    .content(MessageFormat.format("{0} 경매에 낙찰되었습니다.", product.getName()))
                    .build();

            Notification savedNotification = notificationRepository.save(notification);
            simpMessagingTemplate.convertAndSendToUser(highestBidder.getUsername(), "/notify", savedNotification);
            log.info("Notification sent to user: {}", highestBidder.getUsername());
        }

        Notification notification = Notification.builder()
                .userId(product.getCreatedBy().getId())
                .content(MessageFormat.format("{0} 경매가 {1}되었습니다.", product.getName(), isFailed ? "유찰" : "종료"))
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSendToUser(product.getCreatedBy().getUsername(), "/notify", savedNotification);
        log.info("Notification sent to user: {}", product.getCreatedBy().getUsername());
    }

}
