import { IStoreGroup } from 'app/shared/model//store-group.model';

export interface IUserStoreGroup {
  id?: number;
  storeGroupID?: number;
  userId?: number;
  storeGroups?: IStoreGroup[];
}

export class UserStoreGroup implements IUserStoreGroup {
  constructor(public id?: number, public storeGroupID?: number, public userId?: number, public storeGroups?: IStoreGroup[]) {}
}
