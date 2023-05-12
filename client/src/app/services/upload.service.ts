import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { uploadrequest } from '../models/models';
import { firstValueFrom, map } from 'rxjs';

const URL : string = "http://localhost:8080/upload"

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private http:HttpClient) { }
  uploadArchive(newrequest : uploadrequest) : Promise<any> {
    const formData = new FormData();
    formData.append("file", newrequest.selectedfile);
    formData.append("name", newrequest.name);
    formData.append("title", newrequest.title);
    if (!newrequest.comments){
      formData.append("comments", "-");
    }else{
      formData.append("comments", newrequest.comments);
    }
    
    return firstValueFrom(
      this.http.post(URL, formData)
      .pipe(
        map((v:any)=>{
          const bundleId = v["bundleId"] as any
          return bundleId
        })
      )
    )

  }
}
