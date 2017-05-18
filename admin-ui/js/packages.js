import { getToken } from './auth';

export default (nga, admin) => {
  const packages = admin.getEntity('packages');

  packages.showView()
    .fields([
      nga.field('name').label('Package Name (ID)'),
    ])

  packages.listView()
    .fields([
      nga.field('name').label('Package Name (ID)'),
    ])
    .listActions([
      'show',
      'delete'
    ]);

  packages.creationView()
    .fields([
      nga.field('name')
        .label('Package Name (ID)')
        .validation({ 'required': true }),
      nga.field('file', 'file')
        .uploadInformation({ 'url': 'api/admin/tmp/files', headers: { 'Authorization' : `Basic ${getToken()}`} })
        .label('Package Jar')
    ]);

  return packages;
}
