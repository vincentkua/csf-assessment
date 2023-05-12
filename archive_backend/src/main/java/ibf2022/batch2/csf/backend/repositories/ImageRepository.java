package ibf2022.batch2.csf.backend.repositories;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${do.storage.bucketname}")
    private String bucketName;

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! OBJECT !!!

    // Unable to Unzip and upload.... process with 1 photo 1st...
	public Object upload(MultipartFile file , String name , String title, String comments ) throws IOException {
   
        Map<String, String> userData = new HashMap<>();
		userData.put("name", name);
        userData.put("title", title);
        userData.put("comments", comments);
        userData.put("uploadDateTime", LocalDateTime.now().toString());
        userData.put("originalFilename", file.getOriginalFilename());

		ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        String key = UUID.randomUUID().toString().substring(0, 8);
        StringTokenizer tk = new StringTokenizer(file.getOriginalFilename(), ".");
        int count = 0;
        String filenameExt = "";
        while (tk.hasMoreTokens()) {
            if (count == 1) {
                filenameExt = tk.nextToken();
                break;
            } else {
                filenameExt = tk.nextToken();
            }
        }
        if (filenameExt.equals("blob"))
            filenameExt = filenameExt + ".png";

        PutObjectRequest putRequest = new PutObjectRequest(bucketName, "myobject%s.%s".formatted(key, filenameExt),
                file.getInputStream(), metadata);
        putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(putRequest);
        
		return "myobject%s.%s".formatted(key, filenameExt);


	}
}
;