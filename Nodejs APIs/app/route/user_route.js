const express = require ('express')
const route = new express.Router();
const User = require ('../model/user.js')
const auth = require ('../middleware/auth.js')
const Order = require ('../model/order.js')
const multer = require ('multer')

route.post ('/users/me/createorder', auth, async (req, res) => {
	try {
		const user = req.user
		const order = await new Order (req.body)
		order.status = "no"
		await order.save();
		res.send (order)
	}
	catch (e) {
		res.send (e.message)
	}
})

route.get ('/allorders', async (req, res) => {
	try {
		const result = await Order.find ({})
		res.send (result)
	}
	catch (e) {
		res.send (e.message)
	}
})

route.get ('/allpendingorder', async (req, res) => {
	try {
		const result = await Order.find ({status : "no"})
		res.send (result)
	}
	catch (e) {
		res.send (e.message)
	}
})

route.get ('/alldoneorder', async (req, res) => {
	try {
		const result = await Order.find ({status : "yes"})
		res.send (result)
	}
	catch (e) {
		res.send (e.message)
	}
})

route.patch ('/updatestatus/:id', async (req, res) => {
	const id = req.params.id;
	try {
		const result = await Order.findById (id);
		console.log (result)
		if (!result) {
			return res.status(404).send("Prescription not found")
		}
		result.status = "yes"
		await result.save();
		res.status (200).send (result)
	}
	catch (e) {
		res.status(500).send();
	}
})

//create Account
route.post ('/users', async (req, res) => {
	const me = new User (req.body)
	try {
		await me.save ();
		//created token
		const token = await me.generateToken ();
		const ret = {
			status : 1,
			message : "Successfully created the account",
			data : token
		}
		res.status (201).send (ret);
	}
	catch (e) {
		const ret = {
			status : 0,
			error : e.message,
			data : "false"
		}
		res.status (500).send (ret);
	}
})

//login users
route.post ('/users/login', async(req, res) => {
	try {
		const user = await User.loginCredentials (req.body.email);
		const token = await user.generateToken ();
		const ret = {
			status : 1,
			message : "Successfully Logged in",
			data : token
		}
		res.send (ret);
	}
	catch (e) {
		const ret = {
			status : 0,
			error : e.message,
			data : "false"
		}
		res.status (400).send (ret);
	}
})

//Get Profile
route.get ('/users/me', auth, async(req, res) => {
	const user = req.user;
	const ans = {
		status : 1,
		message : "Your Profile",
		data :  {user} 
	}
	res.send (ans);
})

//LogOut
route.post ('/users/logOut', auth, async (req, res) => {
	const user = req.user;
	//update token array
	try {
		user.tokens = user.tokens.filter ((tokens) => {
			return (tokens.token !== req.token)
		})
		await user.save ();
		const ans = {
			status : 1,
			message : "Successfully Logged Out"
		}
		res.send (ans);
	}
	catch (e) {
		const ans = {
			status : 0,
			message : e.message
		}
		res.send (ans);
	}
})

//logOut of all sessions
route.post ('/users/logOutAll', auth, async (req, res) => {
	const user = req.user;
	try {
		user.tokens = [];
		await user.save();
		const ans = {
			status : 1,
			message : "Successfully Logged Out of all sessions"
		}
		res.send (ans);
	}
	catch (e) {
		const ans = {
			status : 0,
			message : e.message
		}
		res.send (ans)
	}
})

const upload = multer ({
	
})

route.post ('/users/me/avatar',auth, upload.single('upload'), async (req, res) => {
	req.user.profile = req.file.buffer
	await req.user.save();
	res.send ("uploaded picture")
})

route.post ('/users/me/uploadid', auth, upload.single('upload'), async (req, res) => {
	req.user.id_image = req.file.buffer
	await req.user.save();
	res.send ("uploaded Id")
})

route.get ('/users/me/avatar', auth, async (req, res) => {
	try {
		const user = req.user
		if (!user.profile)
			throw new Error ("Upload Profile Pic")
		res.set ('Content-Type', 'image/jpg')
		res.send (user.profile)
	}
	catch (e) {
		res.send (e.message)
	}
})

route.get ('/users/me/id', auth, async (req, res) => {
	try {
		const user = req.user
		if (!user.id_image)
			throw new Error ("Upload ID")
		res.set ('Content-Type', 'image/jpg')
		res.send (user.id_image)
	}
	catch (e) {
		res.send (e.message)
	}
})
module.exports = route