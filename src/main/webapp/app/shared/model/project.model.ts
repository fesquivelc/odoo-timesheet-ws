import { IPartner } from 'app/shared/model//partner.model';

export interface IProject {
  id?: number;
  active?: boolean;
  name?: string;
  odooId?: number;
  partner?: IPartner;
}

export const defaultValue: Readonly<IProject> = {
  active: false
};
