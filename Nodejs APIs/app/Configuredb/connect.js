require('dotenv').config()
const mongoose = require ('mongoose');
//mongodb://localhost:port/database Name
mongoose.connect (process.env.MONGODB_URI || 'mongodb://127.0.0.1:27017/pharmacy_app', {
	useNewUrlParser : true,
	useCreateIndex : true,
	useFindAndModify : false,
	useUnifiedTopology: true 
}, (error, client) => {
	if (error)
		return console.log ('Unable to connect to Database')
	console.log ('Connection Successful')
})

