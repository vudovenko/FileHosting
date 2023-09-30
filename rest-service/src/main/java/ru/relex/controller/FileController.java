package ru.relex.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.relex.entity.AppDocument;
import ru.relex.entity.AppPhoto;
import ru.relex.entity.BinaryContent;
import ru.relex.service.FileService;

@Log4j
@RequestMapping("/file")
@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-doc")
    public ResponseEntity<?> getDoc(@RequestParam("id") String id) {
        //TODO для формирования badRequest добавить ControllerAdvice
        AppDocument doc = fileService.getDocument(id);
        if (doc == null) {
            return ResponseEntity.badRequest().build();
        }
        BinaryContent binaryContent = doc.getBinaryContent();

        FileSystemResource fileSystemResource = fileService.getFileSystemResource(binaryContent);
        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getMimeType()))
                .header("Content-disposition", "attachment; filename=" + doc.getDocName())
                .body(fileSystemResource);
        /* Метод getMimeType возвращает MIME-тип файла на основе его расширения.
        MIME-тип представляет собой стандартизированную метку, которая указывает на тип содержимого файла,
        такой как текстовый документ, изображение или аудиофайл.
        Этот метод принимает расширение файла в виде строки (например, "txt", "jpg", "mp3")
        и возвращает соответствующий MIME-тип. */
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-photo")
    public ResponseEntity<?> getPhoto(@RequestParam("id") String id) {
        //TODO для формирования badRequest добавить ControllerAdvice
        AppPhoto photo = fileService.getPhoto(id);
        if (photo == null) {
            return ResponseEntity.badRequest().build();
        }
        BinaryContent binaryContent = photo.getBinaryContent();

        FileSystemResource fileSystemResource = fileService.getFileSystemResource(binaryContent);
        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // добавляет к ответу хедер, в котором передается формат контента
                .header("Content-disposition", "attachment;")
                .body(fileSystemResource);
    }
}
