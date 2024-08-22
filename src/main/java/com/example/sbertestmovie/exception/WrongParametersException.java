package com.example.sbertestmovie.exception;

/**
 * Исключение, которое выбрасывается при наличии некорректных параметров у передаваемого объекта.
 * <p>
 * Это исключение используется для сигнализации о том, что один или несколько параметров
 * переданных в методе, не соответствуют ожидаемым требованиям.
 * </p>
 */
public class WrongParametersException extends RuntimeException {
    /**
     * Конструктор, создающий исключение с указанным сообщением.
     *
     * @param message сообщение об ошибке, которое будет передано исключению
     */
    public WrongParametersException(final String message) {
        super(message);
    }
}