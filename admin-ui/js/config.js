import packages from './packages';
import header from './header.html';

export default (nga) => {
  const admin = nga.application('Hatch Admin').baseApiUrl('/api/admin/');

  // empty dashboard
  admin.dashboard(nga.dashboard());

  // add entities
  admin.addEntity(configureEntity('packages', nga));

  // configure entities
  packages(nga, admin);

  admin.header(header);

  // attach the admin application to the DOM and execute it
  nga.configure(admin);
};

const configureEntity = (entityName, nga) => {
  const entity = nga.entity(entityName);
  entity.identifier(nga.field('name'));
  return entity;
};
