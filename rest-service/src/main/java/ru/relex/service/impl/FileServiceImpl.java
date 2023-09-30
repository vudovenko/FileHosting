package ru.relex.service.impl;


import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.relex.entity.AppDocument;
import ru.relex.entity.AppPhoto;
import ru.relex.entity.BinaryContent;
import ru.relex.repository.AppDocumentRepository;
import ru.relex.repository.AppPhotoRepository;
import ru.relex.service.FileService;
import ru.relex.utils.CryptoTool;

import java.io.File;
import java.io.IOException;

/**
 * Отечает за получение файлов из БД
 */
@Log4j
@Service
public class FileServiceImpl implements FileService {
    private final AppDocumentRepository appDocumentRepository;
    private final AppPhotoRepository appPhotoRepository;
    private final CryptoTool cryptoTool;

    @Autowired
    public FileServiceImpl(AppDocumentRepository appDocumentRepository,
                           AppPhotoRepository appPhotoRepository,
                           CryptoTool cryptoTool) {
        this.appDocumentRepository = appDocumentRepository;
        this.appPhotoRepository = appPhotoRepository;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public AppDocument getDocument(String hash) {
        var id = cryptoTool.idOf(hash);
        if (id == null) {
            return null;
        }
        return appDocumentRepository.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String hash) {
        var id = cryptoTool.idOf(hash);
        if (id == null) {
            return null;
        }
        return appPhotoRepository.findById(id).orElse(null);
    }

    /**
     * Преобразовывает массив байт из БД в объект класса FileSystemResource,
     * который можно отправить в теле ответа пользователю.
     */
    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {
        try {
            // TODO добавить генерацию имени временного файла
            // Создаем временный файл с расширением .bin
            File temp = File.createTempFile("tempFile", ".bin");
            // При завершении JVM удаляем файл из постоянной памяти компьютера.
            // По-сути метод регистрирует файл в очередь на удаление
            temp.deleteOnExit();
            // Записываем массив байт в объект временного файла.
            FileUtils.writeByteArrayToFile(temp, binaryContent.getFileAsArrayOfBytes());
            // Оборачиваем файл в объект FileSystemResource,
            // который может быть передан в теле ответа HTTP.
            return new FileSystemResource(temp);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }
}
