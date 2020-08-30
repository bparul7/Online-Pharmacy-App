const User = require ('../model/user.js')
const jwt = require ('jsonwebtoken')

//check if token 
const auth = async (req, res, next) => {
	try {
		//get token 
		const token = req.header ('Authorization').replace ('Bearer ', '');
		const payload = jwt.verify (token, '@parulbansal')
		const user = await User.findOne ({_id : payload._id, 'tokens.token' : token})
		console.log (user)
		if (!user) {
			throw new Error ('Token has expired');
		}
		req.token = token
		req.user = user
		next();
	}
	catch (e) {
		const ans = {
			status : 0,
			error : e.message,
			data : {}
		}
		res.send (ans)
	}
}

module.exports = auth;