import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ITask } from 'app/shared/model/task.model';
import { getEntitiesByProject as getTasks } from 'app/entities/task/task.reducer';
import { getEntity, updateEntity, createEntity, reset } from './timesheet.reducer';
import { ITimesheet } from 'app/shared/model/timesheet.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITimesheetProjectUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface ITimesheetUpdateState {
  isNew: boolean;
  userId: number;
  taskId: number;
}

export class TimesheetProjectUpdate extends React.Component<ITimesheetProjectUpdateProps, ITimesheetUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userId: 0,
      taskId: 0,
      isNew: true
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUsers();
    this.props.getTasks(this.props.match.params.id);
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { timesheetEntity } = this.props;
      const entity = {
        ...timesheetEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    const projectId = this.props.match.params.id;
    this.props.history.push(`/entity/project/${projectId}/timesheets`);
  };

  render() {
    const { timesheetEntity, users, tasks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="pruebinhaApp.timesheet.home.createOrEditLabel">Create or edit a Timesheet</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : timesheetEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="timesheet-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    Date
                  </Label>
                  <AvField
                    id="timesheet-date"
                    type="date"
                    className="form-control"
                    name="date"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField
                    id="timesheet-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="unitAmountLabel" for="unitAmount">
                    Unit Amount
                  </Label>
                  <AvField
                    id="timesheet-unitAmount"
                    type="text"
                    name="unitAmount"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="odooIdLabel" for="odooId">
                    Odoo Id
                  </Label>
                  <AvField id="timesheet-odooId" type="number" className="form-control" name="odooId" />
                </AvGroup>
                <AvGroup>
                  <Label for="user.id">User</Label>
                  <AvInput id="timesheet-user" type="select" className="form-control" name="user.id">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {`${otherEntity.login} ${otherEntity.firstName} ${otherEntity.lastName}`}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="task.id">Task</Label>
                  <AvInput id="timesheet-task" type="select" className="form-control" name="task.id">
                    <option value="" key="0" />
                    {tasks
                      ? tasks.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/timesheet" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  tasks: storeState.task.entities,
  timesheetEntity: storeState.timesheet.entity,
  loading: storeState.timesheet.loading,
  updating: storeState.timesheet.updating
});

const mapDispatchToProps = {
  getUsers,
  getTasks,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TimesheetProjectUpdate);
