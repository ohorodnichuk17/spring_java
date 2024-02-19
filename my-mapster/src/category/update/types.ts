export interface ICategoryUpdate {
    name: string;
    image: File|undefined;
    description: string;
}

export interface IUploadedFile {
    originFileObj: File
}