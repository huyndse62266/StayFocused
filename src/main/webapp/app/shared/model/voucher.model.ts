export interface IVoucher {
  id?: number;
  voucherID?: number;
  voucherNumber?: string;
  voucherStatus?: boolean;
  postId?: number;
}

export class Voucher implements IVoucher {
  constructor(
    public id?: number,
    public voucherID?: number,
    public voucherNumber?: string,
    public voucherStatus?: boolean,
    public postId?: number
  ) {
    this.voucherStatus = this.voucherStatus || false;
  }
}
