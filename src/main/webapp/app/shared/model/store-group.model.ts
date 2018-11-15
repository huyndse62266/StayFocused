export interface IStoreGroup {
  id?: number;
  storeTypeId?: number;
}

export class StoreGroup implements IStoreGroup {
  constructor(public id?: number, public storeTypeId?: number) {}
}
