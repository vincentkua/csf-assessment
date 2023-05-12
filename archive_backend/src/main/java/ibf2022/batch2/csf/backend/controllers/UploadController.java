package ibf2022.batch2.csf.backend.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api")
public class UploadController {

    @Autowired
    ImageRepository imageRepo;

    @Autowired
    ArchiveRepository archiveRepo;

    // TODO: Task 2, Task 3, Task 4

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadArchive(@RequestPart MultipartFile file, @RequestPart String name,
            @RequestPart String title, @RequestPart String comments) {
        try {
            String key = imageRepo.upload(file, name, title, comments);
            String urllink = "https://vkbucket2023.sgp1.digitaloceanspaces.com/" + key;
            List<String> urls = new LinkedList<>();
            urls.add(urllink);

            Archive newarchive = new Archive(null, null, title, name, comments, urls);
            Object bundleid = archiveRepo.recordBundle(newarchive);

            System.out.println(bundleid);

            JsonObject payload = Json.createObjectBuilder()
                    .add("bundleId", bundleid.toString())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(payload.toString());

        } catch (IOException e) {
            JsonObject errorjson = Json.createObjectBuilder()
            .add("error", e.getMessage())
            .build();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorjson.toString());
        }

    }

    // TODO: Task 5

    // TODO: Task 6

}
