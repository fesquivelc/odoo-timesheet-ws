import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';

import { NavDropdown } from '../header-components';

const accountMenuItemsAuthenticated = (
  <>
    <DropdownItem tag={Link} to="/account/settings">
      <FontAwesomeIcon icon="wrench" /> Configuración
    </DropdownItem>
    <DropdownItem tag={Link} to="/account/password">
      <FontAwesomeIcon icon="clock" /> Contraseña
    </DropdownItem>
    <DropdownItem tag={Link} to="/logout">
      <FontAwesomeIcon icon="sign-out-alt" /> Cerrar sesión
    </DropdownItem>
  </>
);

const accountMenuItems = (
  <>
    <DropdownItem id="login-item" tag={Link} to="/login">
      <FontAwesomeIcon icon="sign-in-alt" /> Iniciar sesión
    </DropdownItem>
    {/*<DropdownItem tag={Link} to="/register">*/}
    {/*<FontAwesomeIcon icon="sign-in-alt" /> Register*/}
    {/*</DropdownItem>*/}
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <NavDropdown icon="user" name="Cuenta" id="account-menu">
    {isAuthenticated ? accountMenuItemsAuthenticated : accountMenuItems}
  </NavDropdown>
);

export default AccountMenu;
