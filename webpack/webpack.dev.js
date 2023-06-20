const webpackMerge = require('webpack-merge').merge;
const commonConfig = require('./webpack.common.js');

module.exports = webpackMerge(commonConfig, {
    devtool: 'eval-source-map',
    devServer: {
        proxy: [
            {
                context: [
                    '/app/rest'
                ],
                target: `http://localhost:9080`,
                secure: false,
            },
            {
                context: [
                    '/websocket'
                ],
                target: 'ws://127.0.0.1:9080',
                ws: true
            }],
        historyApiFallback: true
    },
    mode: 'development'
});
