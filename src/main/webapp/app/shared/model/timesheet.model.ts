import { Moment } from 'moment';
import { IUser } from './user.model';
import { ITask } from 'app/shared/model//task.model';

export interface ITimesheet {
  id?: number;
  date?: Moment;
  name?: string;
  unitAmount?: number;
  odooId?: number;
  user?: IUser;
  task?: ITask;
}

export const defaultValue: Readonly<ITimesheet> = {};
