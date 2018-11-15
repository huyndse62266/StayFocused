import { IPost } from 'app/shared/model//post.model';
import { IListStore } from 'app/shared/model//list-store.model';

export interface IStore {
  id?: number;
  storeID?: string;
  storeName?: string;
  storePhone?: number;
  storeMail?: string;
  storeWeb?: string;
  storeDescription?: string;
  posts?: IPost[];
  listStores?: IListStore[];
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public storeID?: string,
    public storeName?: string,
    public storePhone?: number,
    public storeMail?: string,
    public storeWeb?: string,
    public storeDescription?: string,
    public posts?: IPost[],
    public listStores?: IListStore[]
  ) {}
}
