import { ICustomer } from 'app/shared/model//customer.model';

export interface IRole {
  id?: number;
  roleID?: string;
  roleNanme?: string;
  customers?: ICustomer[];
}

export class Role implements IRole {
  constructor(public id?: number, public roleID?: string, public roleNanme?: string, public customers?: ICustomer[]) {}
}
