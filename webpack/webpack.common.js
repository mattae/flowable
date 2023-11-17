const {
    setInferVersion,
    withModuleFederationPlugin,
    share
} = require('@angular-architects/module-federation/webpack');
setInferVersion(true);

module.exports = withModuleFederationPlugin({
    name: "_259652ea_0e4e_4494_abaa_7883f2d16211",
    //This section relates directly to the webRemote section of plugin.yml
    //The key is the exposedName for components or name for modules and the value is the path to the
    //respective Angular component or module in your web project. For other web frameworks, it is
    //usually the path to the web element exposing your component
    exposes: {
        'WorkRoute': './src/main/webapp/app/flowable/work.routes.ts',
        'TaskRoute': './src/main/webapp/app/flowable/tasks.routes.ts'
    },
    shared: share({
        "@angular/core": {singleton: true, strictVersion: false},
        "@angular/common": {singleton: true, strictVersion: false},
        "@angular/common/http": {singleton: true, strictVersion: false},
        "@angular/router": {singleton: true, strictVersion: false},
        "@angular/forms": {singleton: true, strictVersion: false},
        "@angular/animations": {singleton: true, strictVersion: false},
        "@ngrx/store": {singleton: true, strictVersion: false},
        "@angular/material": {singleton: true, includeSecondaries: true, strictVersion: false},
        "@ngneat/transloco": {singleton: true, strictVersion: false},
        "@mattae/angular-shared": {singleton: true, strictVersion: false},
        "rxjs": {singleton: true, strictVersion: false},
    })
});

