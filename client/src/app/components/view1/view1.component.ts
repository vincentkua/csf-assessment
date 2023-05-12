import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { uploadrequest } from 'src/app/models/models';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit {

  form!:FormGroup
  fileSelected : File | null = null
  

  constructor(private fb:FormBuilder , private uploadSvc : UploadService){}

  ngOnInit(): void {
      this.form = this.fb.group({
        name : this.fb.control<string>("",[Validators.required]),
        title : this.fb.control<string>("",[Validators.required]),
        comments : this.fb.control<string>(""),
        zipfile : this.fb.control("" , [Validators.required])
      })
  }

  filechange(event : any){
    const file:File = event.target.files[0];
    if (file) {
        const filename = file.name;
        this.fileSelected = file;
        console.log("new file assigned:" + this.fileSelected)
    }else{
      this.fileSelected = null
      console.log("file cleared:" + this.fileSelected)
    }
  }

  processform(){
    const newrequest : uploadrequest = this.form.value

    if (this.fileSelected != null) {
      newrequest.selectedfile = this.fileSelected
      this.uploadSvc.uploadArchive(newrequest)
      
    } else{
      alert("Zip file Not Selected?")
    }
    // now call Svc to upload...   


  }

  

}

