const mongoose = require('mongoose')
const {Schema} = mongoose

const Hajj = {
    firstname: {type: String, required: true},
    lastname: {type: String, required: true},
    username: String,
    password: String,

    morshid: String,
    nationality: String,

    age: {type: Number, required: true},
    isMale: {type: Boolean, required: true},
    inscriptionDate: {type: String, required: true},
    img: String,
    passportNumber: {type: String, required: true},
    geo: {type: String, required: true},
    helpDemands: [
        {
            date: {type: Date, required: true},
            geo: {type: String, required: true},
            demandType: String,
            treated: Boolean,
            treatedByAdmin: {type: Schema.Types.ObjectId, ref: 'Admin'}
        }
    ]
}

module.exports = Hajj