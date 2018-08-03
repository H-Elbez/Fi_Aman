const Admin = {
    firstname: {type: String, required: true},
    lastname: {type: String, required: true},
    username: String,
    password: String,
    age: {type: Number, required: true},
    isMale: {type: Boolean, required: true},
    inscriptionDate: {type: String, required: true},
    img: {type: String, required: true},
    lastAuthDate: {type: String, required: true},
}

module.exports = Admin