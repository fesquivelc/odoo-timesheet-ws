import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './timesheet.reducer';
import { ITimesheet } from 'app/shared/model/timesheet.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITimesheetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class TimesheetDetail extends React.Component<ITimesheetDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { timesheetEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="timesheetApp.timesheet.detail.title">Timesheet</Translate> [<b>{timesheetEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">
                <Translate contentKey="timesheetApp.timesheet.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={timesheetEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="name">
                <Translate contentKey="timesheetApp.timesheet.name">Name</Translate>
              </span>
            </dt>
            <dd>{timesheetEntity.name}</dd>
            <dt>
              <span id="unitAmount">
                <Translate contentKey="timesheetApp.timesheet.unitAmount">Unit Amount</Translate>
              </span>
            </dt>
            <dd>{timesheetEntity.unitAmount}</dd>
            <dt>
              <span id="odooId">
                <Translate contentKey="timesheetApp.timesheet.odooId">Odoo Id</Translate>
              </span>
            </dt>
            <dd>{timesheetEntity.odooId}</dd>
            <dt>
              <Translate contentKey="timesheetApp.timesheet.user">User</Translate>
            </dt>
            <dd>{timesheetEntity.user ? timesheetEntity.user.id : ''}</dd>
            <dt>
              <Translate contentKey="timesheetApp.timesheet.task">Task</Translate>
            </dt>
            <dd>{timesheetEntity.task ? timesheetEntity.task.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/timesheet" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/timesheet/${timesheetEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ timesheet }: IRootState) => ({
  timesheetEntity: timesheet.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TimesheetDetail);
