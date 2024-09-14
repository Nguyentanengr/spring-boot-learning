package com.anonymous.shop_application.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessResponse extends ResponseEntity<SuccessResponse.Payload> {

    // PUT, PATCH, DELETE
    public SuccessResponse (HttpStatus status, String message) {
        super(new Payload(status.value(), message), HttpStatus.OK);
    }

    // GET, POST
    public SuccessResponse(HttpStatus status, String message, Object data) {
        super(new Payload(status.value(), message, data), HttpStatus.OK);
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Payload {
        private final int status;
        private final String message;
        private Object data;

        public Payload(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
