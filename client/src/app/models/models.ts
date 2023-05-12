export interface uploadrequest{
    name : string ;
    title : string ;
    comments : string ;
    zipfile : string ;
    selectedfile : File
}

export interface archive{
    bundleId : string;
    title : string;
    name : string;
    date : Date;
    comments : string;
    urls : string[];
}