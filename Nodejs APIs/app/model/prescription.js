const validator = require ('validator')
const mongoose = require ('mongoose')

const presSchema = new mongoose.Schema ({
	company : {
		type : String,
		required : true,
		trim : true,
		lowercase : true,
	},
	drug : {
		type : String,
		unique : true,
		required : true,
		trim : true,
		lowercase : true
	},
	mainsalt : {
		type : String,
		required : true,
		trim : true,
		lowercase : true
	},
	country : {
		type : String,
		required : true,
		trim : true,
		lowercase : true
	},
	type : {
		type : String,
		required : true,
		trim : true,
		lowercase : true
	},
	availability : {
		type : String,
		required : true,
		trim : true,
		lowercase : true
	}
})

const Pres = mongoose.model ('Pres', presSchema)

module.exports = Pres