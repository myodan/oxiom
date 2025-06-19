package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.Notification;
import dev.myodan.oxiom.dto.NotificationResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface NotificationMapper {

    Notification toEntity(NotificationResponse notificationResponse);

    NotificationResponse toResponse(Notification notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Notification partialUpdate(NotificationResponse notificationResponse, @MappingTarget Notification notification);

}