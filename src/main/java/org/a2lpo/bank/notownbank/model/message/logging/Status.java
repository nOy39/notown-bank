package org.a2lpo.bank.notownbank.model.message.logging;

/**
 * Статусы логов.
 * ERROR - Ошибка операции
 * WARNING - Операция была отклонена бизнес логикой приложения.
 * INFO - Информационное сообщение.
 */
public enum Status {
    ERROR, WARNING, INFO
}
