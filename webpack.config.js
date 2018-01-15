'use strict';

const webpack = require('webpack');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

const IS_PRODUCTION = process.env.NODE_ENV === 'production';

const config = {
    context: path.resolve("./static"),
    entry: {
        mainpage: './index.js',
        vendor: './vendor.js'
    },
    output: {
        path: __dirname + '/build',
        filename: '[name]-bundle.js'
    },
    plugins: [
        new HtmlWebpackPlugin({
            title: "Sri Web Template",
            template: "!!ejs-loader!static/index.html"
        }),
        new webpack.optimize.CommonsChunkPlugin({
            name: "vendor",
            minChunks: Infinity,
            filename: "vendor.bundle.js"
        })
    ],
    module: {
        loaders: [

            {
                test: /\.json$/,
                loader: "json-loader"
            },

            {
                test: /\.css$/,
                loader: 'style-loader!css-loader'
            },
            {
                test: /\.woff(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url-loader?limit=10000&mimetype=application/font-woff"
            }, {
                test: /\.woff2(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url-loader?limit=10000&mimetype=application/font-woff"
            }, {
                test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url-loader?limit=10000&mimetype=application/octet-stream"
            }, {
                test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,
                loader: "file-loader"
            },
            {
                test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url-loader?limit=10000&mimetype=image/svg+xml"
            },
            {
                test: /\.(png|jpg|svg)$/,
                loaders: [
                    'url-loader?limit=8192',
                    {
                        loader: 'image-webpack-loader',
                        query: {
                            optipng: {
                                optimizationLevel: 4,
                            },
                            mozjpeg: {
                                progressive: true,
                            }
                        }
                    }
                ]
            } // inline base64 URLs for <=8k images, direct URLs for the rest,

        ]
    }


};

if (IS_PRODUCTION) {
    // prod only configs
    console.log("Prod mode");
    config.plugins.push(
        new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: JSON.stringify("production")
            }
        }),
        new webpack.optimize.ModuleConcatenationPlugin(),
        new UglifyJsPlugin({
            sourceMap: true,
            parallel: true,
            uglifyOptions: {
                output: {
                    semicolons: false
                },
                comments: true
            }
        })
    );
    //   new ClosureCompilerPlugin({
    //     compiler: {
    //       language_in: "ECMASCRIPT5",
    //       language_out: "ECMASCRIPT5",
    //       compilation_level: "ADVANCED"
    //     },
    //     concurrency: 3
    //   })
    // );
} else {
    console.log("Dev mode");
    //dev only configs
    config.devServer = {
        contentBase: path.join(__dirname, "dist/"),
        port: 8090,
        compress: true,
        hot: true,
        historyApiFallback: true
    };
    config.plugins.push(
        new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: JSON.stringify("development")
            }
        }),
        new webpack.HotModuleReplacementPlugin()
    );
}


module.exports = config;