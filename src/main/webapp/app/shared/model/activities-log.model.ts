import { Moment } from 'moment';

export interface IActivitiesLog {
  id?: number;
  activitiesLogID?: number;
  activitiesLogDate?: Moment;
  activitiesLogPointReceived?: number;
  activitiesLog?: number;
  userId?: number;
}

export class ActivitiesLog implements IActivitiesLog {
  constructor(
    public id?: number,
    public activitiesLogID?: number,
    public activitiesLogDate?: Moment,
    public activitiesLogPointReceived?: number,
    public activitiesLog?: number,
    public userId?: number
  ) {}
}
