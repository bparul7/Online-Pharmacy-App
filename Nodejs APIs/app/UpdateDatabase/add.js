const xlsxFile = require('read-excel-file/node');
const Pres = require ('../model/prescription.js');
const User = require ('../model/user.js')

const add = async (entry) => {
	try {
		const prescription = await new Pres (entry);
		await prescription.save();
	}
	catch (e) {
		//console.log (e.message);
	}
}

xlsxFile('../files/data.xlsx').then((rows) => {
	cnt = 0;
 	for (i in rows) {
 		const entry = {
 			"company" : rows[i][0],
 			"drug" : rows[i][1],
 			"mainsalt" : rows[i][2],
 			"country" : rows[i][3],
 			"type" : rows[i][4],
 			"availability" : rows[i][5]
 		}
 		add (entry);
 	}	
})