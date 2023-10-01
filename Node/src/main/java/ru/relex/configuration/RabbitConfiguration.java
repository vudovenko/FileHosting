package ru.relex.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для интеграции с RabbitMQ.
 */
@Configuration
public class RabbitConfiguration {
    /**
     * Возвращает json-конвертер. Преобразовывает апдейты в json и передает их в RabbitMQ.
     * При получении апдейтов обратно в приложение преобразовывает их в Java-объект.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Не нужно во 2 раз объявлять названия очередей в конфиге,
    // так как они уже были прописаны в конфиге RabbitConfiguration в dispatcher
}
