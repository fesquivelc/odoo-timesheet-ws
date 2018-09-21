import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Partner from './partner';
import PartnerDetail from './partner-detail';
import PartnerUpdate from './partner-update';
import PartnerDeleteDialog from './partner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PartnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PartnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PartnerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Partner} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PartnerDeleteDialog} />
  </>
);

export default Routes;
