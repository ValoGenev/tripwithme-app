package app.dto.message;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MessageDto {

    private String id;

    private String message;

    private LocalDateTime sentTime;

}
