import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPartner, defaultValue } from 'app/shared/model/partner.model';

export const ACTION_TYPES = {
  FETCH_PARTNER_LIST: 'partner/FETCH_PARTNER_LIST',
  FETCH_PARTNER: 'partner/FETCH_PARTNER',
  CREATE_PARTNER: 'partner/CREATE_PARTNER',
  UPDATE_PARTNER: 'partner/UPDATE_PARTNER',
  DELETE_PARTNER: 'partner/DELETE_PARTNER',
  RESET: 'partner/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPartner>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PartnerState = Readonly<typeof initialState>;

// Reducer

export default (state: PartnerState = initialState, action): PartnerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PARTNER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARTNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARTNER):
    case REQUEST(ACTION_TYPES.UPDATE_PARTNER):
    case REQUEST(ACTION_TYPES.DELETE_PARTNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PARTNER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARTNER):
    case FAILURE(ACTION_TYPES.CREATE_PARTNER):
    case FAILURE(ACTION_TYPES.UPDATE_PARTNER):
    case FAILURE(ACTION_TYPES.DELETE_PARTNER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTNER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTNER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARTNER):
    case SUCCESS(ACTION_TYPES.UPDATE_PARTNER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARTNER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/partners';

// Actions

export const getEntities: ICrudGetAllAction<IPartner> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PARTNER_LIST,
  payload: axios.get<IPartner>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPartner> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARTNER,
    payload: axios.get<IPartner>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPartner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARTNER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPartner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARTNER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPartner> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARTNER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
