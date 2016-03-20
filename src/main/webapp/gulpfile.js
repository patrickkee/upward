var gulp = require('gulp');
var buffer = require('vinyl-buffer');
var sourcemaps = require('gulp-sourcemaps');
var rename = require("gulp-rename");
var source = require('vinyl-source-stream');
var browserSync = require('browser-sync');
var watchify = require('watchify');
var historyApiFallback = require('connect-history-api-fallback');
var htmltidy = require('gulp-htmltidy');
var filesize = require('gulp-filesize');
var concatCss = require('gulp-concat-css');
var minifyCss = require('gulp-minify-css');
var csslint = require('gulp-csslint');
var assign = require('lodash.assign');
var browserify = require('browserify');

var customOpts = {
  entries: ['./js/app.js'],
  transform: [
    'reactify',
    'envify'
  ],
  debug: true,
  cache: {},
  packageCache: {},
  fullPaths: true
};

var opts = assign({}, watchify.args, customOpts);
var bundler = browserify(opts);

function bundle() {
  return bundler
      .bundle()
      .pipe(source('app.js'))
      .pipe(rename('bundle.js'))
      .pipe(buffer())
      .pipe(filesize())
      .pipe(sourcemaps.init({loadMaps: true}))
      .pipe(filesize())
      .pipe(sourcemaps.write('./'))
      .pipe(gulp.dest('./dist/js/'));
}
gulp.task('serve', ['html', 'js', 'css', 'img'], function() {
  var watch = watchify(bundler);
  // Without the line, update events won't be fired
  watch.bundle().on('data', function() {});

  browserSync({
    server: {
      baseDir: 'dist',
      middleware: [historyApiFallback()]
    },
    port: 8000,
    ui: {
      port: 8001
    }
  });

  gulp.watch(['*.html'], {cwd: 'src'}, ['html', browserSync.reload]);
  gulp.watch(['./js/**/*.js'], ['js', browserSync.reload]); 
  gulp.watch(['./css/*.css'], ['css', browserSync.reload]);
  bundler.on('update', bundle); // on any dep update, runs the bundler
  gulp.watch(['js/**/*.js'], {cwd: 'dist'}, browserSync.reload);
});

gulp.task('js', bundle);

gulp.task('css', function () {

  return gulp.src('./css/*.css')
    //.pipe(csslint({
    //  'compatible-vendor-prefixes': false,
    //  'box-sizing': false
    //}))
    //.pipe(concatCss('app.css'))
    //.pipe(minifyCss())
    .pipe(gulp.dest('./dist/css'));
});

gulp.task('img', function () {
  return gulp.src('./images/*.*')
    .pipe(gulp.dest('./dist/images/'));
});

gulp.task('html', function () {
  return gulp.src('./*.html')
    .pipe(htmltidy())
    .pipe(gulp.dest('./dist'));
});