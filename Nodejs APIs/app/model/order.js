const validator = require ('validator')
const mongoose = require ('mongoose')

const order = new mongoose.Schema ({
	email : {
		type : String,
		required : true,
		trim : true,
		lowercase : true,
		validate (value) {
			if (!validator.isEmail(value))
				throw new Error ('Email not valid')
		}
	},
	patient_name : {
		type : String,
		required : true
	},
	doctor_name : {
		type : String,
		required : true
	},
	medicine : [ {
		type : String
	}],
	status : {
		type : String
	}
})

const Order = mongoose.model ('Order', order)

module.exports = Order