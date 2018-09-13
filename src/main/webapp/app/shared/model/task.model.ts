import { Moment } from 'moment';
import { IProject } from 'app/shared/model//project.model';
import { IUser } from './user.model';

export interface ITask {
  id?: number;
  name?: string;
  plannedHours?: number;
  odooId?: number;
  kanbanState?: string;
  active?: boolean;
  dateDeadline?: Moment;
  project?: IProject;
  user?: IUser;
}

export const defaultValue: Readonly<ITask> = {
  active: false
};
