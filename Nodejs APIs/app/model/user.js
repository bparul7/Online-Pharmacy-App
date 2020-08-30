const validator = require ('validator')
const mongoose = require ('mongoose')
const bcrypt = require ('bcrypt')
const jwt = require ('jsonwebtoken')
//Validator Functions : https://www.npmjs.com/package/validator
//Mongoose Validator Schema Types : https://mongoosejs.com/docs/schematypes.html
//make model to store in database
const userSchema = new mongoose.Schema ({
	name : {
		type : String,
		required : true,
		trim : true,
		lowercase : true,
		validate (value) {
			if (value.length < 5)
				throw new Error ('Name Length should be greater than 4')
		}
	},
	email : {
		type : String,
		unique : true,
		required : true,
		trim : true,
		lowercase : true,
		validate (value) {
			if (!validator.isEmail(value))
				throw new Error ('Email not valid')
		}
	},
	profile : {
		type : Buffer
	},
	id_image : {
		type : Buffer
	},
	address : {
		type : String,
		required : true,
		trim : true,
	},
	tokens : [{
		token : {
			type : String,
			required : true,
		}
	}]
})

userSchema.methods.generateToken = async function () {
	const user = this;
	try {
		const token = await jwt.sign ({_id : user._id.toString()}, '@parulbansal');
		user.tokens = user.tokens.concat ({token : token});
		await user.save();
		return token;
	}
	catch (e) {
		throw new Error ("Unable to generate Token");
	}
}

userSchema.statics.loginCredentials = async (email) => {
	const user = await User.findOne ({email : email});
	if (!user) {
		throw new Error ("Email not found, Sign In first");
	}
	// const isMatch = await bcrypt.compare (password , user.password);
	// if (!isMatch) {
	// 	throw new Error ("Incorrect Password, Try some other password");
	// }
	return user;
}

// userSchema.pre ('save', async function (next) {
// 	const user = this;
// 	if (user.isModified ('password')) {
// 		user.password = await bcrypt.hash (user.password, 8);
// 	}
// 	next();
// })

const User = mongoose.model ('User', userSchema)

module.exports = User