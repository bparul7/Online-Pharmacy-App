require('dotenv').config()
const express = require ('express');
const app = express ();
require ('../Configuredb/connect.js');
require ('../UpdateDatabase/add.js');
const user_route = require ('../route/user_route.js');
const port = process.env.PORT || 5000;

app.use(express.json());
//configure express to use routes
app.use (user_route);

app.listen (port, () => {
	console.log ('Server is running on ' + port)
})