import { Moment } from 'moment';

export interface IVoucherLog {
  id?: number;
  voucherLogID?: number;
  voucherLogStatus?: boolean;
  voucherLogDateUsed?: Moment;
  usernameId?: number;
  voucherIDId?: number;
}

export class VoucherLog implements IVoucherLog {
  constructor(
    public id?: number,
    public voucherLogID?: number,
    public voucherLogStatus?: boolean,
    public voucherLogDateUsed?: Moment,
    public usernameId?: number,
    public voucherIDId?: number
  ) {
    this.voucherLogStatus = this.voucherLogStatus || false;
  }
}
