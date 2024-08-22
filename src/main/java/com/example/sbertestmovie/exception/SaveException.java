package com.example.sbertestmovie.exception;

/**
 * Исключение, которое выбрасывается при ошибках, связанных с сохранением данных.
 * <p>
 * Это исключение используется для обозначения проблем, которые произошли
 * во время попытки сохранить объекты в базу данных.
 * </p>
 */
public class SaveException extends RuntimeException {
    /**
     * Конструктор, создающий исключение с указанным сообщением.
     *
     * @param message сообщение об ошибке, которое будет передано исключению
     */
    public SaveException(final String message) {
        super(message);
    }
}
