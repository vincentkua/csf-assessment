package ibf2022.batch2.csf.backend.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
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
            String key = imageRepo.upload(file, name, title, comments).toString();
            String urllink = "https://vkbucket2023.sgp1.digitaloceanspaces.com/" + key;
            List<String> urls = new LinkedList<>();
            urls.add(urllink);

            Archive newarchive = new Archive(null, null, title, name, comments, urls);
            Object bundleid = archiveRepo.recordBundle(newarchive);

            // System.out.println(bundleid);

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

    @GetMapping(value = "/bundle/{bundleId}")
    public ResponseEntity<String> getbundlebyid(@PathVariable String bundleId){

        Archive archive = (Archive) archiveRepo.getBundleByBundleId(bundleId);
        System.out.println(archive);

        List<String> urllink = archive.getUrls();
        JsonArrayBuilder jsonaray = Json.createArrayBuilder();
        for(String url : urllink){
            jsonaray.add(url);
        }

        JsonObject payload = Json.createObjectBuilder()
        .add("title", archive.getTitle())
        .add("name", archive.getName())
        .add("date", archive.getDate().toString())
        .add("comments", archive.getComments())
        .add("urls", jsonaray)
        .build();

        return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

    }

    // TODO: Task 6
    @GetMapping(value = "/bundles")
    public ResponseEntity<String> getallbundles(){

        List<Archive> archives = (List<Archive>)archiveRepo.getBundles();
        System.out.println(archives);
    
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Archive archive : archives) {
            List<String> urlLinks = archive.getUrls();
            JsonArrayBuilder urlArrayBuilder = Json.createArrayBuilder();
            if (urlLinks != null) {
                for (String url : urlLinks) {
                    urlArrayBuilder.add(url);
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // String sdate = dateFormat.format(new Date());

            JsonObject jsonArchive = Json.createObjectBuilder()
                .add("bundleId", archive.getBundleId())
                .add("title", archive.getTitle())
                .add("name", archive.getName())
                .add("date", dateFormat.format(archive.getDate()))
                .add("comments", archive.getComments())
                .add("urls", urlArrayBuilder)
                .build();
            arrayBuilder.add(jsonArchive);
        }
    
        JsonArray jsonArray = arrayBuilder.build();
        return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
    }

}
