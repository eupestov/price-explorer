var ExtractTextPlugin = require('extract-text-webpack-plugin');
//import HtmlWebpackPlugin from 'html-webpack-plugin';
var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  entry: ['./js/index.js', './css/index.scss'],
  output: {
    path: __dirname + '/dist',
    filename: 'main.js',
    publicPath: ''
  },
  module: {
    loaders: [
      {test: /\.css$/, loader: 'style!css'},
      {test: /\.jsx?$/, exclude: /(node_modules|bower_components)/, loader: 'babel'},
      {test: /node_modules[\\\/]admin-config[\\\/].*\.jsx?$/, loader: 'babel'},
      {test: /\.html$/, loader: 'html'},
      {test: /\.css$/, loader: ExtractTextPlugin.extract('css')},
      {test: /\.scss$/, loader: ExtractTextPlugin.extract('css!sass')}
    ]
  },
  devServer: {
    proxy: {
      '/api/*': {
        target: 'http://localhost:8081',
        secure: false
      }
    }
  },
  plugins: [
    new ExtractTextPlugin('[name].css', {
      allChunks: true
    }),
    new HtmlWebpackPlugin({
      template: `${__dirname}/index.html`
    })
  ]
};
