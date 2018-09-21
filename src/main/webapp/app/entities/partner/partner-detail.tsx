import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './partner.reducer';
import { IPartner } from 'app/shared/model/partner.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPartnerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class PartnerDetail extends React.Component<IPartnerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { partnerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Partner [<b>{partnerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="documentType">Document Type</span>
            </dt>
            <dd>{partnerEntity.documentType}</dd>
            <dt>
              <span id="documentNumber">Document Number</span>
            </dt>
            <dd>{partnerEntity.documentNumber}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{partnerEntity.name}</dd>
            <dt>
              <span id="active">Active</span>
            </dt>
            <dd>{partnerEntity.active ? 'true' : 'false'}</dd>
            <dt>
              <span id="odooId">Odoo Id</span>
            </dt>
            <dd>{partnerEntity.odooId}</dd>
            <dt>User</dt>
            <dd>{partnerEntity.user ? partnerEntity.user.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/partner" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/partner/${partnerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ partner }: IRootState) => ({
  partnerEntity: partner.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PartnerDetail);
