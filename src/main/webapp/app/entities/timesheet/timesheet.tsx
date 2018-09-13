import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './timesheet.reducer';
import { ITimesheet } from 'app/shared/model/timesheet.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITimesheetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Timesheet extends React.Component<ITimesheetProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { timesheetList, match } = this.props;
    return (
      <div>
        <h2 id="timesheet-heading">
          <Translate contentKey="timesheetApp.timesheet.home.title">Timesheets</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="timesheetApp.timesheet.home.createLabel">Create new Timesheet</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="timesheetApp.timesheet.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="timesheetApp.timesheet.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="timesheetApp.timesheet.unitAmount">Unit Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="timesheetApp.timesheet.odooId">Odoo Id</Translate>
                </th>
                <th>
                  <Translate contentKey="timesheetApp.timesheet.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="timesheetApp.timesheet.task">Task</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {timesheetList.map((timesheet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${timesheet.id}`} color="link" size="sm">
                      {timesheet.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={timesheet.date} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{timesheet.name}</td>
                  <td>{timesheet.unitAmount}</td>
                  <td>{timesheet.odooId}</td>
                  <td>{timesheet.user ? timesheet.user.id : ''}</td>
                  <td>{timesheet.task ? <Link to={`task/${timesheet.task.id}`}>{timesheet.task.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${timesheet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${timesheet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${timesheet.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ timesheet }: IRootState) => ({
  timesheetList: timesheet.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Timesheet);
