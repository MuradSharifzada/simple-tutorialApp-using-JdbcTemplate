package com.example.Tutorial.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TutorialNotPublishedException extends RuntimeException {
    private String message;


    public TutorialNotPublishedException(String message) {
        super(message);
        this.message = message;
    }
}
