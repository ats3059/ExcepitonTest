package hello.vali.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
public class FileTestController {
    @Value("${local.dir.name}")
    public String dir;

    @PostMapping("/file/upload")
    public String fileUpTest(MultipartFile file) throws IOException {
        storeFileName(file);
        return "ok";
    }

    @GetMapping("/file/download")
    public ResponseEntity<Resource> fileDown(FileInfo fileInfo) throws MalformedURLException {
        String format = String.format("%s%s%s%s", "file:////",dir, fileInfo.getFileName(), fileInfo.getFileExt());
        UrlResource urlResource = new UrlResource(format);
        String downFileName = UriUtils.encode(fileInfo.getFileName() + fileInfo.getFileExt(), StandardCharsets.UTF_8);
        String disposition = "attachment; filename= " + "\"" + downFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,disposition)
                .contentType(MediaType.TEXT_HTML)
                .body(urlResource);
    }



    private void storeFileName(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        int lastWord = originalFilename.lastIndexOf(".") + 1;

        String ext = originalFilename.substring(lastWord);
        String fileName = UUID.randomUUID().toString();
        storeUploadFile(file, ext, fileName);

    }

    private void storeUploadFile(MultipartFile file, String ext, String fileName) throws IOException {
        String uploadFileName = String.format("%s%s%s", fileName,".", ext);
        file.transferTo(new File(String.format("%s%s",dir,uploadFileName)));
    }

    @Getter
    @Setter
    static class FileInfo {
        private String fileName;
        private String fileExt;
    }

}
