import { Moment } from 'moment';

export interface IPointLog {
  id?: number;
  pointLogID?: number;
  pointLogPointSpent?: number;
  pointLogDateUsed?: Moment;
  voucherId?: number;
  userId?: number;
}

export class PointLog implements IPointLog {
  constructor(
    public id?: number,
    public pointLogID?: number,
    public pointLogPointSpent?: number,
    public pointLogDateUsed?: Moment,
    public voucherId?: number,
    public userId?: number
  ) {}
}
