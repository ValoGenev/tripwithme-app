package app.dto.notification;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NotificationDto {

    private String id;

    private String message;

    private String action;
}
