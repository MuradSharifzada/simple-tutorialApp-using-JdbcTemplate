package com.example.Tutorial.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TutorialAlreadyExistException extends RuntimeException {
    private String message;

    public TutorialAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

}
