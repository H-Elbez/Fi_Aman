const express = require('express')
const bodyParser = require('body-parser')
const methodOverride = require('method-override')
const mongoose = require('mongoose')
const restify = require('express-restify-mongoose')
const app = express()
const router = express.Router()
const path = require('path');

app.use(bodyParser.json())
app.use(methodOverride())

app.use(express.static(__dirname + '/public'));

const {Schema} = mongoose

mongoose.connect('mongodb://terry_baz:hajjhack2018@ds157901.mlab.com:57901/hajjhack')


var Hajj = new mongoose.Schema({
    firstname: {type: String, required: true},
    lastname: {type: String, required: true},
    username: String,
    password: String,

    morshid: String,
    nationality: String,

    age: {type: Number, required: true},
    isMale: {type: Boolean, required: true},
    inscriptionDate: {type: String, required: true},
    img: {type: String, required: true},
    passportNumber: {type: String, required: true},
    geo: {type: String, required: true},
    helpDemands: [
        {
            date: {type: Date, required: true},
            geo: {type: Number, required: true},
            type: String,
            treated: Boolean,
            treatedByAdmin: {type: Schema.Types.ObjectId, ref: 'Admin'}
        }
    ]
})

restify.serve(router, mongoose.model('Hajj', Hajj))


var Admin = new mongoose.Schema({
    firstname: {type: String, required: true},
    lastname: {type: String, required: true},
    username: String,
    password: String,
    age: {type: Number, required: true},
    isMale: {type: Boolean, required: true},
    inscriptionDate: {type: String, required: true},
    img: {type: String, required: true},
    lastAuthDate: {type: String, required: true},
})

restify.serve(router, mongoose.model('Admin', Admin))


app.use(router)

app.get('/', function(req, res) {
    res.sendFile(path.join(__dirname + '/public/index.html'));
});

// app.get('/dashboard', function(req, res) {
//     res.sendFile(path.join(__dirname + '/public/home.html'));
// });

app.get('/dashboard', function(req, res) {
    res.sendFile(path.join(__dirname + '/public/dashboard.html'));
});

app.listen(3000, () => {
    console.log('Express server listening on port 3000')
})