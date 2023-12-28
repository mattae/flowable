import { isTextFieldEntryEdited, TextFieldEntry } from '@bpmn-io/properties-panel';
import { useService } from 'bpmn-js-properties-panel';
import { getLinkEventDefinition, isLinkSupported } from '../../../EventDefinitionUtil';

export default function LinkProps(props) {
    const {
        element
    } = props;

    if (!isLinkSupported(element)) {
        return [];
    }

    return [
        {
            id: 'linkName',
            component: LinkName,
            isEdited: isTextFieldEntryEdited
        },
    ];
}

function LinkName(props) {
    const {element} = props;

    const commandStack = useService('commandStack');
    const translate = useService('translate');
    const debounce = useService('debounceInput');

    const linkEventDefinition = getLinkEventDefinition(element);

    const getValue = () => {
        return linkEventDefinition.get('name');
    };

    const setValue = (value) => {
        commandStack.execute('element.updateModdleProperties', {
            element,
            moddleElement: linkEventDefinition,
            properties: {
                name: value
            }
        });
    };

    return TextFieldEntry({
        element,
        id: 'linkName',
        label: translate('Name'),
        getValue,
        setValue,
        debounce
    });
}
