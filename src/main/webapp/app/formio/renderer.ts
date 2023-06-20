import './components/Webform';
import { getComponents, registerComponent } from './components';
import Components from 'formiojs/components/Components';

import Utils from 'formiojs/utils';

import Formio from 'formiojs/Formio';

import Form from 'formiojs/Form';

function initRenderer() {
    Components.setComponents(getComponents());
    Formio.Components = Components;
    Formio.Templates = {};
}

export { Form, Utils, Components, Formio, initRenderer, registerComponent };
