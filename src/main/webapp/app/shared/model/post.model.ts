import { Moment } from 'moment';
import { IVoucher } from 'app/shared/model//voucher.model';

export interface IPost {
  id?: number;
  postID?: string;
  postName?: string;
  postDescription?: string;
  postStartDate?: Moment;
  postEndDate?: Moment;
  postBanner?: string;
  storeId?: number;
  vouchers?: IVoucher[];
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public postID?: string,
    public postName?: string,
    public postDescription?: string,
    public postStartDate?: Moment,
    public postEndDate?: Moment,
    public postBanner?: string,
    public storeId?: number,
    public vouchers?: IVoucher[]
  ) {}
}
