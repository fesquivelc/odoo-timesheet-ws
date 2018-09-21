import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import {
  Button, Col, Row, Table, Card, CardImg, CardLink, CardText, CardBody,
  CardTitle, CardSubtitle
} from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './project.reducer';
import { IProject } from 'app/shared/model/project.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export class Project extends React.Component<IProjectProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { projectList, match } = this.props;
    return (
      <div>
        <h2 id="project-heading">
          Proyectos
        </h2>
        <hr />
        <Row>
          {projectList.map((project, index) => (
            <Col sm="4" lg="3">
              <Card>
                <CardBody>
                  <CardTitle>{project.name}</CardTitle>
                </CardBody>
                <CardBody>
                  <CardText>
                    <br />
                  </CardText>
                  <Link to={`${match.url}/${project.id}/timesheets`} className="btn btn-dark">
                    <FontAwesomeIcon icon="plus" />&nbsp; Hoja de tiempo
                  </Link>
                </CardBody>
              </Card>
            </Col>
          ))}
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ project }: IRootState) => ({
  projectList: project.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Project);
