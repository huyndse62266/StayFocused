export interface IListStore {
  id?: number;
  storeID?: string;
  storeName?: string;
  storeAddress?: string;
  storeId?: number;
}

export class ListStore implements IListStore {
  constructor(
    public id?: number,
    public storeID?: string,
    public storeName?: string,
    public storeAddress?: string,
    public storeId?: number
  ) {}
}
