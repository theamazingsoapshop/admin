//var tailwindcss = require('tailwindcss');

module.exports = {
    plugins: [
        //tailwindcss('tailwind.config.js'),
        require('tailwindcss'),
        require('autoprefixer'),
        require('cssnano')({preset: 'default'})
    ]
}
