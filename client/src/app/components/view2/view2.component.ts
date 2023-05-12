import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { archive } from 'src/app/models/models';
import { BundleService } from 'src/app/services/bundle.service';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit {
  bundleId : string = ""
  arcdetails !: archive

  
  constructor(private activatedRoute : ActivatedRoute , private bundleSvc : BundleService , private router:Router){}

  ngOnInit(): void {
      this.activatedRoute.params.subscribe((params)=>{
        this.bundleId = params["bundleId"]
        this.bundleSvc.getBundleById(this.bundleId)
        .then(v=>{
          console.log(v)
          this.arcdetails = v
        })
        .catch(err=>{
          console.error(err)
        })
      })
  }

  backtoview1(){
    this.router.navigate(['/upload'])
  }

}
