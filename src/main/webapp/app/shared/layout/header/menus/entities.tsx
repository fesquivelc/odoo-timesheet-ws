import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Gestión" id="entity-menu">
    {/*<DropdownItem tag={Link} to="/entity/partner">*/}
    {/*<FontAwesomeIcon icon="asterisk" />&nbsp;Partner*/}
    {/*</DropdownItem>*/}
    <DropdownItem tag={Link} to="/entity/project">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Proyectos
    </DropdownItem>
    {/*<DropdownItem tag={Link} to="/entity/task">*/}
    {/*<FontAwesomeIcon icon="asterisk" />&nbsp;Task*/}
    {/*</DropdownItem>*/}
    {/*<DropdownItem tag={Link} to="/entity/timesheet">*/}
    {/*<FontAwesomeIcon icon="asterisk" />&nbsp;Timesheet*/}
    {/*</DropdownItem>*/}
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
