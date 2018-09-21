import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './task.reducer';
import { ITask } from 'app/shared/model/task.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITaskDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class TaskDetail extends React.Component<ITaskDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { taskEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Task [<b>{taskEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{taskEntity.name}</dd>
            <dt>
              <span id="plannedHours">Planned Hours</span>
            </dt>
            <dd>{taskEntity.plannedHours}</dd>
            <dt>
              <span id="odooId">Odoo Id</span>
            </dt>
            <dd>{taskEntity.odooId}</dd>
            <dt>
              <span id="kanbanState">Kanban State</span>
            </dt>
            <dd>{taskEntity.kanbanState}</dd>
            <dt>
              <span id="active">Active</span>
            </dt>
            <dd>{taskEntity.active ? 'true' : 'false'}</dd>
            <dt>
              <span id="dateDeadline">Date Deadline</span>
            </dt>
            <dd>
              <TextFormat value={taskEntity.dateDeadline} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Project</dt>
            <dd>{taskEntity.project ? taskEntity.project.id : ''}</dd>
            <dt>User</dt>
            <dd>{taskEntity.user ? taskEntity.user.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/task" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/task/${taskEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ task }: IRootState) => ({
  taskEntity: task.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TaskDetail);
