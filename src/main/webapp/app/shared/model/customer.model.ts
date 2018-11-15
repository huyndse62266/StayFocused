import { Moment } from 'moment';

export interface ICustomer {
  id?: number;
  customerID?: string;
  customerPassword?: string;
  customerFullName?: string;
  customerDOB?: Moment;
  customerPhone?: number;
  customerAddress?: string;
  roleId?: number;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public customerID?: string,
    public customerPassword?: string,
    public customerFullName?: string,
    public customerDOB?: Moment,
    public customerPhone?: number,
    public customerAddress?: string,
    public roleId?: number
  ) {}
}
