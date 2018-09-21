import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Project from './project';
import ProjectDetail from './project-detail';
import ProjectUpdate from './project-update';
import ProjectDeleteDialog from './project-delete-dialog';
import TimesheetByProject from "app/entities/timesheet/timesheet-by-project";
import TimesheetProjectUpdate from "app/entities/timesheet/timesheet-project-create";

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/timesheets`} component={TimesheetByProject}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/timesheets/new`} component={TimesheetProjectUpdate}/>
      <ErrorBoundaryRoute path={match.url} component={Project} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProjectDeleteDialog} />
  </>
);

export default Routes;
