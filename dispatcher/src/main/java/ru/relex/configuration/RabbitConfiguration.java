package ru.relex.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.relex.model.RabbitQueue.*;

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

    /* Блок с бинами очередей в брокере сообщений: с текстовыми сообщениями, фотографиями, документами
        и ответами от Node, адресуемыми Dispatcher-у.
       В брокере сообщений не нужно будет выполнять какие-то дополнительные действия, чтобы создать первичные очереди.
       При первом подключении приложения к брокеру очереди будут созданы с именами,
        которые передаются в конфиге в методах.
       И при последующих запусках приложения и подключениях к брокеру сообщения будут писаться в конец очередей. */
    @Bean
    public Queue textMessageQueue() {
        return new Queue(TEXT_MESSAGE_UPDATE);
    }

    @Bean
    public Queue docMessageQueue() {
        return new Queue(DOC_MESSAGE_UPDATE);
    }

    @Bean
    public Queue photoMessageQueue() {
        return new Queue(PHOTO_MESSAGE_UPDATE);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(ANSWER_MESSAGE);
    }
}
