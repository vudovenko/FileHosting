package ru.relex.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relex.service.ConsumerService;
import ru.relex.service.MainService;

import static ru.relex.model.RabbitQueue.*;

@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {

    private final MainService mainService;

    @Autowired
    public ConsumerServiceImpl(MainService mainService) {
        this.mainService = mainService;
    }

    // Над каждой очередью навешивается @RabbitListener с указанием очереди в RabbitMQ,
    // которую этот метод будет слушать
    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("NODE: Text message is received: " + update.getMessage().getText());
        mainService.processTextMessage(update);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessageUpdates(Update update) {
        log.debug("NODE: Doc message is received");
        mainService.processDocMessage(update);

    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
        log.debug("NODE: Photo message is received");
        mainService.processPhotoMessage(update);
    }
}
