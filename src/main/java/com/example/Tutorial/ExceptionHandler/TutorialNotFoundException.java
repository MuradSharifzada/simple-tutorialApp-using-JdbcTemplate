package com.example.Tutorial.ExceptionHandler;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TutorialNotFoundException extends RuntimeException {
    private String message;

    public TutorialNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
