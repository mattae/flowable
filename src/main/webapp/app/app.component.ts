import { AfterViewInit, Component } from '@angular/core';
import { WorkService } from './flowable/services/work.service';
import { DEFAULT_TOOLBAR, Editor, Toolbar } from 'ngx-editor';
import { FuseNavigationItem, FuseScrollResetDirective, FuseVerticalNavigationComponent } from '@mattae/angular-shared';
import { RouterOutlet } from '@angular/router';

// use Camunda BPMN namespace

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html',
    imports: [
        FuseVerticalNavigationComponent,
        FuseScrollResetDirective,
        RouterOutlet
    ],
    standalone: true

})
export class AppComponent implements AfterViewInit {
    defaultNavigation: FuseNavigationItem[] = [
        {
            id: 'example',
            type: 'basic',
            link: '/'
        }
    ];

    form = {
        components: [
            {
                type: 'textfield',
                label: 'FirstName',
                key: 'firstName',
                input: true
            },
            {
                type: 'textfield',
                label: 'LastName',
                key: 'lastName',
                input: true
            },
            {
                type: 'email',
                label: 'Email',
                key: 'email',
                input: true
            },
            {
                type: 'signature',
                key: 'signature',
                label: 'Submit',
                theme: 'primary'
            }
        ]
    }
    options1 = {
        custom: {
            title: 'User Fields',
            weight: 10,
            components: {
                firstName: {
                    title: 'First Name',
                    key: 'firstName',
                    icon: 'terminal',
                    schema: {
                        label: 'First Name',
                        type: 'textfield',
                        key: 'firstName',
                        input: true
                    }
                },
                lastName: {
                    title: 'Last Name',
                    key: 'lastName',
                    icon: 'terminal',
                    schema: {
                        label: 'Last Name',
                        type: 'textfield',
                        key: 'lastName',
                        input: true
                    }
                },
                email: {
                    title: 'Email',
                    key: 'email',
                    icon: 'at',
                    schema: {
                        label: 'Email',
                        type: 'email',
                        key: 'email',
                        input: true
                    }
                },
                phoneNumber: {
                    title: 'Mobile Phone',
                    key: 'mobilePhone',
                    icon: 'phone-square',
                    schema: {
                        label: 'Mobile Phone',
                        type: 'phoneNumber',
                        key: 'mobilePhone',
                        input: true
                    }
                }
            }
        },
    }
    options = {
        builder: {
            basic: false,
            data: false,
            premium: false,
            customBasic: {
                title: 'Basic Components',
                default: true,
                weight: 0,
                components: {
                    textfield: true,
                    textarea: true,
                    email: true,
                    number: true,
                }
            },
            advanced: {
                components: {
                    email: false,
                    url: false,
                    tags: false,
                    address: false,
                    survey: false,
                    currency: false,
                    signature: false,
                    day: false,
                    phoneNumber: false,
                    datetime: false
                }
            },
            layout: {
                title: 'Layout Components',
                weight: 0,
                components: {
                    panel: true,
                    table: false,
                    tabs: false,
                    well: false,
                    columns: false,
                    fieldset: false,
                    content: false,
                    htmlelement: false
                }
            }
        },
        language: 'es',
        i18n: {
            es: {
                'Label': 'Etiqueta',
                'Label Position': 'Posición de la etiqueta',
                'Placeholder': 'Marcador de posición',
                'Description': 'Descripción',
                'Tooltip': 'Tooltip',
                'Prefix': 'Prefijo',
                'Suffix': 'Sufijo',
                'Widget': 'Widget',
                'Input Mask': 'Máscara de entrada',
                'Custom CSS Class': 'Clase CSS personalizada',
                'Tab Index': 'Tab Index',
                'Hidden ': 'Oculto',
                'Hide Label': 'Ocultar la etiqueta',
                'Show Word Counter': 'Mostrar contador de palabras',
                'Show Character Counter': 'Mostrar contador de caracteres',
                'Hide Input': 'Ocultar Input',
                'Excellent': 'Excelente',
                'Initial Focus': 'Enfoque inicial',
                'Allow Spellcheck': 'Permitir revisión ortográfica',
                'Disabled': 'Deshabilitado',
                'Table View': 'Vista de tabla',
                'Modal Edit': 'Modal Edit',
                'Multiple Values': 'Valores múltiples',
                'Persistent': 'persistente',
                'Input Format': 'Formato de entrada',
                'Protected': 'Protegido',
                'Database Index': 'Índice de la base de datos',
                'Mixed (Allow upper and lower case)': 'Mezclado (Permitir mayúsculas y minúsculas)',
                'Uppercase': 'Mayúsculas',
                'Lowercase': 'Minúsculas',
                'Encrypted (Enterprise Only)': 'Cifrado (Sólo Empresa)',
                'Default Value': 'Valor por defecto',
                'Drag and Drop a form component': 'Arrastrar y soltar un componente',
                'Text Field': 'Campo de texto',
                'Email': 'Correo electrónico',
                'Text Area': 'Área de texto',
                'Panel': 'Panel',
                'Time': 'Tiempo',
                'Submit': 'Enviar',
                'Basic Components': 'Componentes básicos',
                'Layout Components': 'Componentes del diseño',
                'Advanced': 'Avanzado',
                'Hidden': 'Oculto',
                'Component': 'Componente',
                'Display': 'Mostrar',
                'Data': 'Datos',
                'Validation': 'Validación',
                'API': 'API',
                'Conditional': 'Condicional',
                'Logic': 'Lógica',
                'Layout': 'Diseño',
                'Allow Multiple Masks': 'Permitir varias máscaras',
                'Input Field': 'Campo de entrada',
                'Top': 'Arriba',
                'Left (Left-aligned)': 'Izquierda (alineado a la izquierda)',
                'Input Type': 'Tipo de entrada',
                'Collapsible': 'Colapsable',
                'Preview': 'Previsualización',
                'Text Case': 'Caso de texto',
                'Redraw On': 'Redraw On',
                'Clear Value When Hidden': 'Limpiar cuando se oculta',
                'Custom Default Value': 'Valor predeterminado',
                'Calculated Value': 'Valor calculado',
                'Calculate Value on server': 'Calcular el valor en el servidor',
                'Allow Manual Override of Calculated Value': 'Permitir la anulación manual del valor calculado',
                'Server': 'Servidor',
                'Client': 'Cliente',
                'None': 'Ninguno',
                'Validate On': 'Validar en',
                'Required': 'Requerido',
                'Unique': 'Único',
                'Minimum Length': 'Longitud mínima',
                'Maximum Length': 'Longitud máxima',
                'Minimum Word Length': 'Longitud mínima de la palabra',
                'Maximum Word Length': 'Longitud máxima de la palabra',
                'Regular Expression Pattern': 'Patrón de expresión regular',
                'Error Label': 'Etiqueta de error',
                'Custom Error Message': 'Mensaje de error personalizado',
                'Custom Validation': 'Validación personalizada',
                'JSONLogic Validation': 'Validación JSONLogic',
                'Property Name': 'Nombre de la propiedad',
                'Field Tags': 'Etiquetas Tags',
                'Custom Properties': 'Propiedades personalizadas',
                'Add Another': 'Agregar otro',
                'Save': 'Guardar',
                'Cancel': 'Cancelar',
                'Remove': 'Remover',
                'Rows': 'Filas',
                'Title': 'Título',
                'Theme': 'Tema',
                'Data Format': 'Formato de datos',
                'Advanced Logic': 'Lógica avanzada',
                'Advanced Conditions': 'Condiciones Avanzadas',
                'Simple': 'Simple',
                'HTML Attributes': 'Atributos HTML',
                'PDF Overlay': 'Overlay PDF',
                'Number': 'Número ',
                'Use Thousands Separator': 'Usar el separador de miles'
            }
        }
    };
    editor: Editor = new Editor();
    toolbar: Toolbar = DEFAULT_TOOLBAR;

    change(event) {
        console.log('Data', event)
    }

    constructor(private workService: WorkService
    ) {
        workService.getWorkForm().subscribe(res => console.log('Work forms', res))
    }

    ngAfterViewInit(): void {

    }

    protected readonly
    DEFAULT_TOOLBAR = DEFAULT_TOOLBAR;
}
