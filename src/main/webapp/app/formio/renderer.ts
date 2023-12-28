import Webform from 'formiojs/Webform.js';
import { getComponents, registerComponent } from './components';
import Components from 'formiojs/components/Components';

import Utils from 'formiojs/utils';

import Formio from 'formiojs/Formio';

import Form from 'formiojs/Form';

Webform.prototype.redraw = function () {
    return this.render();
};

Webform.prototype.clear = function () {
    const viewContainer = this.viewContainer ? this.viewContainer() : null;
    if (viewContainer) {
        viewContainer.clear();
    }
};

Webform.prototype.render = function () {
    if (this.viewContainer && this.viewContainer()) {
        this.clear();
        this.renderComponents();
        this.setValue(this._submission);
    }
};

function initRenderer() {

    Components.setComponents(getComponents());
    Formio.Components = Components;
    Formio.Templates = {};
}

export { Form, Utils, Components, Formio, initRenderer, registerComponent };
