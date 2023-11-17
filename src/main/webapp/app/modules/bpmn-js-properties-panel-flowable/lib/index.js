import FlowablePropertiesPanel from './FlowablePropertiesPanel';

export default {
  __depends__: [
    require('./cmd'),
    require('diagram-js/lib/i18n/translate').default
  ],
  __init__: [ 'propertiesPanel' ],
  propertiesPanel: [ 'type', FlowablePropertiesPanel]
};
