package com.mattae.snl.plugins.flowable.form.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
public class FormIOFormModel extends SimpleFormModel implements Serializable {
    private String type;
    private String display;
    private String title;
    private List<Component> components = new ArrayList<>();

    @JsonIgnore
    public List<FormField> listAllFields() {
        if (components == null) {
            return Collections.emptyList();
        }
        return components.stream()
            .flatMap(component -> listComponent(component).stream()).
            filter(Component::isInput)
            .map(component -> (FormField) component)
            .toList();
    }

    @JsonIgnore
    public List<FormField> getFields() {
        if (components == null) {
            return Collections.emptyList();
        }
        return components.stream()
            .flatMap(component -> listComponent(component).stream()).
            filter(Component::isInput)
            .map(component -> (FormField) component)
            .toList();
    }

    private List<Component> listComponent(Component component) {
        if (component != null) {
            if (component instanceof ComponentContainer panel) {
                return panel.components.stream()
                    .flatMap(c -> listComponent(c).stream())
                    .toList();
            } else if (component instanceof Column column) {
                return column.getColumns().stream()
                    .flatMap(c -> c.getComponents().stream()
                        .flatMap(x -> listComponent(x).stream())
                    ).toList();
            } else if (component instanceof Tabs tabs) {
                return tabs.getComponents().stream()
                    .flatMap(c -> c.getComponents().stream()
                        .flatMap(x -> listComponent(x).stream()))
                    .toList();
            } else if (component instanceof Table table) {
                return table.getRows().stream()
                    .flatMap(l -> l.stream()
                        .flatMap(m -> m.getOrDefault("components", Collections.emptyList()).stream())
                        .flatMap(c -> listComponent(c).stream()))
                    .toList();
            } else {
                return List.of(component);
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Component extends FormField implements Serializable {
        @JsonIgnore
        private boolean input = false;
        private boolean valueJson = false;
        private String type;
        protected Object value;
        private boolean hidden;
        private boolean clearOnHide;
        private boolean hideLabel;
        private Object defaultValue;
        private String label;
        private String key;
        private int tabindex;
        private boolean tableView;
        private String placeholder;
        private String calculatedValue;
        private Map<String, Object> conditional;
        private Map<String, Object> validate;

        public String getId() {
            return key;
        }

        public void setId(String key) {
            if (key != null) {
                this.key = key;
            }
        }
    }

    @Getter
    @Setter
    public static class Content extends Component {
        private String html;

        public Content() {
            this.setType("content");
            setInput(false);
        }
    }

    @Getter
    @Setter
    public static class HtmlElement extends Component {
        private String tag;
        private String content;
        private List<String> attrs;

        public HtmlElement() {
            this.setType("htmlelement");
            setInput(false);
        }
    }

    static abstract class Container extends Component {
    }

    @Getter
    @Setter
    static abstract class ComponentContainer extends Component {
        protected List<Component> components;
    }

    @Getter
    @Setter
    public static class Column extends Container {
        protected List<? extends ColumnComponent> columns;

        public Column() {
            setType("columns");
            setInput(false);
        }
    }

    @Getter
    @Setter
    public static class Panel extends ComponentContainer {
        public Panel() {
            setType("panel");
            setInput(false);
        }
    }

    @Getter
    @Setter
    public static class ColumnComponent extends ComponentContainer {
        private int width;
        private int offset;
        private int push;
        private int pull;
    }

    @Getter
    @Setter
    public static class Tabs extends Component {
        private boolean verticalLayout;
        private List<TabComponent> components;

        public Tabs() {
            setType("tabs");
            setInput(false);
        }
    }

    @Getter
    @Setter
    public static class TabComponent extends ComponentContainer {
        private String key;
        private String label;
    }

    @Getter
    @Setter
    public static class Survey extends Component {
        private List<ValueLabelPair> questions;
        private List<ValueLabelPair> values;

        public Survey() {
            setType("survey");
            setInput(true);
            setValueJson(true);
        }
    }

    @Getter
    @Setter
    public static class ValueLabelPair {
        private String value;
        private String label;
    }

    @Getter
    @Setter
    public static class Signature extends Component {
        private String footer;
        private String width;
        private String height;
        private String penColor;
        private String backgroundColor;
        private String maxWidth;
        private String minWidth;

        public Signature() {
            setType("signature");
            setInput(true);
        }
    }

    @Getter
    @Setter
    public static class TextField extends Component {
        private String inputType;
        private String inputMask;
        private String prefix;
        private String suffix;
        @JsonProperty("case")
        private String _case;
        private boolean multiple;
        private boolean autofocus;

        public TextField() {
            setType("textfield");
            setInput(true);
        }
    }

    @Getter
    @Setter
    public static class Textarea extends TextField {
        private int rows = 3;
        private boolean wysiwyg;
        private String editor;
        private boolean fixedSize;
        private String inputFormat;

        public Textarea() {
            setType("textarea");
        }
    }

    @Getter
    @Setter
    public static class Password extends TextField {

        public Password() {
            setType("password");
        }
    }

    @Getter
    @Setter
    public static class Number extends TextField {

        public Number() {
            setType("number");
        }
    }

    @Getter
    @Setter
    public static class Currency extends TextField {

        public Currency() {
            setType("currency");
        }
    }

    @Getter
    @Setter
    public static class Email extends TextField {

        public Email() {
            setType("email");
        }
    }

    @Getter
    @Setter
    public static class Checkbox extends TextField {
        private String labelPosition;

        public Checkbox() {
            setType("checkbox");
        }
    }

    @Getter
    @Setter
    public static class PhoneNumber extends TextField {
        private String inputMode;
        private String displayMask;

        public PhoneNumber() {
            setType("phoneNumber");
        }
    }

    @Getter
    @Setter
    public static class Fieldset extends ComponentContainer {
        private String label;
        private String legend;

        public Fieldset() {
            setType("fieldset");
            setInput(false);
        }
    }

    @Getter
    @Setter
    public static class Datagrid extends ComponentContainer {
        private boolean tree;

        public Datagrid() {
            setType("datagrid");
            setInput(false);
            setValueJson(true);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Select extends Component {
        private String idPath;
        private SelectData data;
        private String dataSrc;
        private String widget;
        private String valueProperty;
        private int limit = 100;
        private boolean searchEnabled;
        private boolean lazyLoad;
        private String refreshOn;
        private String filter;
        private String template;
        private String multiple;

        public Select() {
            setType("select");
            setInput(true);
        }
    }

    @Getter
    @Setter
    public static class Button extends Component {
        private String label;
        private String key;
        private String size;
        private String leftIcon;
        private String rightIcon;
        private boolean block;
        private String action;
        private boolean disableOnInvalid;
        private String theme;

        public Button() {
            setType("button");
            setInput(false);
        }
    }

    @Getter
    @Setter
    public static class Datetime extends Component {
        private String label;
        private String key;
        private String format;
        private String useLocaleSettings;
        private boolean allowInput;
        private boolean enableDate;
        private boolean enableTime;
        private String defaultDate;
        private String displayInTimezone;
        private String timezone;
        private String datepickerMode;
        private Map<String, Object> datePicker;
        private Map<String, Object> timePicker;

        public Datetime() {
            setType("datetime");
            setInput(true);
        }
    }

    @Getter
    @Setter
    public static class Day extends Component {
        private String label;
        private String key;
        private Map<String, Object> fields;
        private String dayFirst;

        public Day() {
            setType("day");
            setInput(true);
        }
    }

    @Getter
    @Setter
    public static class EditGrid extends ComponentContainer {
        private String label;
        private String key;
        private String clearOnHide;
        private String tree;
        private String removeRow;
        private boolean defaultOpen;
        private boolean openWhenEmpty;
        private boolean modal;
        private boolean inlineEdit;
        private Map<String, Object> templates;

        public EditGrid() {
            setType("editgrid");
            setInput(false);
            setValueJson(true);
        }
    }

    @Setter
    @Getter
    public static class File extends Component {
        private String type;
        private String label;
        private String key;
        private boolean image;
        private String imageSize;
        private String filePattern;
        private String fileMinSize;
        private String fileMaxSize;

        public File() {
            setType("file");
            setInput(true);
        }
    }

    public static class Upload extends File {
        public Upload() {
            setType("upload");
            setInput(true);
        }
    }

    @Getter
    @Setter
    public static class Radio extends Component {
        private String label;
        private String key;
        private List<Option> values;

        public Radio() {
            setType("radio");
            setInput(true);
        }
    }

    @Getter
    @Setter
    public static class SelectBoxes extends Radio {
        private String inline;

        public SelectBoxes() {
            setType("selectBoxes");
            setInput(true);
        }
    }

    @Getter
    @Setter
    public static class Tags extends Component {
        private String delimeter;
        private String storeas;
        private String maxTags;

        public Tags() {
            setType("tags");
            setInput(true);
        }

        public void setStoreas(String storeas) {
            if (!Objects.equals(storeas, "string")) {
                setValueJson(true);
            }
        }

        public void setValueJson(boolean value) {
            if (!Objects.equals(storeas, "string")) {
                super.setValueJson(true);
            } else {
                super.setValueJson(value);
            }
        }
    }

    @Getter
    @Setter
    public static class Time extends TextField {
        private String format;
        private String dataFormat;

        public Time() {
            setType("time");
        }
    }

    @Getter
    @Setter
    public static class Url extends TextField {

        public Url() {
            setType("url");
            setInputType("url");
        }
    }

    @Getter
    @Setter
    public static class Well extends ComponentContainer {

        public Well() {
            setType("well");
            setInput(false);
        }
    }

    @Getter
    @Setter
    public static class Table extends Component {
        private int numRows;
        private int numCols;
        private String cellAlignment;
        private List<Object> header;
        private String caption;
        private boolean cloneRows;
        private boolean striped;
        private boolean bordered;
        private boolean hover;
        private boolean condensed;
        private List<List<Map<String, List<Component>>>> rows;

        public Table() {
            setType("table");
            setInput(false);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Option {
        private String label;
        private String value;
    }

    @Getter
    @Setter
    @ToString
    public static class SelectData {
        private List<Option> values;
        private String json;
        private String url;
    }
}
