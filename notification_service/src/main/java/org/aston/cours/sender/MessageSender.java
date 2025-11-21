package org.aston.cours.sender;

/**
 * Интерфейс MessageSender определяет контракт для отправки сообщений.
 * <p>
 * Реализации этого интерфейса могут отправлять сообщения через разные каналы.
 * </p>
 */
public interface MessageSender {

    /**
     * Отправляет сообщение пользователю.
     *
     * @param to      адрес получателя (например, email)
     * @param subject тема сообщения
     * @param text    текст сообщения
     */
    void sendMessage(String to, String subject, String text);
}
