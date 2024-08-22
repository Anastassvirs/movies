package com.example.sbertestmovie.exception;

/**
 * Исключение, которое выбрасывается, когда не удаётся найти запрашиваемый объект.
 * <p>
 * Это исключение используется для сигнализации о том, что объект, который
 * искался по идентификатору или другим критериям, не был найден в базе данных.
 * </p>
 */
public class NotFoundAnythingException extends RuntimeException {
    /**
     * Конструктор, создающий исключение с указанным сообщением.
     *
     * @param message сообщение об ошибке, которое будет передано исключению
     */
    public NotFoundAnythingException(final String message) {
        super(message);
    }
}
