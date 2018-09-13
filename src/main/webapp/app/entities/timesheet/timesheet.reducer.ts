import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITimesheet, defaultValue } from 'app/shared/model/timesheet.model';

export const ACTION_TYPES = {
  FETCH_TIMESHEET_LIST: 'timesheet/FETCH_TIMESHEET_LIST',
  FETCH_TIMESHEET: 'timesheet/FETCH_TIMESHEET',
  CREATE_TIMESHEET: 'timesheet/CREATE_TIMESHEET',
  UPDATE_TIMESHEET: 'timesheet/UPDATE_TIMESHEET',
  DELETE_TIMESHEET: 'timesheet/DELETE_TIMESHEET',
  RESET: 'timesheet/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITimesheet>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TimesheetState = Readonly<typeof initialState>;

// Reducer

export default (state: TimesheetState = initialState, action): TimesheetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIMESHEET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIMESHEET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TIMESHEET):
    case REQUEST(ACTION_TYPES.UPDATE_TIMESHEET):
    case REQUEST(ACTION_TYPES.DELETE_TIMESHEET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TIMESHEET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIMESHEET):
    case FAILURE(ACTION_TYPES.CREATE_TIMESHEET):
    case FAILURE(ACTION_TYPES.UPDATE_TIMESHEET):
    case FAILURE(ACTION_TYPES.DELETE_TIMESHEET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIMESHEET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIMESHEET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIMESHEET):
    case SUCCESS(ACTION_TYPES.UPDATE_TIMESHEET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIMESHEET):
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

const apiUrl = 'api/timesheets';

// Actions

export const getEntities: ICrudGetAllAction<ITimesheet> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TIMESHEET_LIST,
  payload: axios.get<ITimesheet>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITimesheet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIMESHEET,
    payload: axios.get<ITimesheet>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITimesheet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIMESHEET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITimesheet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIMESHEET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITimesheet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIMESHEET,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
