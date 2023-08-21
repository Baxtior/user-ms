package uz.agro.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoggingObject {
    private String method;
    private String request;
    private String response;
    private LocalDateTime dateTime;
    private long timestamp;

    @Override
    public String toString() {
        return "LoggingObject{" +
                "method='" + method + '\'' +
                ", request='" + request + '\'' +
                ", response='" + response + '\'' +
                ", dateTime=" + dateTime +
                ", timestamp=" + timestamp +
                '}';
    }
}
