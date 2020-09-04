package kz.ok.training.service.exception;

public class UserActivationException extends Throwable {
    public UserActivationException(String message) {
        System.out.println(message);
    }
}