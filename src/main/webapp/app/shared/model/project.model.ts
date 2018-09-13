import { IPartner } from 'app/shared/model//partner.model';
import { IUser } from './user.model';

export interface IProject {
  id?: number;
  active?: boolean;
  name?: string;
  odooId?: number;
  partner?: IPartner;
  users?: IUser[];
}

export const defaultValue: Readonly<IProject> = {
  active: false
};
