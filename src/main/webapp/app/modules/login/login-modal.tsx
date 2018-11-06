import React from 'react';

import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, Alert, Row, Col } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: Function;
  handleClose: Function;
}

class LoginModal extends React.Component<ILoginModalProps> {
  handleSubmit = (event, errors, { username, password, rememberMe }) => {
    const { handleLogin } = this.props;
    handleLogin(username, password, rememberMe);
  };

  render() {
    const { loginError, handleClose } = this.props;

    return (
      <Modal isOpen={this.props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
        <AvForm onSubmit={this.handleSubmit}>
          <ModalHeader id="login-title" toggle={handleClose}>
            Iniciar sesión
          </ModalHeader>
          <ModalBody>
            <Row>
              <Col md="12">
                {loginError ? (
                  <Alert color="danger">
                    <strong>¡Error al iniciar sesión!</strong> Revise sus datos de acceso e intente nuevamente.
                  </Alert>
                ) : null}
              </Col>
              <Col md="12">
                <AvField
                  name="username"
                  label="Nombre de usuario"
                  placeholder="Su nombre de usuario"
                  required
                  errorMessage="¡Debe escribir su nombre de usuario!"
                  autoFocus
                />
                <AvField
                  name="password"
                  type="password"
                  label="Contraseña"
                  placeholder="Su contraseña"
                  required
                  errorMessage="¡Debe escribir su contraseña!"
                />
                <AvGroup check inline>
                  <Label className="form-check-label">
                    <AvInput type="checkbox" name="rememberMe" /> Recordarme
                  </Label>
                </AvGroup>
              </Col>
            </Row>
            <div className="mt-1">&nbsp;</div>
            <Alert color="warning">
              <Link to="/reset/request">¿Olvidó su contraseña?</Link>
            </Alert>
            {/*<Alert color="warning">*/}
            {/*<span>You don't have an account yet?</span> <Link to="/register">Register a new account</Link>*/}
            {/*</Alert>*/}
          </ModalBody>
          <ModalFooter>
            <Button color="secondary" onClick={handleClose} tabIndex="1">
              Cancelar
            </Button>{' '}
            <Button color="primary" type="submit">
              Iniciar sesión
            </Button>
          </ModalFooter>
        </AvForm>
      </Modal>
    );
  }
}

export default LoginModal;
