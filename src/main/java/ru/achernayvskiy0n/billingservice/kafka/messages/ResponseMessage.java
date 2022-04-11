package ru.achernayvskiy0n.billingservice.kafka.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.kafka.common.requests.ApiError;
import org.springframework.http.HttpStatus;

/**
 *
 */
@Data
@Builder
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String username;
    private ApiResponseStatus status;
    private String message;

    public enum ApiResponseStatus {

        OK(200, "OK"),
        INTERNAL_SERVER_ERROR(500, "OK");

        private final int value;
        private final String reasonPhrase;

        ApiResponseStatus(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

    }
}
