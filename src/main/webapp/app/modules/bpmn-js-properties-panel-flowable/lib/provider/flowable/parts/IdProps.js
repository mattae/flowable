'use strict';

import { isTextFieldEntryEdited, TextFieldEntry } from '@bpmn-io/properties-panel';
import { getBusinessObject, is } from 'bpmn-js/lib/util/ModelUtil';
import { isIdValid } from '../../../Utils';

module.exports = function(group, modeling, debounceInput, translate) {
    return [
        {
            id: 'id',
            component: Id(group, modeling, debounceInput, translate),
            isEdited: isTextFieldEntryEdited
        }
    ];
}

function Id(props, modeling, debounce, translate) {
    const {
        element
    } = props;

    const setValue = (value) => {
        modeling.updateProperties(element, {
            id: value
        });
    };

    const getValue = (element) => {
        return element.businessObject.id;
    };

    const validate = (value) => {
        const businessObject = getBusinessObject(element);

        return isIdValid(businessObject, value, translate);
    };

    return TextFieldEntry({
        element,
        id: 'id',
        label: translate(is(element, 'bpmn:Participant') ? 'Participant ID' : 'ID'),
        getValue,
        setValue,
        debounce,
        validate
    });
}
