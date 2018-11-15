import { IStoreGroup } from 'app/shared/model//store-group.model';

export interface IStoreType {
  id?: number;
  storeTypeID?: string;
  storeTypeName?: string;
  storeGroups?: IStoreGroup[];
}

export class StoreType implements IStoreType {
  constructor(public id?: number, public storeTypeID?: string, public storeTypeName?: string, public storeGroups?: IStoreGroup[]) {}
}
