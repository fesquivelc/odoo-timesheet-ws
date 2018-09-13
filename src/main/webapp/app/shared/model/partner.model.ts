import { IUser } from './user.model';

export interface IPartner {
  id?: number;
  documentType?: string;
  documentNumber?: string;
  name?: string;
  active?: boolean;
  odooId?: number;
  user?: IUser;
}

export const defaultValue: Readonly<IPartner> = {
  active: false
};
