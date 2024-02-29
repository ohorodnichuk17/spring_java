export interface IProductCreate {
    name: string;
    price: number;
    description: string;
    category_id: number;
    files: Array<{
        originFileObj: File;
    }>;
}
export interface IUploadedFile {
    originFileObj: File
}

export interface IProductItem {
    id: number;
    name: string;
    description: string;
    price: string;
    category_id: number;
}

export interface IProductImageItem {
    id: number;
    name: string;
    product_id: number;
}

export interface ICategoryItem {
    id: number;
    name: string;
    description: string;
    image: string;
}